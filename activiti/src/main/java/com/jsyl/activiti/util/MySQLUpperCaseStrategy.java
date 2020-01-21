package com.jsyl.activiti.util;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * @Author chenk
 * @create 2020/1/20 13:47
 */
public class MySQLUpperCaseStrategy extends PhysicalNamingStrategyStandardImpl {

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {

        String tableName = name.getText().toUpperCase();
        return name.toIdentifier(tableName);
    }
}
