package Test;

import newMethod.CompanyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.*;
import java.util.*;

public class GetPostDemo {

    /**
     * @param generalUrl 请求url
     * @param contentType 请求文本类型 application/x-www-form-urlencoded application/json
     * @param params  请求参数
     * @param encoding  编码
     * @return
     * @throws Exception
     */
    public static String postGeneralUrl(String generalUrl, String contentType, String params, String encoding)
            throws Exception {
        URL url = new URL(generalUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 得到请求的输出流对象
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.write(params.getBytes(encoding));
        out.flush();
        out.close();

        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> headers = connection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String key : headers.keySet()) {
            System.err.println(key + "--->" + headers.get(key));
        }
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = null;
        in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), encoding));
        String result = "";
        String getLine;
        while ((getLine = in.readLine()) != null) {
            result += getLine;
        }
        in.close();
        System.err.println("result:" + result);
        return result;
    }

    /**
     * @param generalUrl 请求url
     * @param params 请求参数
     * @return
     */
    public static String getURL(String generalUrl, String params) {
        String authHost = generalUrl+params;
        try {
            URL realUrl = new URL(authHost);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

            System.err.println("result:" + result);
            return result;
        } catch (Exception e) {
            System.err.printf("获取失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static void main(String []args){

        try {
            String paramsURL = "qiye=" + URLEncoder.encode("中金云金融（北京）大数据科技股份有限公司", "UTF-8")+"&ceng=1";
            String a = postGeneralUrl("http://172.16.234.141:8090/qiyebase", "application/x-www-form-urlencoded",
                    paramsURL, "UTF-8");

            System.out.println(a);
            JSONObject jsonObject= new JSONObject(a);
            String base = jsonObject.get("rows").toString();
            System.out.println(base);

            JSONArray jsonA= new JSONArray(base);
            for(int i = 0;i<jsonA.length();i++){
                JSONObject jsonObject2 = (JSONObject) jsonA.get(i);
                System.out.println(jsonObject2.get("name1") + " " +jsonObject2.get("name2"));
                System.out.println(jsonObject2.get("name3") + " " +jsonObject2.get("name4"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        String u = getURL("http://pvt.daxiangdaili.com/ip/?","tid=55923198871342&num=1");
//        System.out.println(u);

    }
//
//    public static void main(String []args){
//        //获取cookies包含用户登陆信息
//        Map<String,String> cookie = CompanyUtils.getCookieMap();
//        String url ="http://www.qichacha.com/gongsi_getList";
//        Connection connection = Jsoup.connect(url);
//
//        String body = "key=小米&type=undefined";
//        connection.requestBody(body);
//
//        connection.header("Accept-Encoding", "gzip, deflate");
//        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
//        connection.header("Connection","keep-alive");
//        connection.header("Host","www.qichacha.com");
//        connection.header("Origin","http://www.qichacha.com");
//        connection.header("X-Requested-With","XMLHttpRequest");
//        connection.header("Content-Length","25");
//        connection.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
//        connection.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//        connection.header("Accept", "*/*");
//        connection.header("Referer","http://www.qichacha.com/");
//        connection.cookies(cookie);
//        connection.timeout(30000);
//        connection.ignoreContentType(true);
//
//        try {
//            Document document = connection.post();
//
//           //System.out.println(document.toString());
//            JSONArray jsonA= new JSONArray(document.text());
//            for(int i = 0;i<jsonA.length();i++){
//                JSONObject jsonObject = (JSONObject) jsonA.get(i);
//                System.out.println(jsonObject.get("KeyNo") + "," +jsonObject.get("Name").toString().replaceAll("</em>",""));
//            }
//            //System.out.println(jsonObj.get(0));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String []args){
//        String url ="http://gs.amac.org.cn/amac-infodisc/api/pof/manager";
//        Connection connection = Jsoup.connect(url);
//        connection.data("rand","0.925022878004083");
//        connection.data("page","0");
//        connection.data("size","20");
//
//        String body = "{\"fundScale\":{\"from\":\"500000\"},\"fundType\":\"私募证券自主发行\"}";
//        connection.requestBody(body);
//
//        connection.header("Content-Type", "application/json");
//        connection.header("Accept", "application/json, text/javascript, */*; q=0.01");
//        connection.timeout(30000);
//        connection.ignoreContentType(true);
//
//        try {
//            Document document = connection.post();
//
//            System.out.println(document.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
