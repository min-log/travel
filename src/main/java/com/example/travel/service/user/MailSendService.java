package com.example.travel.service.user;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class MailSendService  {
    // application-mail.properties 값 가져오기 // 보안

    @Value("${spring.mail.host}")
    String host;

    @Value("${spring.mail.port}")
    int port;
    @Value("${spring.mail.username}")
    String username;

    @Value("${spring.mail.password}")
    String password;




    public void sendEmail(String e) throws Exception {
        Email email = new SimpleEmail();
        email.setHostName(host);
        email.setSmtpPort(port);
        email.setCharset("utf-8");// 인코딩 설정(utf-8, euc-kr)
        email.setAuthenticator(new DefaultAuthenticator(username, password));
        email.setSSL(true);
        email.setFrom(username, "travel");
        email.setSubject("[ travel ] 이메일 인증번호 전송");
        email.setMsg("메일 내용");
        email.addTo(e, e);
        email.send();
    }

}
