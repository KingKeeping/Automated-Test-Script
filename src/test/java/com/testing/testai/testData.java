package com.testing.testai;
import com.get.automation.utils.FileDataInserter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class testData {
    public static void main(String[] args) {
        Map<String, List<String>> stringListMap = FileDataInserter.insertTestData();
        // 获取某个键对应的 List
        List<String> todayFiles = stringListMap.get("today");

        // 处理可能的空指针问题（推荐使用空安全方法）
        if (todayFiles != null) {
            for (String file : todayFiles) {
                System.out.println(file);
            }
        }
       // FileDataInserter.cleanTestData();
    }
}

class test{
    public static void main(String[] args) {
        Map<String, List<String>> stringListMap = FileDataInserter.insertTestData();
        //System.out.println(stringListMap.values());
        //Collection<List<String>> values = stringListMap.values();
        int pageCount=0;
        for (List<?> list : stringListMap.values()) {
            pageCount += list.size();
        }
        pageCount=pageCount/10+1;
        System.out.println(pageCount);

    }






}