package com.lq.robot;

import java.util.HashMap;
import java.util.Map;
//对接口进行测试
public class TestMain {
    private String url = "https://unite.nike.com/login?appVersion=431&experienceVersion=360&uxid=com.nike.commerce.nikedotcom.web&locale=zh_CN&backendEnvironment=identity&browser=Google%20Inc.&os=undefined&mobile=false&native=false&visit=1&visitor=542767df-ea1a-4f8d-8c36-b72d58c2fbb2";
    private String charset = "utf-8";
    private HttpClientUtil httpClientUtil = null;

    public TestMain(){
        httpClientUtil = new HttpClientUtil();
    }

    public void test(){
        //{"username":"1@11.com",
        // "password":"1",
        // "client_id":"HlHa2Cje3ctlaOqnxvgZXNaAs7T9nAuH",
        // "ux_id":"com.nike.commerce.nikedotcom.web",
        // "grant_type":"password"}
        String httpOrgCreateTest = url ;//+ "httpOrg/create";
        Map<String,String> createMap = new HashMap<String,String>();
        createMap.put("username","tianshi139803@126.com");
        createMap.put("password","liu123456L");
        createMap.put("client_id","HlHa2Cje3ctlaOqnxvgZXNaAs7T9nAuH");
        createMap.put("ux_id","com.nike.commerce.nikedotcom.web");
        createMap.put("grant_type","password");
        String httpOrgCreateTestRtn = httpClientUtil.doPost(httpOrgCreateTest,createMap,charset);
        System.out.println("result:"+httpOrgCreateTestRtn);
    }

    public void openIndex(){
        String httpOrgCreateTest = "https://www.nike.com/cn/zh_cn/";
        Map<String,String> createMap = new HashMap<String,String>();
        String httpOrgCreateTestRtn = httpClientUtil.doPost(httpOrgCreateTest,createMap,charset);
        System.out.println("result:"+httpOrgCreateTestRtn);
    }
    public static void main(String[] args){
        TestMain main = new TestMain();
        main.test();
    }
}