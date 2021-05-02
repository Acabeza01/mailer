package com.acabeza.mailer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.acabeza.mailer.model.Mail;
import com.acabeza.mailer.model.MyAlbum;
import com.acabeza.mailer.service.EmailSenderService;
import com.acabeza.mailer.service.SpotifyService;
import com.neovisionaries.i18n.CountryCode;

@Component
public class ScheduledTasks {

    @Autowired
    private EmailSenderService emailService;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM HH:mm:ss");

    // @Scheduled(fixedDelay = 500000)
    // @Scheduled(cron = "3 * * * * *") //elke minuut op :03 seconden -- seconden minuten uren dagen maanden jaren
    // @Scheduled(cron = "0 0 7 * * *") //elke dag om 7:00:00 uur -- seconden minuten uren dagen maanden jaren
    // @Scheduled(cron = "* 31 * * * WED") //elke WOENSdag om :31:00 uur -- seconden minuten uren dagen maanden jaren
    // @Scheduled(cron = "0 0 ${uurStart} * * *") // elke application.properteis uurStart = 7

    @Scheduled(cron = "0 0 5 * * FRI") // elke dag om 7:00:00 uur -- seconden minuten uren dagen maanden jaren
    public void mailUS() throws Exception {
//        createMail(CountryCode.US);
//        createMail(CountryCode.GB);
        createMail(CountryCode.NL);
    }

    public void createMail(CountryCode country) throws Exception {

        log.info("The time is now {}", dateFormat.format(new Date()));

        log.info("Country {}", country.getName());
        List<MyAlbum> albums = SpotifyService.getListOfNewReleases_Sync(country);
        log.info("Albums read.. {}", dateFormat.format(new Date()));

        Mail mail = new Mail();
        mail.setFrom(System.getenv("mailsender"));// replace with your desired email
        mail.setMailTo(System.getenv("mailreceiver"));// replace with your desired email
        mail.setSubject("New Releases " + country.getName() + " " + dateFormat.format(new Date()));
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("albums", albums);
        mail.setProps(model);
        Thread.sleep(1000);
        emailService.sendEmail(mail);
        log.info("Mail sent.. {}", dateFormat.format(new Date()));

    }

}