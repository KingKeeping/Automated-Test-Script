package com.get.automation.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DynamicTableNames {
    // 存储表名的静态列表
    private static final List<String> tableNames = new ArrayList<>();

    // 记录生成表名的日期（格式：yyyyMMdd）
    private static String generatedDate;

    // 静态初始化块（首次加载类时执行）
    static {
        refreshTableNames();
    }

    // 刷新表名列表（根据需要手动调用）
    public static void refreshTableNames() {
        // 获取当前日期
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        // 如果日期未变化则无需刷新
        if (today.equals(generatedDate)) return;

        // 清空旧数据并生成新表名
        tableNames.clear();
        for (int i = 1; i <= 15; i++) {
            tableNames.add(String.format("Table_%s_%02d", today, i));
        }
        generatedDate = today;
    }

    // 获取当前表名列表（自动带日期）
    public static List<String> getTableNames() {
        return new ArrayList<>(tableNames); // 返回副本避免意外修改
    }
}
