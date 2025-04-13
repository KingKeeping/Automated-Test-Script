package com.get.automation.utils;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;


public class FileDataInserter {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/kmoketest";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    private static final int BATCH_SIZE = 15; // 总插入数量
    private static  Connection conn;
    // 新增数据结构存储时间线文件
    private static final Map<String, List<String>> timelineFiles = new LinkedHashMap<>();
    private static final String TODAY = "today";
    private static final String LAST_7_DAYS = "last_7_days";
    private static final String EARLIER = "earlier";




    public static Map<String, List<String>> insertTestData() {
        timelineFiles.clear(); // 清空旧数据
        timelineFiles.put(TODAY, new ArrayList<>());
        timelineFiles.put(LAST_7_DAYS, new ArrayList<>());
        timelineFiles.put(EARLIER, new ArrayList<>());

        try  {
            conn=DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            // 关闭自动提交以提升批量插入性能
            conn.setAutoCommit(false);

            // 准备插入语句
            String sql = "INSERT INTO file_info (file_name, file_type, file_size, created_at, updated_at, "
                    + "storage_path, storage_engine, creator_id, owner_id, file_hash) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // 生成测试数据
                generateTestData(pstmt);

                // 执行批量插入
                int[] results = pstmt.executeBatch();
                conn.commit();
                System.out.println("成功插入 " + results.length + " 条记录");
            }
        } catch (SQLException e) {
            System.err.println("数据库操作异常: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return timelineFiles;
    }



    private static void generateTestData(PreparedStatement pstmt) throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        
        // 生成今天的时间数据（5条）
        for (int i = 1; i <= 5; i++) {
            LocalDateTime time = now.minusHours(i);
            String fileName= addFileRecord(pstmt,
                    "Table_Today_",
                    time, // 当前时间往前推i小时
                    i
            );
            timelineFiles.get(TODAY).add(fileName);
        }

        // 生成近七天的时间数据（5条）
        for (int i = 1; i <= 5; i++) {
            LocalDateTime time = now.minusDays(i).minusHours(1);
            String fileName = addFileRecord(pstmt,
                    "Table_7Days_",
                    time, // 3天前+i小时
                    i
            );
            timelineFiles.get(LAST_7_DAYS).add(fileName);
        }

        // 生成更早的时间数据（5条）
        for (int i = 8; i <= 12; i++) {
            LocalDateTime time = now.minusDays(i).minusHours(1);
            String fileName = addFileRecord(pstmt,
                    "Table_Earlier_",
                    time, // 8天前+i小时
                    i
            );
            timelineFiles.get(EARLIER).add(fileName);
        }
    }
    // 新增数据清理方法
    public static void cleanTestData() {
        try {
            conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            conn.setAutoCommit(false);

            String deleteSQL = "DELETE FROM file_info WHERE file_name LIKE ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                pstmt.setString(1, "Table_%");
                int affectedRows = pstmt.executeUpdate();
                conn.commit();
                System.out.println("已清理 " + affectedRows + " 条测试数据");
            }
        } catch (SQLException e) {
            System.err.println("数据清理失败: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void cleanTestData(String fileName) {
        try  {
            conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            conn.setAutoCommit(false);
            String deleteSQL = "DELETE FROM file_info WHERE file_name=?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                pstmt.setString(1, fileName);
                int affectedRows = pstmt.executeUpdate();
                conn.commit();
               // System.out.println("已清理 " + affectedRows + " 条测试数据");
            }
        } catch (SQLException e) {
            System.err.println("数据清理失败: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private static String addFileRecord(PreparedStatement pstmt,
                                    String baseName, 
                                    LocalDateTime createTime,
                                    int index) throws SQLException {
        // 生成唯一文件名
        String fileName = baseName + "_" + UUID.randomUUID().toString().substring(0, 6);
        
        // 设置参数
        pstmt.setString(1, fileName); // file_name
        pstmt.setString(2, "test");   // file_type
        pstmt.setLong(3, 1024 * index); // file_size
        pstmt.setTimestamp(4, Timestamp.valueOf(createTime)); // created_at
        pstmt.setTimestamp(5, Timestamp.valueOf(createTime)); // updated_at
        pstmt.setString(6, "/storage/" + fileName); // storage_path
        pstmt.setString(7, "local"); // storage_engine
        pstmt.setInt(8, 1); // creator_id (测试用户ID)
        pstmt.setInt(9, 1); // owner_id
        pstmt.setString(10, UUID.randomUUID().toString()); // file_hash (模拟)
        pstmt.addBatch();
        return fileName; // 返回生成的文件名
    }


}