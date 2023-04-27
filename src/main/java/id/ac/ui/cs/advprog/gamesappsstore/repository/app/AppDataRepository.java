package id.ac.ui.cs.advprog.gamesappsstore.repository.app;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppDataRepository extends JpaRepository<AppData, String> {
    @NonNull
    List<AppData> findAll();

    @NonNull
    Optional<AppData> findById(@NonNull Long id);

    @Query("SELECT MAX(id) FROM AppData")
    Long findLatestId();
    @Query("SELECT a FROM AppData a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<AppData> searchAppsByKeyword(@Param("keyword") String keyword);
    List<AppData> findByVerificationStatusIsNull();

    List<AppData> findByVerificationStatus(VerificationStatus verificationStatus);
}
