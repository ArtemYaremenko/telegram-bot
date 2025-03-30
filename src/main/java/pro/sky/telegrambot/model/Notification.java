package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification_task")
public class Notification {

    @Id
    private Long id;

    private String notificationText;

    private LocalDateTime notificationDate;

    public Notification() {
    }

    public Notification(Long id, String notificationText, LocalDateTime notificationDate) {
        this.id = id;
        this.notificationText = notificationText;
        this.notificationDate = notificationDate;
    }
    public Long getId() {
        return id;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNotificationText(String text) {
        this.notificationText = text;
    }

    public void setNotificationDate(LocalDateTime date) {
        this.notificationDate = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;
        Notification that = (Notification) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getNotificationText(), that.getNotificationText()) && Objects.equals(getNotificationDate(), that.getNotificationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNotificationDate());
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", text='" + notificationText + '\'' +
                ", date=" + notificationDate +
                '}';
    }
}
