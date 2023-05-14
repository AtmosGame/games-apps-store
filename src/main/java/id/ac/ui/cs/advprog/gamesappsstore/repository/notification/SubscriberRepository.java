package id.ac.ui.cs.advprog.gamesappsstore.repository.notification;

import id.ac.ui.cs.advprog.gamesappsstore.core.notification.AppDev;
import id.ac.ui.cs.advprog.gamesappsstore.core.notification.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long>{
    @NonNull
    List<Subscriber> findAll();

    @NonNull
    Optional<Subscriber> findById(@NonNull Long id);

    @NonNull
    Optional<Subscriber> findByAppDevAndUserId(@NonNull AppDev appDev, @NonNull Long userId);

    @NonNull
    List<Subscriber> findByUserId(@NonNull Long userId);
}
