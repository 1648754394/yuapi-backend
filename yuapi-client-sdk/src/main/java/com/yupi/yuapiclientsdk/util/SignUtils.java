package com.yupi.yuapiclientsdk.util;

import cn.hutool.crypto.digest.DigestUtil;

public class SignUtils {

    /**
     * 生成签名
     * @param body
     * @param secretKey
     * @return
     */
    public static String genSign(String body, String secretKey) {
        String content = body + "." + secretKey;
        return DigestUtil.md5Hex(content);
    }
}
