package com.get.automation.config;

import java.io.FileInputStream;
import java.io.IOException;

public class MyProperties {


   public static java.util.Properties properties ;

    public static java.util.Properties getProperties() {
        // 创建 Properties 对象
         properties = new java.util.Properties();

        try {
            // 加载 properties 文件
            FileInputStream input = new FileInputStream("src/main/resources/config.properties");
            try {
                properties.load(input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            /*// 读取属性
            String Url = properties.getProperty("url");
            String mobile = properties.getProperty("mobile");
            String Pwd = properties.getProperty("pwd");*/

            // 关闭输入流
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
