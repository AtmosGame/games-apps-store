package id.ac.ui.cs.advprog.gamesappsstore.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCartAPIResponseCartDetails {
    private Integer id;
    private String appId;
    private String appName;
    private Double appPrice;
    private Date addDate;
}
