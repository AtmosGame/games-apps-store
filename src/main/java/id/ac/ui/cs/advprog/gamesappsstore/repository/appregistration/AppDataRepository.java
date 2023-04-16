package id.ac.ui.cs.advprog.gamesappsstore.repository.appregistration;

import id.ac.ui.cs.advprog.gamesappsstore.core.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppDataRepository extends JpaRepository<AppData, String> {
    @NonNull
    List<AppData> findAll();

    // List<AppData> findAllByUserId(Integer userId);
    @NonNull
    Optional<AppData> findById(@NonNull Long id);

    @Query("SELECT MAX(id) FROM AppData")
    Long findLatestId();

    List<AppData> findByVerificationStatusIsNull();

    List<AppData> findByVerificationStatus(VerificationStatus verificationStatus);
}
