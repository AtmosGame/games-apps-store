package id.ac.ui.cs.advprog.gamesappsstore.service.cart;

import id.ac.ui.cs.advprog.gamesappsstore.core.cart.api.DeleteCartAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.cart.api.IsAppInCartAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.cart.api.UpdateCartAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.cart.AppInCartException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.AppDataDoesNotExistException;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private IsAppInCartAPICall isAppInCartAPICall;
    @Autowired
    private UpdateCartAPICall updateCartAPICall;
    @Autowired
    private DeleteCartAPICall deleteCartAPICall;

    @Autowired
    private AppDataRepository appDataRepository;

    public boolean isAppInCart(Long appId, String jwtToken) {
        AppData app = getAppOrNotFound(appId);
        return isAppInCartAPICall.execute(app, jwtToken);
    }

    public void addAppToCart(Long appId, String username, String jwtToken) {
        AppData app = getAppOrNotFound(appId);

        if (isAppInCartAPICall.execute(app, jwtToken)) {
            throw new AppInCartException(app.getId());
        }

        updateCartAPICall.execute(app, username, jwtToken);
    }

    public void deleteAppFromCart(Long appId, String jwtToken) {
        AppData app = getAppOrNotFound(appId);
        deleteCartAPICall.execute(app, jwtToken);
    }

    private AppData getAppOrNotFound(Long appId) {
        var appOptional = appDataRepository.findById(appId);
        if (appOptional.isEmpty()) {
            throw new AppDataDoesNotExistException();
        }
        return appOptional.get();
    }
}
