package id.ac.ui.cs.advprog.gamesappsstore.dto.cart;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateCartAPIRequest {
    private String id;
    private String name;
    private Double price;
    private String username;
}
