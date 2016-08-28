/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.config;

import com.njin.mychores.controller.ChoreGroupController;
import com.njin.mychores.controller.ChoreGroupUserController;
import com.njin.mychores.controller.UserController;
import com.njin.mychores.dao.ChoreGroupDao;
import com.njin.mychores.dao.ChoreGroupDaoImpl;
import com.njin.mychores.dao.ChoreGroupUserDao;
import com.njin.mychores.dao.ChoreGroupUserDaoImpl;
import com.njin.mychores.dao.UserDao;
import com.njin.mychores.dao.UserDaoImpl;
import com.njin.mychores.service.ChoreGroupService;
import com.njin.mychores.service.ChoreGroupServiceImpl;
import com.njin.mychores.service.ChoreGroupUserService;
import com.njin.mychores.service.ChoreGroupUserServiceImpl;
import com.njin.mychores.service.SessionService;
import com.njin.mychores.service.UserService;
import com.njin.mychores.service.UserServiceImpl;
import java.util.Properties;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import service.TestSessionServiceImpl;

/**
 *
 * @author aj
 */
@Configuration
@EnableTransactionManagement
@Profile("test")
@PropertySource(value={"classpath:application.properties"})
@WebAppConfiguration 
public class JpaConfiguration {
 
    @Autowired
    private Environment environment;
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("test.jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("test.jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("test.jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("test.jdbc.password"));
        return dataSource;
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityMangerFactory() throws NamingException {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(new String[] { "com.njin.mychores.model" });
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties());
        return factoryBean;
    }
    
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        return hibernateJpaVendorAdapter;
    }
    
    private Properties jpaProperties() {
        Properties p = new Properties();
        p.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        p.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        p.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        return p;
    }
    
    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }
    
    @Bean
    public UserDao getUserDao() {
        return new UserDaoImpl();
    }
    
    @Bean
    public UserController userController() {
        return new UserController();
    }
    
    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }    
    
    @Bean
    public SessionService sessionService() {
        return new TestSessionServiceImpl();
    }
    
    @Bean
    public ChoreGroupController choreGroupController() {
        return new ChoreGroupController();
    }
    
    @Bean
    public ChoreGroupDao choreGroupDao() {
        return new ChoreGroupDaoImpl();
    }
    
    @Bean
    public ChoreGroupService choreGroupService() {
        return new ChoreGroupServiceImpl();
    }    
    
    @Bean
    public ChoreGroupUserDao choreGroupUserDao() {
        return new ChoreGroupUserDaoImpl();
    }
    
    @Bean
    public ChoreGroupUserService choreGroupUserService() {
        return new ChoreGroupUserServiceImpl();
    }
    
    @Bean
    public ChoreGroupUserController choreGroupUserController() {
        return new ChoreGroupUserController();
    }
}
