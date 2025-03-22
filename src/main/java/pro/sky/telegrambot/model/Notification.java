package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "notification_task")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String notificationText;

    private LocalDate notificationDate;

    public Integer getId() {
        return id;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public LocalDate getNotificationDate() {
        return notificationDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNotificationText(String text) {
        this.notificationText = text;
    }

    public void setNotificationDate(LocalDate date) {
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
