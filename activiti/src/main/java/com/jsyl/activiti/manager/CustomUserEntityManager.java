package com.jsyl.activiti.manager;

import com.google.gson.Gson;
import com.jsyl.activiti.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author chenk
 * @create 2019/12/5 14:08
 */
@Component
@Slf4j
public class CustomUserEntityManager extends UserEntityManager {
    @Autowired
    private UserRepository userRepository;

    Gson GSON = new Gson();

    @Override
    public User findUserById(String userId) {
        log.info("id:" + userId);
        com.jsyl.activiti.pojo.User userBean = userRepository.getOne(userId);
        UserEntity user = new UserEntity();
        user.setId(userBean.getId());
        user.setPassword(userBean.getPassword());
        user.setLastName(userBean.getName());
        return user;
    }

    @Override
    public Boolean checkPassword(String userId, String password) {
        System.out.println("userId:" + userId + "   password:" + password);
        return false;
    }

    /**
     * http://localhost:8081/identity/users
     */
    @Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        List<User> list = new ArrayList<>();
        User user = new UserEntity();
        user.setLastName("111");
        user.setPassword("222");
        user.setEmail("123@qq.com");
        list.add(user);
        return list;
    }
}
