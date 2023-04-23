package com.yupi.yuapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yupi.yuapiclientsdk.model.User;
import lombok.AllArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.yupi.yuapiclientsdk.util.SignUtils.genSign;


@AllArgsConstructor
public class YuApiClient {

    private String accessKey;
    private String secretKey;

    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result= HttpUtil.get("http://localhost:8123/api/name/", paramMap);
        System.out.println(result);
        return result;
    }


    public String getNameByPost(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result= HttpUtil.post("http://localhost:8123/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    private Map<String, String> getHeaderMap(String body){
        HashMap<String, String> hashMap = new HashMap<>();
        //解决中文乱码问题
        try {
            String encode = URLEncoder.encode(accessKey, "utf-8");
            hashMap.put("accessKey", encode);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        //不能明文发送密码
        //hashMap.put("secretKey", secretKey);
        //todo 该随机数要保存下来，保存时间为规定有效时间，与timestamp联动
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body", body);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", genSign(body, secretKey));
        return hashMap;
    }

    public String getUsernameByPost(User user) {

        String json = JSONUtil.toJsonStr(user);
        HttpResponse res = HttpRequest.post("http://localhost:8123/api/name/user")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        System.out.println(res);
        String result = res.body();
        System.out.println(result);
        return result;
    }


}
