import { Component, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { UserService } from 'src/app/common/services/user.service';
import { Constants } from 'src/app/common/utility/constants';

export interface Data {
  id: number,
  userId: number,
  picture: string,
  username: string,
  roles: {}[],
  email: string,
  mobile: string,
  statu: number,
  createTime: string,
  disabled: boolean
}
@Component({
  selector: 'app-user-manage',
  templateUrl: './user-manage.component.html',
  styleUrls: ['./user-manage.component.less']
})
export class UserManageComponent implements OnInit {

  username: string;
  // 拿到状态码对应信息
  menuState = Constants.MenuState;

  checked = false;
  loading = false;
  indeterminate = false;
  listOfData: readonly Data[] = [];
  listOfCurrentPageData: readonly Data[] = [];
  setOfCheckedId = new Set<number>();
  // 编辑模态框
  isConfirmLoading: boolean;
  popupsHandle: number;
  popupsTitle: string;
  isVisible = false;
  // 绑定到子组件的userId
  userId: number;
  // 搜索参数
  searchInfo: string = '';
  // 批量删除提交数据
  requestData: number[] = [];

  constructor(private nzMessageService: NzMessageService, private userService: UserService) { }

  ngOnInit() {
    this.getUserListOfAll();
  }

  search() { }

  /**
   * 获取所有用户信息
   */
  getUserListOfAll() {
    this.userService.getUserListOfAll().subscribe((res) => {
      let users = res.data;
      this.convertUsers(users);
    })
  }

  /**
  * users显示格式转换
  * @param users
  */
  convertUsers(users: any) {
    this.listOfData = users.map((user, index) => ({
      id: index,
      userId: user.userId,
      picture: user.picture,
      username: user.username,
      roles: user.roles,
      email: user.email,
      mobile: user.mobile,
      statu: user.statu,
      createTime: user.createTime,
      disabled: false
    }))
  }

  /**
   * 对话框弹出switch
   * @param handleNum 
   * @param userId 
   */

  showModal(handleNum: number, userId?: number): void {

    if (handleNum === 1) {
      this.popupsHandle = 1
      this.popupsTitle = "新增用户";
    } else if (handleNum === 2) {
      this.userId = userId;
      this.popupsHandle = 2;
      this.popupsTitle = "分配角色";
    } else if (handleNum === 3) {
      this.userId = userId;
      this.popupsHandle = 3;
      this.popupsTitle = "编辑";
    }
    this.isVisible = true;
  }

  /**
  * 模态框取消
  */
  handleCancel(): void {
    this.isVisible = false;
  }

  /**
   * 接受子组件更新消息
   * @param isUpdate
   */
  accept() {
    this.getUserListOfAll();
    this.handleCancel();
  }

  /**
   * 删除气泡确认框
   */
  cancel(): void {
    this.nzMessageService.info('click cancel');
  }

  /**
   * 删除点击事件
   * @param roleId 
   */
  confirm(roleId: number, dataId?: number): void {
    let roleIdArr: number[] = [roleId];
    this.delateUsers(roleIdArr, dataId);
  }

  /**
   * 批量删除角色信息
   * @param userIds
   */
  delateUsers(userIds: number[], dataId?: number) {
    if (dataId) {
      this.userService.deleteUserByIds(userIds).subscribe((res) => {
        this.nzMessageService.info(res.msg);
        if (res.status === 200) {
          if (this.setOfCheckedId.has(dataId)) { this.setOfCheckedId.delete(dataId); }
          this.getUserListOfAll();
        }
      })
    } else {
      this.userService.deleteUserByIds(userIds).subscribe((res) => {
        this.nzMessageService.info(res.msg);
        this.getUserListOfAll();
      })
    }
  }

  updateCheckedSet(id: number, checked: boolean): void {
    if (checked) {
      this.setOfCheckedId.add(id);
    } else {
      this.setOfCheckedId.delete(id);
    }
  }

  onCurrentPageDataChange(listOfCurrentPageData: readonly Data[]): void {
    this.listOfCurrentPageData = listOfCurrentPageData;
    this.refreshCheckedStatus();
  }

  refreshCheckedStatus(): void {
    const listOfEnabledData = this.listOfCurrentPageData.filter(({ disabled }) => !disabled);
    this.checked = listOfEnabledData.every(({ id }) => this.setOfCheckedId.has(id));
    this.indeterminate = listOfEnabledData.some(({ id }) => this.setOfCheckedId.has(id)) && !this.checked;
  }

  onItemChecked(id: number, checked: boolean): void {
    this.updateCheckedSet(id, checked);
    this.refreshCheckedStatus();
  }

  onAllChecked(checked: boolean): void {
    this.listOfCurrentPageData
      .filter(({ disabled }) => !disabled)
      .forEach(({ id }) => this.updateCheckedSet(id, checked));
    this.refreshCheckedStatus();
  }

  sendRequest(): void {
    this.loading = true;
    this.requestData = this.listOfData.filter(data => this.setOfCheckedId.has(data.id))
      .map(val => val.userId);
    this.delateUsers(this.requestData);
    setTimeout(() => {
      this.setOfCheckedId.clear();
      this.refreshCheckedStatus();
      this.loading = false;
    }, 1000);
  }

}

