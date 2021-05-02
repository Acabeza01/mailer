package com.acabeza.mailer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.acabeza.mailer.model.Mail;
import com.acabeza.mailer.service.EmailSenderService;
import com.neovisionaries.i18n.CountryCode;

@SpringBootApplication
@EnableScheduling
public class MailerApplication {

    @Autowired
    private EmailSenderService emailService;

    @Autowired
    private ScheduledTasks scheduledtasks;

    private static final Logger log = LoggerFactory.getLogger(MailerApplication.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM HH:mm:ss");

    public static void main(String[] args) {
        SpringApplication.run(MailerApplication.class, args);
    }

    /*
     * HEROKU start vanaf CLI:
     * 
     * heroku run java -jar target/mailer-0.0.1-SNAPSHOT.jar heroku run java -Dserver.port=$PORT $JAVA_OPTS -jar
     * target/mailer-0.0.1-SNAPSHOT.jar
     * 
     * zie Procfile voor Scheduler job met van Heroku CLI: heroku ps:scale scheduler=1
     * 
     */

    @Component
    public class ApplicationRunnerBean implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments args) throws Exception {

            log.info("Mailer started");

            // scheduledtasks.createMail(CountryCode.NL);

            // Mail mail = new Mail();
            // mail.setFrom(System.getenv("mailsender"));// replace with your desired email
            // mail.setMailTo(System.getenv("mailreceiver"));// replace with your desired email
            // mail.setSubject("New Releases Started " + " " + dateFormat.format(new Date()));
            // emailService.sendEmail(mail);
            // log.info("Mailer sent starter mail.. {}", dateFormat.format(new Date()));

            log.info("Ended");

        }
    }

}
