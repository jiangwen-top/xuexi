package org.jiangwen.config;

import org.jiangwen.security.CaptchaFilter;
import org.jiangwen.security.LoginFailureHandler;
import org.jiangwen.security.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    CaptchaFilter captchaFilter;

//    @Autowired
//    CustomAuthenticationFilter customAuthenticationFilter;

    private static final String[] URL_WHITELIST = {

            "/login",
            "/logout",
            "/captcha",
            "/favicon.ico",

    };

    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()

                // 登录配置
                .formLogin()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)

//                .and()
//                .logout()
//                .logoutSuccessHandler(jwtLogoutSuccessHandler)

                // 禁用session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 配置拦截规则
                .and()
                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()
                .anyRequest().authenticated()

        // 异常处理器
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler)

        //配置自定义的过滤器
                .and()
//                .addFilter(jwtAuthenticationFilter())
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

        ;
//        http.addFilterAt(customAuthenticationFilter(),
//                UsernamePasswordAuthenticationFilter.class);

    }

//    @Bean
//    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
//        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
//        filter.setAuthenticationSuccessHandler(loginSuccessHandler);
//        filter.setAuthenticationFailureHandler(loginFailureHandler);
//
//
//        //重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
//        filter.setAuthenticationManager(authenticationManagerBean());
//        return filter;
//    }


}
