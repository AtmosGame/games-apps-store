package id.ac.ui.cs.advprog.gamesappsstore.core.notification;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.ac.ui.cs.advprog.gamesappsstore.models.notification.NotificationData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="_developer")
public class AppDev implements Subject{
    @Id
    @GeneratedValue
    private Long id;
    private Long appId;

    @JsonManagedReference
    @OneToMany(mappedBy = "appDev")
    private List<Subscriber> subscribers;

    @Override
    public void addSubscriber(Subscriber subscriber) {
        subscriber.setAppDev(this);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
       if(subscriber.getAppDev() == this){
           subscriber.setAppDev(null);
       }
    }

    @Override
    public void notifySubscriber(NotificationData notificationData) {
        for (Subscriber notifiedSubscriber : subscribers) {
            notifiedSubscriber.handleNotification(notificationData);
        }
    }
}
