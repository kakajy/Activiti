package com.jsyl.activiti.configuration;

import com.jsyl.activiti.factory.CustomGroupEntityManagerFactory;
import com.jsyl.activiti.factory.CustomUserEntityManagerFactory;
import com.jsyl.activiti.manager.CustomUserEntityManager;
import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.rest.common.application.ContentTypeResolver;
import org.activiti.rest.common.application.DefaultContentTypeResolver;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author chenk
 * @create 2019/12/9 9:46
 */
@Configuration
public class ActivitiConfiguration {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Bean//(name = "processEngineConfiguration")
    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
        SpringProcessEngineConfiguration spec = new SpringProcessEngineConfiguration();
        List<SessionFactory> customSessionFactories = new ArrayList<SessionFactory>();
        CustomUserEntityManagerFactory customUserEntityManagerFactory = new CustomUserEntityManagerFactory();
        customUserEntityManagerFactory.setCustomUserEntityManager(new CustomUserEntityManager());
        customSessionFactories.add(customUserEntityManagerFactory);
        spec.setCustomSessionFactories(customSessionFactories);
        spec.setDataSource(dataSource);
        spec.setTransactionManager(platformTransactionManager);
        spec.setDatabaseSchemaUpdate("true");
//        Resource[] resources = null;
//        // 启动自动部署流程
//        try {
//            resources = new PathMatchingResourcePatternResolver().getResources("classpath*:bpmn/*.bpmn");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        spec.setDeploymentResources(resources);
        return spec;
    }

    @Bean(name = "processEngineConfiguration")
    public ProcessEngineConfigurationImpl processEngineConfiguration() {
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
        // add following configuration
        // load the custom manager session factory
        List<SessionFactory> customSessionFactories = new ArrayList<SessionFactory>();
        customSessionFactories.add(new CustomUserEntityManagerFactory());
        processEngineConfiguration.setCustomSessionFactories(customSessionFactories);

        return processEngineConfiguration;
    }

    @Bean
    public ProcessEngineFactoryBean processEngine() {
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration(springProcessEngineConfiguration());
        return processEngineFactoryBean;
    }


    @Bean
    public RepositoryService repositoryService() throws Exception {
        return processEngine().getObject().getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService() throws Exception {
        return processEngine().getObject().getRuntimeService();
    }

    @Bean
    public TaskService taskService() throws Exception {
        return processEngine().getObject().getTaskService();
    }

    @Bean
    public HistoryService historyService() throws Exception {
        return processEngine().getObject().getHistoryService();
    }

    @Bean
    public IdentityService identityService() throws Exception {
        return processEngine().getObject().getIdentityService();
    }

    @Bean
    public FormService formService() throws Exception {
        return processEngine().getObject().getFormService();
    }

    @Bean
    public ManagementService managementService() throws Exception {
        return processEngine().getObject().getManagementService();
    }

    //rest service
    @Bean
    public RestResponseFactory restResponseFactory() {
        return new RestResponseFactory();
    }

    @Bean
    public ContentTypeResolver contentTypeResolver() {
        return new DefaultContentTypeResolver();
    }
}
