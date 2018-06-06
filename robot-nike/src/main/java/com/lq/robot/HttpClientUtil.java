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
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/*
 * 利用HttpClient进行post请求的工具类
 */
public class HttpClientUtil {
    //private String cookies = "RT=\"sl=0&ss=1528301203177&tt=0&obo=0&bcn=%2F%2F36fb68c2.akstat.io%2F&sh=&dm=nike.com&si=9e40fbb3-f707-4bbc-9792-ad5303028ad9&r=https%3A%2F%2Fwww.nike.com%2Fcn%2Fzh_cn%2F&ul=1528301246911\"";
    private CookieStore cookieStore = null;
    //创建一个HttpContext对象，用来保存Cookie
    //HttpClientContext httpClientContext = HttpClientContext.create();
    SSLClient httpClient ;
    public void request(){
        String url = "https://www.nike.com/cn/zh_cn/";
        HttpGet httpGet = new HttpGet(url);
        setIndexPageHeader(httpGet);
        HttpResponse response = doGet(httpGet,"主页");
        String result = responseString(response);
        //System.out.println(result);
        //cookieStore = httpClient.getCookieStore();
       // Header[] cookieHeaders = response.getHeaders("set-cookie");
        //for(Header header : cookieHeaders){
//            if(!cookies.equals("") && !cookies.endsWith(";")) {
//                cookies += ";";
//            }
//            cookies +=header.getValue();
//        }
        requestData();
        requestAppInit();
        requestLogin1();
    }

    private void setIndexPageHeader(HttpGet httpGet) {
        //httpGet.setHeader("Cookie",cookies);
        httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding","gzip, deflate, br");
        httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.9");
        httpGet.setHeader("Cache-Control","max-age=0");
        httpGet.setHeader("Connection","keep-alive");
        httpGet.setHeader("Cookie","RT=\"sl=0&ss=1528301203177&tt=0&obo=0&bcn=%2F%2F36fb68c2.akstat.io%2F&sh=&dm=nike.com&si=9e40fbb3-f707-4bbc-9792-ad5303028ad9&r=https%3A%2F%2Fwww.nike.com%2Fcn%2Fzh_cn%2F&ul=1528301246911\"");
        httpGet.setHeader("Host","www.nike.com");
        httpGet.setHeader("Upgrade-Insecure-Requests","1");
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
    }

    public void requestData() {
        String url="https://www.nike.com/_bm/_data";
        HttpPost httpPost = new HttpPost(url);
        setRequestDataHeader(httpPost);
        String json="{\"sensor_data\":\"7a74G7m23Vrp0o5c9997316.78-6,2,-36,-495,Mozilla/9.8 (Windows NT 0.9) AppleWebKit/718.21 (KHTML, like Gecko) Chrome/75.5.8417.76 Safari/289.85,uaend,40366,35931749,zh-CN,Gecko,0,2,1,9,028182,7159694,6932,250,7608,195,5,1,0296,,cpen:7,i2:9,dm:2,cwen:6,non:2,opc:3,fc:2,sc:1,wrc:4,isc:1,vib:5,bat:6,x32:9,x76:9,0367,2.68143683424,416297264368,loc:-3,7,-00,-914,do_en,dm_en,t_en-8,4,-84,-523,-0,9,-09,-266,-2,1,-46,-009,-3,3,-41,-269,-7,4,-23,-638,-1,8,-75,-686,-6,2,-36,-494,-3,7,-00,-927,-8,4,-84,-521,-0,9,-09,-276,https://unite.nike.com/session.html-6,7,-43,-757,1,9,7,2,5,0,6,6,1,0093401418422,-284641,65355,2,1,1299,1,9,25,2,1,A598D031C99A7EC43C4F1384CBE0560DD7C59555422B2813136F370BBD47AF2F~-8~pdA9o3YVgjhgxQCJ6BQkxDzSJvYxFSOcGqL+J1RMdqk=~5~-3,3126,-3,-4,87782179-7,4,-23,-627,9,1-5,0,-89,-324,-2-5,0,-89,-337,0,6,2,1,0,7,2-6,2,-36,-09,-6-2,1,-58,-93,41-3,7,-00,-929,092153418-5,0,-89,-323,40171-2,1,-46,-022,;27;-1;10\"}";
        setRequestPayload(httpPost,json);
        HttpResponse response = doPost(httpPost,"_DATA:");
        String result = responseString(response);
        System.out.println(result);

    }

    private void setRequestDataHeader(HttpPost httpPost) {
        httpPost.setHeader(":authority","www.nike.com");
        httpPost.setHeader(":method","POST");
        httpPost.setHeader(":path","/_bm/_data");
        httpPost.setHeader(":scheme","https");
        httpPost.setHeader("accept","*/*");
        httpPost.setHeader("accept-encoding","gzip, deflate, br");
        httpPost.setHeader("accept-language","zh-CN,zh;q=0.9");
        //httpPost.setHeader("content-length","1677");
        //httpPost.setHeader("Cookie" ,getCookies());
        httpPost.setHeader("content-type","application/json");
        httpPost.setHeader("origin","https://www.nike.com");
        httpPost.setHeader("referer","https://www.nike.com/cn/zh_cn/");
        httpPost.setHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        httpPost.setHeader("x-newrelic-id","VQYGVF5SCBAJVlFaAQIH");
    }

    private void requestAppInit() {
        String url = "https://unite.nike.com/appInitialization?appVersion=435&experienceVersion=361&uxid=com.nike.commerce.nikedotcom.web&locale=zh_CN&backendEnvironment=identity&browser=Google%20Inc.&os=undefined&mobile=false&native=false&visit=&visitor=&clientId=HlHa2Cje3ctlaOqnxvgZXNaAs7T9nAuH&status=success&uxId=com.nike.commerce.nikedotcom.web&isAndroid=false&isIOS=false&isMobile=false&isNative=false&timeElapsed=1459";
        HttpGet httpGet = new HttpGet(url);
        setAppInitHeader(httpGet);
        HttpResponse response = doGet(httpGet,"APPinit");
        String result = responseString(response);
        System.out.println(result);

    }

    private void setAppInitHeader(HttpGet httpGet){
       // httpGet.setHeader("Cookie",getCookies());
        httpGet.setHeader(":authority","unite.nike.com");
        httpGet.setHeader(":method","GET");
        httpGet.setHeader(":path","/appInitialization?appVersion=435&experienceVersion=361&uxid=com.nike.commerce.nikedotcom.web&locale=zh_CN&backendEnvironment=identity&browser=Google%20Inc.&os=undefined&mobile=false&native=false&visit=&visitor=&clientId=HlHa2Cje3ctlaOqnxvgZXNaAs7T9nAuH&status=success&uxId=com.nike.commerce.nikedotcom.web&isAndroid=false&isIOS=false&isMobile=false&isNative=false&timeElapsed=1459");
        httpGet.setHeader(":scheme","https");
        httpGet.setHeader("accept","*/*");
        httpGet.setHeader("accept-encoding","gzip, deflate, br");
        httpGet.setHeader("accept-language","zh-CN,zh;q=0.9");
        httpGet.setHeader("origin","https://www.nike.com");
        httpGet.setHeader("referer","https://www.nike.com/cn/zh_cn/");
        httpGet.setHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
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
        String url = "https://unite.nike.com/login?appVersion=435&experienceVersion=361&uxid=com.nike.commerce.nikedotcom.web&locale=zh_CN&backendEnvironment=identity&browser=Google%20Inc.&os=undefined&mobile=false&native=false&visit=1&visitor=7debb18f-2178-4b47-b656-ed217bbb2808";
        HttpOptions httpOptions = new HttpOptions(url);
        setLoginHeader1(httpOptions);
        HttpResponse response = doOptions(httpOptions,"login1 ");
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
        //cookies = "AnalysisUserId=509e0559.56de5f2cd9706; bm_sz=DA557834BA54EF937C66CAB102F46E5E~QAAQH3fA0qmEirhjAQAAudVU1fDH+y9MBDzH1f9QiOOMlS6kd9T1v+QcxzNd95vyPSNw6ELHYmQASJdjdYLhxnr39e10SEgqzh2sh5YFqBcqfHNA/neUJgsYefwVLa8GrEJfv9Jk8qMYuob3/YGpxC/15dGxUzLPsoqOKxxre54jh8cfMOEjormlB73g; ak_bmsc=C11B42E60FFF525C3E65275D7618608BD2C0771F2D30000090E4175B3F409C38~plzXImkmrVDS0zEK0vLu70mV0SnYaSSNbk0VLvnQMjkOkDW8Cmy2WE/teCxKZtJ3qJvnmKZ85MyVt3guLmaGvSy68h226n4VKW1KjxLw7eQO0dfaTqMB77wB5EnlwKvYqLRWD47VX2v/WnGs2TS+V+Kgdhgu4Qf+DJ7CgcAOGpRdsfXIfn1SPyjw5vHEjiVdBAgBpvT6pV95fiZOJew77ue6GZtuHwD9Xmv4l4PSjmWBvJ5dHfs7RtkRBwKstHDaMJ+f8l0j40B1IYqruC9fE1KBpLK6jP3SEkrVXfytm2oYjGgH3FaFlFrJ0Bz7aOF+RLq5jNjvyvRZuHT4J655a/Sg==; AKA_A2=A; NIKE_COMMERCE_COUNTRY=CN; NIKE_COMMERCE_LANG_LOCALE=zh_CN; nike_locale=cn/zh_cn; guidU=4ff378ed-6375-4dd5-a8d6-7bfda375b42b; neo.swimlane=41; dreams_sample=55; guidS=3a710196-1bcc-44f0-e10e-769e195a7098; AMCVS_F0935E09512D2C270A490D4D%40AdobeOrg=1; AMCV_F0935E09512D2C270A490D4D%40AdobeOrg=2121618341%7CMCMID%7C48759906085072709082068505081692928310%7CMCAAMLH-1528897326%7C11%7CMCAAMB-1528897326%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1528299726s%7CNONE%7CMCAID%7CNONE; ajs_anonymous_id=%22e8f605f4-f47f-44ae-b980-0f8a53ea95eb%22; RES_TRACKINGID=997212644527297; NIKE_COMMERCE_CCR=1528292806954; CONSUMERCHOICE_SESSION=t; CONSUMERCHOICE=cn/zh_cn; slCheck=yCHgIDBDljFtxMwP7MCPwOPXL+M6kndrrvS6b3oUt9T8tqFVHNDLGdQsZRR3DDVAgRsqWxnhfAZk7GiAmqGh8SeWsdwPTrytt6U6uZKqM4kf+pR6lumthvxfArvPntlU; lls=3; llCheck=zHVuwtujtq3eTjk5eaeB5mk/+PaDmqmIbj9TdypLibZIuWV739LYGFYIfxOFfMsgaUuqjoSheKa9ZgNHE/kM/wbrPvZeG9px/xM9L8v9lIBBEreX51fDPqFeHe5FyvPqdoK314ZbGqEn8k3D3zWCtQMwt47IDNDSqzox4rVSiUs=; APID=80F3F84B99BAED12F09CBD1F90FB5578.sin-343-app-ap-0; _abck=A023D982C83A5EC14C7F9235CBE6389DD2C07406416B0000289F165BBD83AF1F~0~Za1E1J2nVGxBiWqU3jsxZh7s9TcUu7NSHD0s4K5adHY=~-1~-1; sls=3; CART_SUMMARY=%7B%22profileId%22+%3A%2278fe6469-1a4e-422f-8f8e-1d81532f0e0f%22%2C%22userType%22+%3A%22DEFAULT_USER%22%2C%22securityStatus%22+%3A%224%22%2C%22cartCount%22+%3A0%7D; exp.swoosh.user=%7B%22granted%22%3A0%7D; s_sess=%20tp%3D3997%3B%20s_ppv%3Dnikecom%25253Ehomepage%252C4%252C4%252C150%3B%20c51%3Dhorizontal%3B%20prevList2%3D%3B%20s_cc%3Dtrue%3B; ResonanceSegment=1; RES_SESSIONID=943208289622958; ppd=homepage%7Cnikecom%3Ehomepage; _gscu_207448657=27685784hwd2np14; _gscs_207448657=28292528g6nzk814|pv:10; _gscbrs_207448657=1; _smt_uid=5b17e4b1.10445faa; ajs_group_id=null; ajs_user_id=null; bm_sv=BFA1C323FB471E9B810BCE3BB8FC1335~Cw4tHLnHc/S/fcc8eS5wIWIUVHap+63H6IUnzwpA/NJGRQgcvrWLTnlIJS/nSZzGDzCpO58gfX0j/RgtXUAQOD9032l4D00BIA8CC1gGt8JpxRLoeluqI3l9lWaB3lnSHbmH3Ng1kJD7lI9luzxWOv+LHkA/urRIXgX/Pncqutw=; RT=\"sl=10&ss=1528292501120&tt=190909&obo=0&sh=1528294881894%3D10%3A0%3A190909%2C1528294836461%3D9%3A0%3A184860%2C1528294685133%3D8%3A0%3A172662%2C1528294626124%3D7%3A0%3A164168%2C1528294490007%3D6%3A0%3A155309&dm=nike.com&si=9e40fbb3-f707-4bbc-9792-ad5303028ad9&bcn=%2F%2F36fb6d10.akstat.io%2F&r=https%3A%2F%2Fwww.nike.com%2Fcn%2Fzh_cn%2F&ul=1528294961706&hd=1528294962118\"; utag_main=_st:1528296763692$ses_id:1528293118809%3Bexp-session; s_pers=%20c58%3Dno%2520value%7C1528296682069%3B%20s_dfa%3Dnikecomprod%7C1528296763978%3B; mm_wc_pmt=1; neo.experiments=%7B%22main%22%3A%7B%223698-interceptor%22%3A%22a%22%7D%7D; guidSTimestamp=1528292523175|1528294964932";
        reqeustLogin2(url);
    }

    public void reqeustLogin2(String url){
        HttpPost httpPost = new HttpPost(url);
        setLoginHeader2(httpPost);
        String param = "{\"username\":\"tianshi139803@126.com\",\"password\":\"liu123456L\",\"client_id\":\"HlHa2Cje3ctlaOqnxvgZXNaAs7T9nAuH\",\"ux_id\":\"com.nike.commerce.nikedotcom.web\",\"grant_type\":\"password\"}";
        setRequestPayload(httpPost,param);
        HttpResponse httpResponse = doPost(httpPost,"Login2 ");
        String result = responseString(httpResponse);
        System.out.println("-------------------------------");
        System.out.println(result);
    }

    private SSLClient getHttpClient(){
         try {
            httpClient = new SSLClient();
            httpClient.setCookieStore(cookieStore);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpClient;
    }

    public HttpResponse doPost(HttpPost httpPost,String name) {
        return doReqeust(httpPost,name);
    }

    public HttpResponse doReqeust(HttpRequestBase httpRequestBase,String name){
        HttpResponse response = null;
        try {

            response = getHttpClient().execute(httpRequestBase);
            cookieStore = httpClient.getCookieStore();
            System.out.println(httpClient);
            printRequest(httpRequestBase,name);
            printResponse(response,name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

    public HttpResponse doGet(HttpGet httpGet,String name){
        return doReqeust(httpGet,name);
    }

    public HttpResponse doOptions(HttpOptions httpOptions,String name){

        return doReqeust(httpOptions,name);
    }

    private void printResponse(HttpResponse response,String name){
        Header[] headers = response.getAllHeaders();
        System.out.println("-------------------");
        System.out.println(name+"response header:");
        for(Header header : headers){
            System.out.println(header.getName() +":"+header.getValue());
        }
    }


    private void printRequest(HttpRequestBase httpRequestBase,String name){
        Header[] requests = httpRequestBase.getAllHeaders();
        System.out.println(name+"request header:");
        for(Header request : requests){
            System.out.println(request.getName()+":"+request.getValue());
        }
    }

    private void setRequestPayload(HttpPost httpPost,String string){
        StringEntity stringEntity = null;
        stringEntity = new StringEntity(string,"utf-8");
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
        httpPost.setHeader(":path","/login?appVersion=435&experienceVersion=361&uxid=com.nike.commerce.nikedotcom.web&locale=zh_CN&backendEnvironment=identity&browser=Google%20Inc.&os=undefined&mobile=false&native=false&visit=1&visitor=7debb18f-2178-4b47-b656-ed217bbb2808");
        httpPost.setHeader(":scheme","https");
        httpPost.setHeader("accept","*/*");
        httpPost.setHeader("accept-encoding","gzip, deflate, br");
        httpPost.setHeader("accept-language","zh-CN,zh;q=0.9");
        httpPost.setHeader("content-type","application/json");
        //httpPost.setHeader("cookie","AnalysisUserId=210.192.119.45.53631527566944881; NIKE_COMMERCE_COUNTRY=CN; NIKE_COMMERCE_LANG_LOCALE=zh_CN; guidU=4ca6bd2c-1098-4fc7-b190-f1b04fec2428; neo.swimlane=51; dreams_sample=47; _gscu_207448657=275669547dl0db71; ajs_user_id=null; ajs_group_id=null; ajs_anonymous_id=%220b3e7d9a-34c9-4fe3-a8b0-79bbd3fa2ecf%22; _smt_uid=5b0cd26a.43b13900; RES_TRACKINGID=313986637794354; ResonanceSegment=1; NIKE_COMMERCE_CCR=1527567080044; CONSUMERCHOICE_SESSION=t; CONSUMERCHOICE=cn/zh_cn; siteCatalyst_sample=61; dreamcatcher_sample=25; neo_sample=62; neo.experiments=%7B%22main%22%3A%7B%223698-interceptor%22%3A%22a%22%7D%2C%22snkrs%22%3A%7B%7D%2C%22ocp%22%3A%7B%7D%7D; lls=3; _abck=9DBBDD054807383F131FFB0CEC6AA813D2C0772DF314000060D20C5B7AFAA738~0~1kLnceily3bJSJ9Q4OdzoKXWFf5BZhJD132poroMB1w=~-1~-1; bm_sz=6D21E0C8EF698546AA219356595EEBE4~QAAQFXfA0iu2dJdjAQAAFNL9yAeT4AgoENECJPQNQ/9KxYmO9yhylZAx4Bg306OswND5o+5GUxNCKzKgsbU0Uit/vDGMp/iwJYGcFsjRO8HePwAFB8D7y3hRqyqh+iuMgOFi886wqHCBD2y/y3h1les7dO87zKtB/rysk/O5evB+9jLsAzJukyfZWkAI; ak_bmsc=80095E436FE6EC9549D601A2E1B21633D2C07715EA380000DBBB145BEC58A950~pl5coT6Lm3YQlltEqJ7jnTU/W8QLOdWqHkTxbYRAg9Ye6Yc9LeZ3VwWIByjLzEhtxmVKf706FbdbspKq32Ov+r/P3J4okTbgB3fbV7/HVArIXSmjzxh0ITJX5kgcIHVgh2msFGAIdnlf+7aRgqjiA4XjecBFt4jP+u+9MBN1gUyROIDWVN01MBW6jGFh7Dm5hbazReqVMEEyLLM5jDwCb58cfff8jHgEhf38t19jHPoRntipgO7iSj12yNNaduKbpx9MfPeSBV01p/pLIgOTUjgjXwlS1m098b95Wn+FxMpW8UYojRMVqQRxaIfaMllp/HV97zJSzEZR+6pKrKgkjaEg==; AKA_A2=A; nike_locale=cn/zh_cn; mm_wc_pmt=1; guidS=17287095-d328-43ef-d48e-c0214162a5ed; _gscbrs_207448657=1; AMCVS_F0935E09512D2C270A490D4D%40AdobeOrg=1; AMCV_F0935E09512D2C270A490D4D%40AdobeOrg=2121618341%7CMCIDTS%7C17686%7CMCMID%7C35431566262966316330772367245609357384%7CMCAAMLH-1528258366%7C11%7CMCAAMB-1528690293%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1528092693s%7CNONE%7CMCAID%7CNONE; utag_main=_st:1528087425505$ses_id:1528085983222%3Bexp-session; guidSTimestamp=1528085471108|1528085626245; _gscs_207448657=28085491rphrtd71|pv:3; bm_sv=512C7D0A95B6DBB833AD9C9618404321~/ySNlifPFUcUZ/acoJoBOpYJf154hbzuazUpnPDFt3juyXda2Ce1UpoKb6GLIZlJ7d0gFniHU4I77ZDPDq0Af43BPEUC8Lzw5daI/QVdn6eJeizgYJOh32VoldbZuTnM3DFsu2J2awIABOkThyaMGQ==; exp.swoosh.user=%7B%22granted%22%3A0%7D; RES_SESSIONID=335318428634909; ppd=homepage%7Cnikecom%3Ehomepage; RT=\"dm=nike.com&si=05859b72-8963-4e6b-a58c-2a2409248fe5&ss=1528085467526&sl=3&tt=60558&obo=0&sh=1528085639038%3D3%3A0%3A60558%2C1528085595217%3D2%3A0%3A47061%2C1528085491128%3D1%3A0%3A23596&bcn=%2F%2F36fb6d10.akstat.io%2F\"; s_sess=%20c51%3Dhorizontal%3B%20s_cc%3Dtrue%3B%20tp%3D4301%3B%20prevList2%3D%3B%20s_ppv%3Dunified%252520profile%25253Elogin%252C9%252C9%252C400%3B; s_pers=%20s_dfa%3Dnikecomprod%7C1528087425962%3B%20c58%3Dno%2520value%7C1528087599037%3B");
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
        httpPost.setHeader("visit","1");
        httpPost.setHeader("visitor","7debb18f-2178-4b47-b656-ed217bbb2808");

       // httpPost.setHeader("Cookie",getCookies());
    }

   /* private String getCookies(){
        cookies = "";
        for(Cookie cookie : cookieStore.getCookies()){
            cookies = cookies+ cookie.getName()+"="+cookie.getValue()+";";
        }
        return  cookies;
    }*/
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
        //httpOptions.setHeader("Cookie",getCookies());
    }
}
