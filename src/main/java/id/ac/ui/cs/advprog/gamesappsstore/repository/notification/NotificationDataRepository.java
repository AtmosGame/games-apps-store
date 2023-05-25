package id.ac.ui.cs.advprog.gamesappsstore.repository.notification;

import id.ac.ui.cs.advprog.gamesappsstore.core.notification.Subscriber;
import id.ac.ui.cs.advprog.gamesappsstore.models.notification.NotificationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationDataRepository extends JpaRepository<NotificationData, Long> {
    @NonNull
    List<NotificationData> findAll();

    @NonNull
    Optional<NotificationData> findById(@NonNull Long id);

    List<NotificationData> findBySubscriber(Subscriber subscriber);
}
