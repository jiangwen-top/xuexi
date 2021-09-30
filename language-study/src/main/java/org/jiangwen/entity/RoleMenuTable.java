package org.jiangwen.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author name：JiangWen
 * @since 2021-10-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMenuTable extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long roleMenuTable;

    private Long roleId;

    private Long frontMenuId;


}