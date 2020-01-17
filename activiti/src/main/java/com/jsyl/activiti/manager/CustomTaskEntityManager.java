package com.jsyl.activiti.manager;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.springframework.stereotype.Component;

/**
 * @Author chenk
 * @create 2020/1/6 9:16
 */
@Component
@Slf4j
public class CustomTaskEntityManager extends TaskEntityManager {

    @Override
    public TaskEntity findTaskById(String id) {
        if (id == null) {
            throw new ActivitiIllegalArgumentException("Invalid task id : null");
        }
        return new TaskEntity();
    }
}
