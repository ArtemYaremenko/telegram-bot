package pro.sky.telegrambot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.model.Notification;
import pro.sky.telegrambot.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UpdateServiceTest {

    @Mock
    private NotificationRepository repositoryMock;

    @InjectMocks
    private UpdateService out;

    @Test
    public void shouldReturnNotificationsList() {
        List<Notification> notifications = List.of(new Notification(1l, "Notification_1", LocalDateTime.now()),
                new Notification(2l, "Notification_2", LocalDateTime.now()));

        when(repositoryMock.findByNotificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))).thenReturn(notifications);

        List<Notification> expected = notifications;

        //test
        List<Notification> actual = out.sendNotifications();

        //check
        assertIterableEquals(expected, actual);

        verify(repositoryMock, only()).findByNotificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    @Test
    public void shouldReturnTrueWhenMessageMatchedAndSaved() {
        String message = "04.05.2025 07:00 У меня день рождения!";
        Notification notification = new Notification(1L, "У меня день рождения!", out.stringToDateTime("04.05.2025 07:00"));
        when(repositoryMock.save(notification)).thenReturn(notification);

        //test
        Matcher matcher = out.datePattern.matcher(message);
        boolean actual = out.updateSave(1L, matcher);

        //check
        assertTrue(actual);

        verify(repositoryMock, only()).save(notification);
    }

    @Test
    public void shouldReturnFalseWhenMessageNotMatched() {
        String message = "INVALID MESSAGE!";

        //test
        Matcher matcher = out.datePattern.matcher(message);
        boolean actual = out.updateSave(1L, matcher);

        //check
        assertFalse(actual);
    }
}
