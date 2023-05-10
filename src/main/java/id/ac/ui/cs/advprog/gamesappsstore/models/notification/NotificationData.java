package id.ac.ui.cs.advprog.gamesappsstore.models.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import id.ac.ui.cs.advprog.gamesappsstore.core.notification.Subscriber;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @ManyToMany(mappedBy = "notifications")
    @JsonIgnore
    private List<Subscriber> subscriber = new ArrayList<>();
}


