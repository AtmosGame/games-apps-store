package id.ac.ui.cs.advprog.gamesappsstore.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCartAPIResponse {
    private Integer id;
    private String username;
    private List<GetCartAPIResponseCartDetails> cartDetailsData;
}
