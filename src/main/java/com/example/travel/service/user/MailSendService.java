package com.example.travel.service.user;

import lombok.RequiredArgsConstructor;
import org.apache.commons.mail.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class MailSendService{

   // final JavaMailSender emailSender;

    // application-mail.properties 값 가져오기 // 보안
    @Value("${spring.mail.host}")
    String host;

    @Value("${spring.mail.port}")
    int port;
    @Value("${spring.mail.username}")
    String username;

    @Value("${spring.mail.password}")
    String password;


    String ePw; // 랜덤 인증번호
    String name; //회원이름

    // 전달할 이메일 내용
    String msgTit = "[Travel Road]";
    String msgTxt;




    public String createPassword() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return ePw=key.toString();
    }


    //메일보내기
    public String sendEmail(String userEmail,String name, String value) throws Exception {
        try {
            this.name = name; //회원이름
            createPassword(); // 인증번호 생성
            HtmlEmail email = new HtmlEmail();
            email.setCharset("euc-kr"); // 한글 인코딩
            email.setHostName(host); //SMTP서버 설정
            email.setSmtpPort(port);
            email.setSSL(true);
            email.setAuthentication(username, password); //메일인증
            
            try {
                email.addTo(userEmail,name); // 수신자
            } catch (EmailException e) {
                e.printStackTrace();
            }

            try {
                email.setFrom(username, "Travel Road"); // 발신자
            } catch (EmailException e) {
                e.printStackTrace();
            }
            
            // Create the email message
            email.addTo(userEmail, name);  //수신자
            email.setFrom(username, "Travel Road"); // 발신자


            // 메일 제목 & 내용 설정
            if(value.equals("userCheck")){
                //아이디 체크 인증번호가 있을 시 인증 메일 내용 전달
                msgEmailPw();
            }else if(value.equals("joinSuccess")){
                //아이디 체크 인증번호가 있을 시 인증 메일 내용 전달
                joinSuccess();
            }



            email.setSubject(msgTit); // 메일 제목
            email.setHtmlMsg(msgTxt); // 메일 내용

            // set the alternative message
            //email.setTextMsg("Your email client does not support HTML messages");

            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }



        return ePw;
   }


    // 메일 제목 & 내용 설정
    public void msgEmailPw(){
        msgTit = "[Travel Road] 이메일 인증번호 전달";
        //content
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ko\">");
        strBuf.append("<head>");
        strBuf.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
        strBuf.append("<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\">");
        strBuf.append("<meta http-equiv=\"Content-Style-Type\" content=\"text/css\">");
        strBuf.append("<title>[Travel Road]이메일인증 번호</title>");
        strBuf.append("</head>");
        strBuf.append("<body style=\"margin:0;font-size:1em\">");
        strBuf.append("<div>");
        strBuf.append("<h1 style='font-size:1.3em;margin-bottom:10px;'>안녕하세요 'Travel Road' 입니다.</h1>");
        strBuf.append("<p>회원님의 아이디를 찾으시려면 <span style='font-weight:bold;color:red'>'인증번호'</span>란에 아래 번호를 넣어주세요.</p>");
        strBuf.append("<p style='margin:10px 0;border: 1px solid #ced4da;background:#eee;padding:20px;font-size:1.2em'>");
        strBuf.append("[ 인증번호 : <span style='font-weight:bold;color:red'>" + ePw + "</span> ]");
        strBuf.append("</p>");
        strBuf.append("<p>감사합니다.</p>");
        strBuf.append("</div>");
        strBuf.append("</body>");
        strBuf.append("</html>");
        msgTxt = strBuf.toString();



        // embed the image and get the content id
        //URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
        //String cid = email.embed(url, "Apache logo");
        // set the html message
        ///images/home/bul_citizen_icon01.png

    }


    public void joinSuccess(){
        msgTit = "[Travel Road] 회원가입";
        //content
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ko\">");
        strBuf.append("<head>");
        strBuf.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
        strBuf.append("<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\">");
        strBuf.append("<meta http-equiv=\"Content-Style-Type\" content=\"text/css\">");
        strBuf.append("<title>[Travel Road]이메일인증 번호</title>");
        strBuf.append("</head>");
        strBuf.append("<body style=\"margin:0;font-size:1em\">");
        strBuf.append("<div>");
        strBuf.append("<h1 style='font-size:1.3em;margin-bottom:10px;'>안녕하세요 'Travel Road' 입니다.</h1>");
        strBuf.append("<p> '" + name + " ' 회원님의 회원가입이 완료되었습니다.</p>");
        strBuf.append("<p>감사합니다.</p>");
        strBuf.append("</div>");
        strBuf.append("</body>");
        strBuf.append("</html>");
        msgTxt = strBuf.toString();



        // embed the image and get the content id
        //URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
        //String cid = email.embed(url, "Apache logo");
        // set the html message
        ///images/home/bul_citizen_icon01.png

    }

}
