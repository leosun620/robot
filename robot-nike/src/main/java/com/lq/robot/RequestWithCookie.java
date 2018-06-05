package com.lq.robot;


import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class RequestWithCookie {
    //创建一个HttpContext对象，用来保存Cookie
    HttpClientContext httpClientContext = HttpClientContext.create();

    public String postRequest(String url, List<NameValuePair> nameValuePairs) throws URISyntaxException, IOException, ClassNotFoundException {
        //构造请求资源地址
        URI uri = new URIBuilder(url).addParameters(nameValuePairs).build();
        //构造请求对象
        HttpUriRequest httpUriRequest = RequestBuilder.post().setUri(uri).build();
        return requestWithCookie(httpUriRequest, "cookie");
    }

    public String optionsRequest(String url, List<NameValuePair> nameValuePairs) throws URISyntaxException, IOException, ClassNotFoundException {
        //构造请求资源地址
        URI uri = new URIBuilder(url).addParameters(nameValuePairs).build();
        //构造请求对象
        HttpUriRequest httpUriRequest = RequestBuilder.options().setUri(uri).build();
        return requestWithCookie(httpUriRequest, "cookie");
    }

    public String getRequest(String url, List<NameValuePair> nameValuePairs) throws URISyntaxException, IOException, ClassNotFoundException {
        //构造请求资源地址
        URI uri = new URIBuilder(url).addParameters(nameValuePairs).build();
        //构造请求对象
        HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(uri).build();
        return requestWithCookie(httpUriRequest, "cookie");
    }
    private String responseHeader(HttpResponse response){
        Header[] headers = response.getAllHeaders();
        String result = "";
        for(Header header : headers){
            result += (header.getName() +":"+header.getValue()+"\n");
        }
        return result;
    }


    private String requestHeader(HttpUriRequest httpUriRequest){
        Header[] requests = httpUriRequest.getAllHeaders();
        String result = "";
        for(Header request : requests){
            result += (request.getName()+":"+request.getValue() +"\n");
        }
        return result;
    }
    private String responseString(HttpResponse response) {
        String result = "";
        if (response != null) {
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                try {
                    result = EntityUtils.toString(resEntity, "utf-8");
                    result += "\n";
                    result += responseHeader(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    public String requestWithCookie(HttpUriRequest httpUriRequest, String cookiePath) throws IOException, ClassNotFoundException {
        String result = requestHeader(httpUriRequest)+"\n";
        //下面我们将演示如何使用Cookie来请求，首先我们将之前的Cookie读出来
        CookieStore cookieStore1 = readCookieStore(cookiePath);

        //构造一个带这个Cookie的HttpClient
        HttpClient newHttpClient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore1).build();

        //使用这个新的HttpClient请求就可以了。这时候我们的HttpClient已经带有了之前的登录信息，再爬取就不用登录了
        HttpResponse response = newHttpClient.execute(httpUriRequest, httpClientContext);

        //从请求结果中获取Cookie，此时的Cookie已经带有登录信息了
        CookieStore cookieStore = httpClientContext.getCookieStore();

        //这个CookieStore保存了我们的登录信息，我们可以先将它保存到某个本地文件，后面直接读取使用
        saveCookieStore(cookieStore, cookiePath);

        result += responseString(response);
        return result;
    }

    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException {

        //待请求的地址
        String url = "http://www.datalearner.com";

        //请求参数
        List<NameValuePair> loginNV = new ArrayList<>();
        loginNV.add(new BasicNameValuePair("userName", "test"));
        loginNV.add(new BasicNameValuePair("passWord", "test"));
        RequestWithCookie requestWithCookie = new RequestWithCookie();
        String result = requestWithCookie.getRequest("https://www.nike.com/cn/zh_cn/", new ArrayList<NameValuePair>());

        System.out.println(result);
        System.out.println("------------------------------------------------------------");
        String loginUrl = "https://unite.nike.com/login?appVersion=435&experienceVersion=361&uxid=com.nike.commerce.nikedotcom.web&locale=zh_CN&backendEnvironment=identity&browser=Google%20Inc.&os=undefined&mobile=false&native=false&visit=2&visitor=7df3dddd-8279-43db-8e6e-5bb6e3ac8720";
        result = requestWithCookie.optionsRequest(loginUrl,new ArrayList<NameValuePair>());
        System.out.println(result);
        System.out.println("------------------------------------------------------------");
        result = requestWithCookie.optionsRequest(loginUrl,new ArrayList<NameValuePair>());
        System.out.println(result);
    }

    //使用序列化的方式保存CookieStore到本地文件，方便后续的读取使用
    private static void saveCookieStore(CookieStore cookieStore, String savePath) throws IOException {

        FileOutputStream fs = new FileOutputStream(savePath);
        ObjectOutputStream os = new ObjectOutputStream(fs);
        os.writeObject(cookieStore);
        os.close();

    }

    //读取Cookie的序列化文件，读取后可以直接使用
    private static CookieStore readCookieStore(String savePath) {
        try {
            FileInputStream fs = new FileInputStream("cookie");//("foo.ser");
            ObjectInputStream ois = new ObjectInputStream(fs);
            CookieStore cookieStore = (CookieStore) ois.readObject();
            ois.close();
            return cookieStore;
        } catch (Exception e) {
            System.out.println("没有 cookie文件，不用读取");
        }
        return null;
    }

}
