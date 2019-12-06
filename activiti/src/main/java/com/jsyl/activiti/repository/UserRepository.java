package com.jsyl.activiti.repository;

import com.jsyl.activiti.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author chenk
 * @create 2019/12/5 14:26
 */
public interface UserRepository extends JpaRepository<User, String> {
}
