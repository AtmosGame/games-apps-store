package id.ac.ui.cs.advprog.gamesappsstore.repository.notification;

import id.ac.ui.cs.advprog.gamesappsstore.core.notification.AppDev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppDeveloperRepository extends JpaRepository<AppDev, Long> {
    @NonNull
    List<AppDev> findAll();

    @NonNull
    Optional<AppDev> findById(@NonNull Long id);

    @NonNull
    Optional<AppDev> findByAppId(@NonNull Long id);

}
