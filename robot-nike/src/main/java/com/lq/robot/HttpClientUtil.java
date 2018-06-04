package com.lq.robot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/*
 * 利用HttpClient进行post请求的工具类
 */
public class HttpClientUtil {
    private String cookies = "";
    HttpClient httpClient = null;
    public void request(){
        String url = "https://www.nike.com/cn/zh_cn/";
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = doPost(httpPost);
        String result = responseString(response);
        System.out.println(result);
        Header[] cookieHeaders = response.getHeaders("set-cookie");
        for(Header header : cookieHeaders){
            if(!cookies.equals("") && !cookies.endsWith(";")) {
                cookies += ";";
            }
            cookies +=header.getValue();
        }
        requestLogin1();
    }

    private String responseString(HttpResponse response){
        String result="";
        if (response != null) {
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                try {
                    result = EntityUtils.toString(resEntity, "utf-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    public void requestLogin1(){
        String url = "https://unite.nike.com/login?appVersion=435&experienceVersion=361&uxid=com.nike.commerce.nikedotcom.web&locale=zh_CN&backendEnvironment=identity&browser=Google%20Inc.&os=undefined&mobile=false&native=false&visit=2&visitor=c32921a7-1f9c-4b25-a70c-01625e013350";
        HttpOptions httpOptions = new HttpOptions(url);
        setLoginHeader1(httpOptions);
        HttpResponse response = doOptions(httpOptions);
        String result = responseString(response);
        System.out.println(result);
        System.out.println("------------------------------------------------");
//        Header[] cookieHeaders = response.getHeaders("set-cookie");
//        cookies="";
//        for(Header header : cookieHeaders){
//            if(!cookies.endsWith(";")) {
//                cookies += ";";
//            }
//            cookies +=header.getValue();
//        }
        reqeustLogin2(url);
    }

    private void reqeustLogin2(String url){
        HttpPost httpPost = new HttpPost(url);
        setLoginHeader2(httpPost);
        String param = "{\"username\":\"tianshi139803@126.com\",\"password\":\"liu123456L\",\"client_id\":\"HlHa2Cje3ctlaOqnxvgZXNaAs7T9nAuH\",\"ux_id\":\"com.nike.commerce.nikedotcom.web\",\"grant_type\":\"password\"}";
        setRequestPayload(httpPost,param);
        HttpResponse httpResponse = doPost(httpPost);
        String result = responseString(httpResponse);
        System.out.println("-------------------------------");
        System.out.println(result);
    }

    private HttpClient getHttpClient(){
        if(httpClient == null){
            try {
                httpClient = new SSLClient();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return httpClient;
    }

    public HttpResponse doPost(HttpPost httpPost) {
        HttpResponse response = null;
        try {
            printRequest(httpPost);
            response = getHttpClient().execute(httpPost);
            printResponse(response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

    public HttpResponse doOptions(HttpOptions httpOptions){
        HttpResponse response = null;
        try {
            printRequest(httpOptions);
            response = getHttpClient().execute(httpOptions);
            printResponse(response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

    private void printResponse(HttpResponse response){
        Header[] headers = response.getAllHeaders();
        System.out.println("response header:");
        for(Header header : headers){
            System.out.println(header.getName() +":"+header.getValue());
        }
    }


    private void printRequest(HttpRequestBase httpRequestBase){
        Header[] requests = httpRequestBase.getAllHeaders();
        System.out.println("request header:");
        for(Header request : requests){
            System.out.println(request.getName()+":"+request.getValue());
        }
    }

    private void setRequestPayload(HttpPost httpPost,String string){
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(string,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPost.setEntity(stringEntity);
    }

    private void setParamValue( HttpPost httpPost,Map<String, String> map, String charset) throws UnsupportedEncodingException {
        //设置参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, String> elem = (Entry<String, String>) iterator.next();
            list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
        }
        if (list.size() > 0) {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
            httpPost.setEntity(entity);
        }
    }

    private void setLoginHeader2(HttpPost httpPost) {
        httpPost.setHeader(":authority","unite.nike.com");
        httpPost.setHeader(":method","POST");
        httpPost.setHeader(":path","/login?appVersion=435&experienceVersion=361&uxid=com.nike.commerce.nikedotcom.web&locale=zh_CN&backendEnvironment=identity&browser=Google%20Inc.&os=undefined&mobile=false&native=false&visit=1&visitor=878d9bb9-ea87-424f-87db-59a66593b046");
        httpPost.setHeader(":scheme","https");
        httpPost.setHeader("accept","*/*");
        httpPost.setHeader("accept-encoding","gzip, deflate, br");
        httpPost.setHeader("accept-language","zh-CN,zh;q=0.9");
        httpPost.setHeader("content-type","application/json");
        httpPost.setHeader("cookie","AnalysisUserId=210.192.119.45.53631527566944881; NIKE_COMMERCE_COUNTRY=CN; NIKE_COMMERCE_LANG_LOCALE=zh_CN; guidU=4ca6bd2c-1098-4fc7-b190-f1b04fec2428; neo.swimlane=51; dreams_sample=47; _gscu_207448657=275669547dl0db71; ajs_user_id=null; ajs_group_id=null; ajs_anonymous_id=%220b3e7d9a-34c9-4fe3-a8b0-79bbd3fa2ecf%22; _smt_uid=5b0cd26a.43b13900; RES_TRACKINGID=313986637794354; ResonanceSegment=1; NIKE_COMMERCE_CCR=1527567080044; CONSUMERCHOICE_SESSION=t; CONSUMERCHOICE=cn/zh_cn; siteCatalyst_sample=61; dreamcatcher_sample=25; neo_sample=62; neo.experiments=%7B%22main%22%3A%7B%223698-interceptor%22%3A%22a%22%7D%2C%22snkrs%22%3A%7B%7D%2C%22ocp%22%3A%7B%7D%7D; lls=3; _abck=9DBBDD054807383F131FFB0CEC6AA813D2C0772DF314000060D20C5B7AFAA738~0~1kLnceily3bJSJ9Q4OdzoKXWFf5BZhJD132poroMB1w=~-1~-1; bm_sz=6D21E0C8EF698546AA219356595EEBE4~QAAQFXfA0iu2dJdjAQAAFNL9yAeT4AgoENECJPQNQ/9KxYmO9yhylZAx4Bg306OswND5o+5GUxNCKzKgsbU0Uit/vDGMp/iwJYGcFsjRO8HePwAFB8D7y3hRqyqh+iuMgOFi886wqHCBD2y/y3h1les7dO87zKtB/rysk/O5evB+9jLsAzJukyfZWkAI; ak_bmsc=80095E436FE6EC9549D601A2E1B21633D2C07715EA380000DBBB145BEC58A950~pl5coT6Lm3YQlltEqJ7jnTU/W8QLOdWqHkTxbYRAg9Ye6Yc9LeZ3VwWIByjLzEhtxmVKf706FbdbspKq32Ov+r/P3J4okTbgB3fbV7/HVArIXSmjzxh0ITJX5kgcIHVgh2msFGAIdnlf+7aRgqjiA4XjecBFt4jP+u+9MBN1gUyROIDWVN01MBW6jGFh7Dm5hbazReqVMEEyLLM5jDwCb58cfff8jHgEhf38t19jHPoRntipgO7iSj12yNNaduKbpx9MfPeSBV01p/pLIgOTUjgjXwlS1m098b95Wn+FxMpW8UYojRMVqQRxaIfaMllp/HV97zJSzEZR+6pKrKgkjaEg==; AKA_A2=A; nike_locale=cn/zh_cn; mm_wc_pmt=1; guidS=17287095-d328-43ef-d48e-c0214162a5ed; _gscbrs_207448657=1; AMCVS_F0935E09512D2C270A490D4D%40AdobeOrg=1; AMCV_F0935E09512D2C270A490D4D%40AdobeOrg=2121618341%7CMCIDTS%7C17686%7CMCMID%7C35431566262966316330772367245609357384%7CMCAAMLH-1528258366%7C11%7CMCAAMB-1528690293%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1528092693s%7CNONE%7CMCAID%7CNONE; utag_main=_st:1528087425505$ses_id:1528085983222%3Bexp-session; guidSTimestamp=1528085471108|1528085626245; _gscs_207448657=28085491rphrtd71|pv:3; bm_sv=512C7D0A95B6DBB833AD9C9618404321~/ySNlifPFUcUZ/acoJoBOpYJf154hbzuazUpnPDFt3juyXda2Ce1UpoKb6GLIZlJ7d0gFniHU4I77ZDPDq0Af43BPEUC8Lzw5daI/QVdn6eJeizgYJOh32VoldbZuTnM3DFsu2J2awIABOkThyaMGQ==; exp.swoosh.user=%7B%22granted%22%3A0%7D; RES_SESSIONID=335318428634909; ppd=homepage%7Cnikecom%3Ehomepage; RT=\"dm=nike.com&si=05859b72-8963-4e6b-a58c-2a2409248fe5&ss=1528085467526&sl=3&tt=60558&obo=0&sh=1528085639038%3D3%3A0%3A60558%2C1528085595217%3D2%3A0%3A47061%2C1528085491128%3D1%3A0%3A23596&bcn=%2F%2F36fb6d10.akstat.io%2F\"; s_sess=%20c51%3Dhorizontal%3B%20s_cc%3Dtrue%3B%20tp%3D4301%3B%20prevList2%3D%3B%20s_ppv%3Dunified%252520profile%25253Elogin%252C9%252C9%252C400%3B; s_pers=%20s_dfa%3Dnikecomprod%7C1528087425962%3B%20c58%3Dno%2520value%7C1528087599037%3B");
        httpPost.setHeader("origin","https://www.nike.com");
        httpPost.setHeader("referer","https://www.nike.com/cn/zh_cn/");
        httpPost.setHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        httpPost.setHeader("appVersion","435");
        httpPost.setHeader("experienceVersion","361");
        httpPost.setHeader("uxid","com.nike.commerce.nikedotcom.web");
        httpPost.setHeader("locale","zh_CN");
        httpPost.setHeader("backendEnvironment","identity");
        httpPost.setHeader("browser","Google Inc.");
        httpPost.setHeader("os","undefined");
        httpPost.setHeader("mobile","false");
        httpPost.setHeader("native","false");
        httpPost.setHeader("visit","2");
        httpPost.setHeader("visitor","9be0d89a-6cd2-455a-a98b-8ecb4d593ec8");
        httpPost.setHeader("cookie",cookies);
    }

    private void setLoginHeader1(HttpOptions httpOptions){
        httpOptions.setHeader(":authority","unite.nike.com");
        httpOptions.setHeader(":method","OPTIONS");
        httpOptions.setHeader(":path"," /login?appVersion=435&experienceVersion=361&uxid=com.nike.commerce.nikedotcom.web&locale=zh_CN&backendEnvironment=identity&browser=Google%20Inc.&os=undefined&mobile=false&native=false&visit=2&visitor=9be0d89a-6cd2-455a-a98b-8ecb4d593ec8");
        httpOptions.setHeader(":scheme","https");
        httpOptions.setHeader("accept","*/*");
        httpOptions.setHeader("accept-encoding","gzip, deflate, br");
        httpOptions.setHeader("accept-language","zh-CN,zh;q=0.9");
        httpOptions.setHeader("access-control-request-headers","content-type");
        httpOptions.setHeader("access-control-request-method","POST");
        httpOptions.setHeader("origin","https://www.nike.com");
        httpOptions.setHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        httpOptions.setHeader("cookie",cookies);
    }
}
