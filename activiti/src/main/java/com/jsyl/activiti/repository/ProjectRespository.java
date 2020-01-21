package com.jsyl.activiti.repository;

import com.jsyl.activiti.pojo.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author chenk
 * @create 2020/1/20 9:14
 */
public interface ProjectRespository extends JpaRepository<Project, String> {

    @Query(value = "select * from TB_PROJECT " +
            "where ID in " +
            "(select TEXT_ from ACT_RU_VARIABLE where EXECUTION_ID_ = ?1 AND NAME_ = 'refId')", nativeQuery = true)
    List<Project> findByProcId(int procId);
}
