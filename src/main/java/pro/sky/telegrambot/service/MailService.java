package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Notification;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private UpdateService updateService;

    @Scheduled(cron = "0 0/1 * * * *")
    public void run() {
        logger.debug("Sending the notification");
        List<Notification> notifications = updateService.sendNotifications();
        for (Notification notification : notifications) {
            logger.debug("Send the notification \"{}\" at {}", notification, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
            String date = updateService.dateTimeToString(notification.getNotificationDate());
            String text = notification.getNotificationText();
            telegramBot.execute(new SendMessage(notification.getId(), date + " " + text));
        }
    }
}
