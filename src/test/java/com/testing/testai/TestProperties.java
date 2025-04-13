package com.testing.testai;


import com.get.automation.config.MyProperties;
import com.get.automation.utils.DynamicTableNames;

import java.util.List;

public class TestProperties {
    public static void main(String[] args) {
      /*  java.util.Properties properties = MyProperties.getProperties();
        String mobile = properties.getProperty("mobile");
        String pwd = properties.getProperty("Pwd");
        System.out.println(mobile);
        System.out.println(pwd);
        properties.getProperty("kuser");
        System.out.println(properties.getProperty("kuser"));*/
        List<String> tableNames = DynamicTableNames.getTableNames();
        for (String tableName : tableNames) {
            System.out.println(tableName);
        }
    }

}
