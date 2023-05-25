package id.ac.ui.cs.advprog.gamesappsstore.core.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import id.ac.ui.cs.advprog.gamesappsstore.models.notification.NotificationData;
import jakarta.persistence.*;
import lombok.*;

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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "_appDev_id")
    @JsonIgnore
    private AppDev appDev;
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<NotificationData> notifications;
}
