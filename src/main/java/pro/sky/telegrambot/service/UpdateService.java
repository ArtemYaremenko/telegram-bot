package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public static final Logger logger = LoggerFactory.getLogger(UpdateService.class);

    public Pattern datePattern = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");

    @Autowired
    private NotificationRepository repository;

    public List<Notification> sendNotifications() {
        List<Notification> notifications = repository.findByNotificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        logger.debug("Return actual notifications - {}", notifications);
        return notifications;
    }

    public boolean updateSave(Long chatId, Matcher matcher) {
        logger.debug("Saving notification");
        if (matcher.find()) {
            String dateString = matcher.group(1);
            LocalDateTime date = stringToDateTime(dateString);
            String text = matcher.group(3);
            repository.save(new Notification(chatId, text, date));
            logger.debug("Saved successfully");
            return true;
        }
        logger.debug("The notification was not saved - the wrong format");
        return false;
    }

    public LocalDateTime stringToDateTime(String date) {
        LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        logger.debug("Convert String \"{}\" to LocalDateTime \"{}\"", date, localDateTime);
        return localDateTime;
    }

    public String dateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String string = dateTime.format(formatter);
        logger.debug("Convert LocalDateTime \"{}\" to String \"{}\"", dateTime, string);
        return string;
    }
}
