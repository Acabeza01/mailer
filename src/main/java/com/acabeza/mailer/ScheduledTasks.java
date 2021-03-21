package com.acabeza.mailer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//	@Scheduled(fixedRate = 5000)
//	@Scheduled(cron = "3 * * * * *")	//elke minuut op :03 seconden -- seconden minuten uren dagen maanden jaren
	@Scheduled(cron = "0 0 17 * * *")	//elke dag om 17:00:00 uur -- seconden minuten uren dagen maanden jaren
	public void reportCurrentTime() throws Exception {
		log.info("The time is now {}", dateFormat.format(new Date()));
		sendmail();
	}
	
	private void sendmail() throws AddressException, MessagingException, IOException {
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.office365.com");
		   props.put("mail.smtp.port", "587");
		   
		   String mailSender = System.getenv("mailsender");
		   String mailPw = System.getenv("mailpw");
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication(mailSender, mailPw);
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress(mailSender, false));
		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailSender));
		   
		   byte[] array = new byte[7]; // length is bounded by 7
		   new Random().nextBytes(array);
		   String generatedSubject = new String(array, Charset.forName("UTF-8"));

		   new Random().nextBytes(array);
		   String generatedContent = new String(array, Charset.forName("UTF-8"));

		   new Random().nextBytes(array);
		   String generatedBodyPart = new String(array, Charset.forName("UTF-8"));
		   
		   msg.setSubject(generatedSubject);
		   msg.setContent(generatedContent, "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent(generatedBodyPart, "text/html");

		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);

//		   MimeBodyPart attachPart = new MimeBodyPart();
//		   attachPart.attachFile("/var/tmp/image19.png");
//		   multipart.addBodyPart(attachPart);

		   msg.setContent(multipart);
		   Transport.send(msg);   
		}		
}