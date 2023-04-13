package id.ac.ui.cs.advprog.gamesappsstore.repository.AppRegistration;

import id.ac.ui.cs.advprog.gamesappsstore.core.app.AppData;
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

    List<AppData> findAllByUserId(Integer userId);
    @NonNull
    Optional<AppData> findById(@NonNull Integer id);

    @Query("SELECT MAX(id) FROM AppData")
    Long findLatestId();
}
