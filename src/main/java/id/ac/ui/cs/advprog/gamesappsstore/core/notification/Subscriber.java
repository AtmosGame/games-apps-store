package id.ac.ui.cs.advprog.gamesappsstore.core.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import id.ac.ui.cs.advprog.gamesappsstore.models.notification.NotificationData;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="_subscriber")
public class Subscriber implements Observer{
    @Id
    @GeneratedValue
    Long id;
    Long userId;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "_appDev_id")
    private AppDev appDev;
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "subscriber_notification",
            joinColumns = @JoinColumn(name = "sub_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id"))
    private List<NotificationData> notifications = new ArrayList<>();

    @Override
    public void handleNotification(NotificationData notificationData) {
        notifications.add(notificationData);
        notificationData.getSubscriber().add(this);
    }

}
