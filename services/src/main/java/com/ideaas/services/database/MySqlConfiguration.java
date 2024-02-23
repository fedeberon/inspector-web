package com.ideaas.services.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@ComponentScan( basePackages = "com.ideaas.*")
@EnableJpaRepositories(
        basePackages = { "com.ideaas.*"},
        entityManagerFactoryRef = "mySqlEntityManagerFactory",
        transactionManagerRef = "mySqlTransactionManager")
public class MySqlConfiguration {

    @Value("${spring.datasource.driver}")
    private String driver;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String dialect;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Primary
    @Bean(name = "dataSource")
    public DataSource sqlServerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.driver);
        dataSource.setUrl("jdbc:mysql://" + this.url + "?verifyServerCertificate=false&allowPublicKeyRetrieval=true&useSSL=false&requireSSL=false");
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        return dataSource;
    }

    @Primary
    @Bean(name = "mySqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mySqlEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource barDataSource) {
        return builder
                .dataSource(barDataSource)
                .packages("com.ideaas.services.*")
                .persistenceUnit("mySqlEntityManager")
                .properties(jpaProperties())
                .build();
    }

    private Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("hibernate.dialect", this.dialect);
        /*props.put("hibernate.hbm2ddl.auto", "update");*/
        return props;
    }

    @Primary
    @Bean(name = "mySqlTransactionManager")
    public PlatformTransactionManager barTransactionManager(
            @Qualifier("mySqlEntityManagerFactory") EntityManagerFactory mySqlEntityManagerFactory) {
        return new JpaTransactionManager(mySqlEntityManagerFactory);
    }


}
