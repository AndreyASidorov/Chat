package ru.Sidorov.ChatSimbirsoft.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.servlet.*;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
//@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@EnableWebSecurity
public class WebSecurityConfig /*extends WebSecurityConfigurerAdapter*/ {
    @Autowired
    DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();
    }

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/registration", "/js/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/main")
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll();
    }*/
    /*@Scheduled(fixedRate = "1000ms")
    public void unbannedUsers(){
        @Autowired userbannedlist;
        if(userbannedlist.getTime() <= new Date(){
            @Autowired roomRepo;
            Room room = roomRepo.findById(userBannedList.getRoom());
            room.setBannedUsers(room.getBannedUsers.remove(userbannedlist.getid()));
        }
    }*/

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .requestMatchers()
                    .antMatchers("/getUserKey", "/messageSend", "/chat")
                    .and()
                    .authorizeRequests().anyRequest().authenticated()/*hasRole("BOT")*/
                    .and().httpBasic();
                    //.and();
        }
    }

    @Configuration
    //@Order(2)
    public static class ApiLoginFormConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/registration", "/js/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .defaultSuccessUrl("/room")
                    .loginPage("/login")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutSuccessUrl("/login")
                    .permitAll();
        }
    }

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
        manager.setDataSource(dataSource);
        manager.setAuthoritiesByUsernameQuery("select login, user_role " +
                "from chat.user_role r " +
                "join chat.app_user u ON r.id = u.user_role_id " +
                "where u.login = ?");
        manager.setUsersByUsernameQuery("select login, password, is_active from chat.app_user " +
                "where login=?");
        return manager;
    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("select login, user_role " +
                        "from chat.user_role r " +
                        "join chat.app_user u ON r.id = u.user_role_id " +
                        "where u.login = ?")
                .usersByUsernameQuery("select login, password, 'true' from chat.app_user " +
                        "where login=?");
    }*/
}
