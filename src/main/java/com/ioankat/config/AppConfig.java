package com.ioankat.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.ioankat")
@PropertySource("classpath:persistence-mysql.properties")
public class AppConfig {

//    The Environment object, env, will
//    be loaded with the properties file from the annotation
    @Autowired
    private Environment env;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public DataSource securityDatasource() {
        ComboPooledDataSource securityDatasource = new ComboPooledDataSource();

        try {
            securityDatasource.setDriverClass(env.getProperty("jdbc.driver"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }

        logger.info(">>>> jdbc.url = " + env.getProperty("jdbc.url"));
        logger.info(">>>> jdbc.user = " + env.getProperty("jdbc.user"));

        securityDatasource.setJdbcUrl(env.getProperty("jdbc.url"));
        securityDatasource.setUser(env.getProperty("jdbc.user"));
        securityDatasource.setPassword(env.getProperty("jdbc.password"));

        securityDatasource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        securityDatasource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
        securityDatasource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        securityDatasource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
        return securityDatasource;
    }

    private int getIntProperty(String propName) {
        Optional<String> propVal = Optional.ofNullable(env.getProperty(propName));
        return Integer.parseInt(propVal.orElseThrow(() -> new RuntimeException("Property not found -> " + propName)));
    }


    private Properties getHibernateProperties() {
        // set hibernate properties
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        return props;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(){

        // create session factorys
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        // set the properties
        sessionFactory.setDataSource(securityDatasource());
        sessionFactory.setPackagesToScan(env.getProperty("hiberante.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

        // setup transaction manager based on session factory
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

}
