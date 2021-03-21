package com.acabeza.mailer;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

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

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class MailerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailerApplication.class, args);
	}

	@Component
	public class ApplicationRunnerBean implements ApplicationRunner {
		@Override
		public void run(ApplicationArguments args) throws Exception {

			System.out.println("Started");
			sendmail();
			System.out.println("Ended");

		}
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
		   msg.setSubject("Tutorials point email");
		   msg.setContent("Tutorials point content", "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent("Dit is de tekst van het <b>mailbericht<b>", "text/html");

		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);

//		   MimeBodyPart attachPart = new MimeBodyPart();
//		   attachPart.attachFile("/var/tmp/image19.png");
//		   multipart.addBodyPart(attachPart);

		   msg.setContent(multipart);
		   Transport.send(msg);   
		}	
	
}
