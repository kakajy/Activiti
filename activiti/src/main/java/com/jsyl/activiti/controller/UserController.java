package com.jsyl.activiti.controller;

import com.jsyl.activiti.manager.CustomUserEntityManager;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author chenk
 * @create 2019/12/5 14:53
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private CustomUserEntityManager manager;

    @GetMapping("/query")
    public User test() {
        System.out.println("-----test-----");
        return manager.findUserById("1");
    }

    //    @GetMapping("/check")
//    public boolean getUser() {
//        // 调用引擎,初始化
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        IdentityService identityService = processEngine.getIdentityService();
//        return identityService.checkPassword("chenk", "12456");
//    }
//    @GetMapping("/test2")
//    public List<Task> test2() {
//        return taskService.createTaskQuery().list();
//    }
}
