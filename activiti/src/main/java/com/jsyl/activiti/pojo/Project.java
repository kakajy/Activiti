package com.jsyl.activiti.pojo;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author chenk
 * @create 2020/1/20 9:12
 */
@Entity
@Data
@Table(name = "TB_PROJECT")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "jpa-uuid")
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "operator")
    private String operator;

    @Column(name = "operatetime")
    private Date operateTime;
}
