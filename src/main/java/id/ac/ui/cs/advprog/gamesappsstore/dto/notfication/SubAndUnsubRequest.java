package id.ac.ui.cs.advprog.gamesappsstore.dto.notfication;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SubAndUnsubRequest {
    private Long appDevId;
    public SubAndUnsubRequest(Long appDevId){
        this.appDevId = appDevId;
    }
}
