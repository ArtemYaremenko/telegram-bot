package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.model.Notification;
import pro.sky.telegrambot.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UpdateService {

    @Autowired
    private TelegramBot telegramBot;

    private TelegramBotUpdatesListener telegramBotUpdatesListener;

    public Pattern datePattern = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");

    @Autowired
    private NotificationRepository repository;

    @Scheduled(cron = "0 0/1 * * * *")
    public void run() {
        List<Notification> notifications = sendNotifications();
        for (Notification notification : notifications) {
            String date = dateTimeToString(notification.getNotificationDate());
            String text = notification.getNotificationText();
            telegramBot.execute(new SendMessage(notification.getId(), date + " " + text));
        }
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public List<Notification> sendNotifications() {
        return repository.findByNotificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    public boolean updateSave(Long chatId, Matcher matcher) {
        if (matcher.find()) {
            String dateString = matcher.group(1);
            LocalDateTime date = stringToDateTime(dateString);
            String text = matcher.group(3);
            repository.save(new Notification(chatId, text, date));
            return true;
        }
        return false;
    }

    public LocalDateTime stringToDateTime(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public String dateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return dateTime.format(formatter);
    }
}
