package com.jsyl.activiti.controller;

import com.google.gson.Gson;
import com.jsyl.activiti.pojo.Project;
import com.jsyl.activiti.repository.ProjectRespository;
import org.activiti.engine.*;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author chenk
 * @create 2019/12/5 14:53
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserEntityManager manager;
    @Resource
    private IdentityService identityService;
    @Resource
    private TaskService taskService;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private ProjectRespository projectRespository;

    Gson GSON = new Gson();
    private static final org.apache.log4j.Logger LOG = Logger.getLogger(UserController.class);
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @GetMapping("/query")
    public User test() {
        System.out.println("-----test-----");
        return manager.findUserById("1");
    }

    @GetMapping("/test2")
    public List<User> test2() {
        return identityService.createUserQuery().list();
    }

    @GetMapping("/test3")
    public boolean test3() {
        return identityService.checkPassword("chenk", "chenk");
    }

    @GetMapping("/test4")
    public List<Model> test4() {
        return repositoryService.createModelQuery().list();
    }

    @GetMapping("/revive")
    public String revive() throws IOException, ParseException {
        addUserAndRole();
        deploy();
        startDeployment("qingjia:1:8");
        submit();
//        check("zhuguan",true);
//        check("jingli",true);
//        check("renshi",true);
        return "success";
    }

    /**
     * 部署流程
     *
     * @throws IOException
     */
    @GetMapping("/deploy")
    public void deploy() throws IOException {
        File file = new File("C:\\Users\\jsyl\\Desktop\\qingjia.bpmn20.xml");
        Deployment deployment = repositoryService// 与流程定义和部署相关的Service
                .createDeployment()// 创建一个部署对象
                .name("qingjia")// 添加部署的名称
                .addInputStream(file.getName(), new FileInputStream(file))// 加载资源，一次一个
                .deploy();// 完成部署
//        System.out.println("部署ID" + deployment.getId());// 1
//        System.out.println("部署名称" + deployment.getName());
    }

    /**
     * 启动流程
     *
     * @param procdefid
     */
    @GetMapping("/start")
    public void startDeployment(String procdefid) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("applyUser", "chenk");
        variables.put("stationId", "22");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(procdefid, variables);
//        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
//        LOG.info("task:" + GSON.toJson(task));
    }

    /**
     * 用户提交信息
     *
     * @return
     * @throws ParseException
     */
    @GetMapping("/submit")
    public String submit() throws ParseException {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("chenk").list();
        for (Task task : tasks) {
            LOG.info("任务ID:" + task.getId());
            LOG.info("任务名称：" + task.getName());
            LOG.info("任务的创建时间：" + task.getCreateTime());
            LOG.info("任务的代理人：" + task.getAssignee());
            LOG.info("流程实例ID：" + task.getProcessInstanceId());
            LOG.info("执行对象ID：" + task.getExecutionId());
            LOG.info("流程定义ID：" + task.getProcessDefinitionId());
        }
        Project project = addProject();
        Map<String, Object> variables = new HashMap<>();
        variables.put("startTime", calculateDate(sdf2.parse("2020-01-01 10:00"), 0, 10, 0));
        variables.put("endTime", calculateDate(sdf2.parse("2020-01-01 10:00"), 0, 12, 0));
        variables.put("refId", project.getId());
        taskService.complete(tasks.get(0).getId(), variables);
        return "success";
    }

    public Project addProject() {
        Project project = new Project();
        project.setName("0120测试！");
        project.setCode("2020012001");
        project.setOperator("chenk");
        project.setOperateTime(new Date());
        return projectRespository.save(project);
    }

    /**
     * 用户审核
     *
     * @param name
     * @param answer
     * @return
     */
    @GetMapping("/check")
    public String check(String name, boolean answer) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateOrAssigned(name).list();
        for (Task task : tasks) {
            LOG.info("任务ID:" + task.getId());
            LOG.info("任务名称：" + task.getName());
            LOG.info("任务的创建时间：" + task.getCreateTime());
            LOG.info("任务的代理人：" + task.getAssignee());
            LOG.info("流程实例ID：" + task.getProcessInstanceId());
            LOG.info("执行对象ID：" + task.getExecutionId());
            LOG.info("流程定义ID：" + task.getProcessDefinitionId());
        }
        if (tasks.size() > 0) {
            Map<String, Object> variables = new HashMap<>();
            variables.put("answer", answer);
            taskService.complete(tasks.get(0).getId(), variables);
            return "success";
        }
        return "fail";
    }

    @GetMapping("/addUserAndRole")
    public String addUserAndRole() {
        String[] users = {"chenk", "zhuguan", "jingli", "renshi"};
        String[] groups = {"user", "supervisor", "manager", "personnel"};
        User user;
        Group group;
        for (int i = 0; i < groups.length; i++) {
            user = new UserEntity();
            user.setId(users[i]);
            user.setFirstName(users[i]);
            user.setPassword("111111");
            identityService.saveUser(user);

            group = new GroupEntity();
            group.setId(groups[i]);
            identityService.saveGroup(group);
            identityService.createMembership(users[i], groups[i]);
        }
        return "success";
    }

    public Date calculateDate(Date date, Integer days, Integer hour, Integer min) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(calendar.HOUR_OF_DAY, hour);
        calendar.set(calendar.MINUTE, min);
        calendar.set(calendar.SECOND, 0);
        calendar.set(calendar.MILLISECOND, 0);
        Date result = DateUtils.addDays(calendar.getTime(), days);
        return result;
    }

    @GetMapping("/query/project")
    public List<Project> queryProject(int procId) {
        return projectRespository.findByProcId(procId);
    }
}
