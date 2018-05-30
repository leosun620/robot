package com.lq.robot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/*
 * 利用HttpClient进行post请求的工具类
 */
public class HttpClientUtil {
    public String doPost(String url, Map<String, String> map, String charset) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
//            httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
//            httpPost.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//            httpPost.setHeader("Accept-Encoding","deflate, br");
//            httpPost.setHeader("accept-language","zh-CN,zh;q=0.9");
//            httpPost.setHeader("Accept-Language","zh-CN,zh;q=0.9");
//            httpPost.setHeader("cache-control","max-age=0");
//            httpPost.setHeader("Connection","keep-alive");
            setHeader(httpPost);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = (Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            String jsonString = "{\"username\":\"463093053@qq.com\",\"password\":\"liu123456L\",\"client_id\":\"HlHa2Cje3ctlaOqnxvgZXNaAs7T9nAuH\",\"ux_id\":\"com.nike.commerce.nikedotcom.web\",\"grant_type\":\"password\"}";
            if (list.size() > 0) {
                //UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);

               // httpPost.setEntity(entity);
            }
            StringEntity entity = new StringEntity(jsonString, "text/html", "utf-8");
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private void setHeader(HttpPost httpPost) {
        httpPost.setHeader("accept", "*/*");
        httpPost.setHeader("accept-encoding", "gzip, deflate, br");
        httpPost.setHeader("accept-language", "zh-CN,zh;q=0.9");
        String cookie ="AnalysisUserId=210.192.119.37.75351527685737393; bm_sz=457C03A9FE543AC644484E7E56D59DE6~QAAQJXfA0hY7TZdjAQAAsWsqsZVta9fc+eOAwGzj9LxpLXov/EJnY1KrXb2BjH1HKtolOnjQC7YVvvHZJ6O2+eT/PPm8cf2WW+A8G1+zzce2teYuJ2GT2d++l2YeULUowuoPmarAH36sPfH3l+YNQYRq7gcX/cn979ZwvrIA9fNSEhyHh1ZIMs0L+KnJ; guidU=26bf140e-7c1d-4984-c1bb-3f5698c2cc7c; neo.swimlane=37; dreams_sample=73; AMCVS_F0935E09512D2C270A490D4D%40AdobeOrg=1; guidS=872cb5e2-35c2-4a1c-ef44-65c4b18ad281; ajs_anonymous_id=%22b1e3be2b-b1e7-4f89-bab3-211a8322b6b3%22; NIKE_COMMERCE_CCR=1527685968207; CONSUMERCHOICE_SESSION=t; CONSUMERCHOICE=cn/zh_cn; NIKE_COMMERCE_COUNTRY=CN; NIKE_COMMERCE_LANG_LOCALE=zh_CN; ak_bmsc=5D0BF243D5ADB33079677E11FA0A905AD2C077256F1D00009DA30E5BB7FCB469~pl5FCErCbOWnbmZal0+ytkjA9Qiix1ACcODTNfAGFHSRrx80WymOPJmdhOtLb1bcdwZT0kG1m32NgqCTtTtqTChJmXgl5+60+9GGodQ3KZMRHG8SMvRACW2J80OU0ndxH2pX2ERZ65v/wHw33FT3y1A6FVebNU27dFQl/+3+yNpPuokxQ29pm4HFiCSxsK7o58F5dStsetwAeJ3vqT+kIf9I772ZoETjAphhLD/A0nFpD20ORuwOiwbTH3Ee4mbVnFIRc9/UZtZIXSNfKU/rkZehHLRQ+d1jS2Uk9VE3yoSZivj6pbDPX35xAHi/X6RbLzhFWezmCKWJ2cs+/nUc88Pw==; AMCV_F0935E09512D2C270A490D4D%40AdobeOrg=2121618341%7CMCIDTS%7C17682%7CMCMID%7C48759906085072709082068505081692928310%7CMCAAMLH-1528291313%7C11%7CMCAAMB-1528291313%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1527693713s%7CNONE%7CMCAID%7CNONE%7CvVersion%7C2.4.0; RES_TRACKINGID=185485953986549; nike_locale=cn/zh_cn; utag_main=_st:1527690499498$ses_id:1527685902603%3Bexp-session; mm_wc_pmt=1; neo.experiments=%7B%22main%22%3A%7B%7D%7D; guidSTimestamp=1527685780476|1527688701466; _gscu_207448657=27685784hwd2np14; _gscs_207448657=27685784gbhar514|pv:6; _gscbrs_207448657=1; _smt_uid=5b0ea299.1434dba8; ajs_group_id=null; ajs_user_id=%22DE4E38BB59E1E316BB0AFB9CB70F06B9%22; ResonanceSegment=1; RES_SESSIONID=883054328794325; APID=1FB284960C480A862C3F0AE6C254A31F.sin-332-app-ap-0; lls=3; _abck=E2A434F29CD0B3D66CAA15DED73EE619D2C077256F1D000069A20E5BD68FDA3B~0~GpEN4Bajuj4Rb5VjW3PkSXl+HWfAgoJnQ3b/wkuLgag=~-1~-1; RT=\"dm=nike.com&si=431ee4ba-8c79-400b-96f3-5ebbc83df940&ss=1527685740285&sl=9&tt=99476&obo=0&sh=1527688702468%3D9%3A0%3A99476%2C1527687258389%3D8%3A0%3A94356%2C1527687182217%3D7%3A0%3A89957%2C1527686828422%3D6%3A0%3A85292%2C1527686513935%3D5%3A0%3A80609&bcn=%2F%2F36fb61b0.akstat.io%2F&nu=&cl=1527689857124\"; slCheck=N; llCheck=N; sls=0; bm_sv=3F52007DED0A1FC1137179AD0AF5B2E7~Ib1iy03YeiHYurXkoSFSUn6hMHFjudHKVI/R5k+va5WjGIXTqEX7nvemqjFeV6wDwz6qWTl9XqneTpDxYY4qAvOGl8mSGPJpIxfFFpnnVcJOpIR15RpVd1W0+6i2HPPeNYjx7hyAgc2mF4SJBwMv8g==; exp.swoosh.user=%7B%22granted%22%3A0%7D; ppd=homepage%7Cnikecom%3Ehomepage; s_pers=%20s_dfa%3Dnikecomprod%7C1527690501138%3B%20c58%3Dno%2520value%7C1527691685718%3B; s_sess=%20c51%3Dhorizontal%3B%20s_cc%3Dtrue%3B%20prevList2%3D%3B%20tp%3D5157%3B%20s_ppv%3Dunified%252520profile%25253Elogin%252C8%252C8%252C418%3B";

        httpPost.setHeader("content-type", "application/json");
        httpPost.setHeader("cookie", cookie);
        httpPost.setHeader("origin", "https://www.nike.com");
        httpPost.setHeader("referer", "https://www.nike.com/cn/zh_cn/");
        httpPost.setHeader(" user-agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
    }
}
