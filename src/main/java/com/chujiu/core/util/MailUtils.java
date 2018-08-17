package com.chujiu.core.util;

/**
 * Created by Administrator on 2016/10/25.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
@Component
public class MailUtils {

    private Logger logger = LoggerFactory.getLogger(MailUtils.class);

    @Value("${mail.host}")
    private String host; // smtp服务器

    @Value("${mail.from}")
    private String from; // 发件人地址

    @Value("${mail.to}")
    public String[] to; // 收件人地址支持多个
    //private static final String affix = ""; // 附件地址
    //private static final String affixName = ""; // 附件名称
    @Value("${mail.user}")
    private String user; // 发件人用户名

    @Value("${mail.pwd}")
    private String pwd; // 发件人密码
    //private static final String subject = ""; // 邮件标题

    /**
     * 添加任务到任务队列（顺序执行）
     *
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param affix 附件位置
     * @param affixName 附件名称
     */
    public void send(String subject, String content, String affix,String affixName) {
        /*this.host = host;
        this.user = user;
        this.pwd = pwd;*/

        Properties props = new Properties();

        // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.host", host);
        // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        props.put("mail.smtp.auth", "true");

        // 用刚刚设置好的props对象构建一个session
        Session session = Session.getDefaultInstance(props);

        // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
        // 用（你可以在控制台（console)上看到发送邮件的过程）
        session.setDebug(true);

        // 用session为参数定义消息对象
        MimeMessage message = new MimeMessage(session);
        try {
            // 加载发件人地址
            message.setFrom(new InternetAddress(from));
            // 加载收件人地址
            for (String t : to) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(t));
            }
            // 加载标题
            message.setSubject(subject);

            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText(content);
            multipart.addBodyPart(contentPart);
            if(!"".equals(affix)){
                // 添加附件
                BodyPart messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(affix);
                // 添加附件的内容
                messageBodyPart.setDataHandler(new DataHandler(source));
                // 添加附件的标题
                // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
                sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
                messageBodyPart.setFileName("=?GBK?B?"
                        + enc.encode(affixName.getBytes()) + "?=");
                multipart.addBodyPart(messageBodyPart);
            }

            // 将multipart对象放到message中
            message.setContent(multipart);
            // 保存邮件
            message.saveChanges();
            // 发送邮件
            Transport transport = session.getTransport("smtp");
            // 连接服务器的邮箱
            transport.connect(host, user, pwd);
            // 把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }
}
