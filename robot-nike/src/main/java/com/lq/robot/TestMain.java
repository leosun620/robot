package com.lq.robot;

import java.util.HashMap;
import java.util.Map;
//对接口进行测试
public class TestMain {
    //private String url = "https://unite.nike.com/appInitialization?appVersion=435&experienceVersion=361&uxid=com.nike.commerce.nikedotcom.web&locale=zh_CN&backendEnvironment=identity&browser=Google%20Inc.&os=undefined&mobile=false&native=false&visit=&visitor=&clientId=HlHa2Cje3ctlaOqnxvgZXNaAs7T9nAuH&status=success&uxId=com.nike.commerce.nikedotcom.web&isAndroid=false&isIOS=false&isMobile=false&isNative=false&timeElapsed=632";
    //private String url = "https://unite.nike.com/login?appVersion=435&experienceVersion=361&uxid=com.nike.commerce.nikedotcom.web&locale=zh_CN&backendEnvironment=identity&browser=Google%20Inc.&os=undefined&mobile=false&native=false&visit=2&visitor=9be0d89a-6cd2-455a-a98b-8ecb4d593ec8";
    //private String url = "https://unite.nike.com/login?appVersion=435&experienceVersion=361&uxid=com.nike.commerce.nikedotcom.web&locale=zh_CN&backendEnvironment=identity&browser=Google%20Inc.&os=undefined&mobile=false&native=false&visit=1&visitor=878d9bb9-ea87-424f-87db-59a66593b046";
    private String url = "https://www.nike.com/cn/zh_cn/";
    private String charset = "utf-8";
    private HttpClientUtil httpClientUtil = null;

    public TestMain(){
        httpClientUtil = new HttpClientUtil();
    }

    public void test(){

        String httpOrgCreateTest = url ;//+ "httpOrg/create";
        Map<String,String> createMap = new HashMap<String,String>();
        /*createMap.put("username","tianshi139803@126.com");
        createMap.put("password","liu123456L");
        createMap.put("client_id","HlHa2Cje3ctlaOqnxvgZXNaAs7T9nAuH");
        createMap.put("ux_id","com.nike.commerce.nikedotcom.web");
        createMap.put("grant_type","password");*/
        httpClientUtil.requestLogin1();
        //System.out.println("result:"+httpOrgCreateTestRtn);
    }

    public static void main(String[] args){
        TestMain main = new TestMain();
        main.test();
    }
}