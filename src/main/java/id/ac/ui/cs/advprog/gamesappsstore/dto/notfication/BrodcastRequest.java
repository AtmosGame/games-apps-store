package id.ac.ui.cs.advprog.gamesappsstore.dto.notfication;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BrodcastRequest {
    private Long appDevId;
    private String message;
    public BrodcastRequest(Long appDevId, String message){
        this.appDevId = appDevId;
        this.message = message;
    }
}
