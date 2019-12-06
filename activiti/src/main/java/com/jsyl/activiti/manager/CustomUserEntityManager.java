package com.jsyl.activiti.manager;

import com.google.gson.Gson;
import com.jsyl.activiti.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        User user = new UserEntity();
        user.setId(userBean.getId());
        user.setPassword(userBean.getPassword());
        user.setLastName(userBean.getName());
        return user;
    }
}
