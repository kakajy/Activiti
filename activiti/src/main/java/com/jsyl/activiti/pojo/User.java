package com.jsyl.activiti.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.activiti.engine.impl.persistence.entity.UserEntity;

import javax.persistence.*;

/**
 * @Author chenk
 * @create 2019/12/5 14:29
 */
@Entity
@Data
@Table(name = "tb_user")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;
}
