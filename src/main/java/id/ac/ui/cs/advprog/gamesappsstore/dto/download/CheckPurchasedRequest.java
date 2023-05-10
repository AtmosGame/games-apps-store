package id.ac.ui.cs.advprog.gamesappsstore.dto.download;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckPurchasedRequest {
    private String username;
    private String appId;
}

