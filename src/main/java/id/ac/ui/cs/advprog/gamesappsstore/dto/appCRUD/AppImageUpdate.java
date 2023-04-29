package id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class AppImageUpdate {
    private MultipartFile imageFile;

    public AppImageUpdate(MultipartFile imageFile){
        this.imageFile = imageFile;
    }
}
