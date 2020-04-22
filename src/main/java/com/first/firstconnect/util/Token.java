package com.first.firstconnect.util;
//jwt用于密码加密的哦小兄弟
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.first.firstconnect.entity.User;

public class Token {
    public static String getToken(User user) {
        String token = "";
        //这边是生成token，这里用法需要自己百度，面向百度编程。
        token = JWT.create().withAudience(user.getUserName())
                .sign(Algorithm.HMAC256(user.getUserPassword()))
        ;
        return token;
    }
}
