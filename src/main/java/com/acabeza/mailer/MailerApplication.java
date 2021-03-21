package com.acabeza.mailer;

import java.io.IOException;
import java.nio.charset.Charset;
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

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableScheduling
public class MailerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailerApplication.class, args);
	}

/*
 * HEROKU start vanaf CLI:
 * 
 * heroku run java -jar target/mailer-0.0.1-SNAPSHOT.jar
 * heroku run java -Dserver.port=$PORT $JAVA_OPTS -jar target/mailer-0.0.1-SNAPSHOT.jar
 * 
 * zie Procfile voor Scheduler job met van Heroku CLI:
 * heroku ps:scale scheduler=1 
 * 
 */
	
//	@Component
//	public class ApplicationRunnerBean implements ApplicationRunner {
//		@Override
//		public void run(ApplicationArguments args) throws Exception {
//
//			System.out.println("Mailer started");
////			sendmail();
////			System.out.println("Ended");
//
//		}
//	}
	

	
}
