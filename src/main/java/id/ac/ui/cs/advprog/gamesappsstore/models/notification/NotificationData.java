package id.ac.ui.cs.advprog.gamesappsstore.models.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import id.ac.ui.cs.advprog.gamesappsstore.core.notification.Subscriber;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_notification")
public class NotificationData {
    @Id
    @GeneratedValue
    private Long id;
    private Long subjectId;
    private String description;
    private Timestamp timestamp;
    @ManyToOne
    @JsonIgnore
    private Subscriber subscriber;
}


