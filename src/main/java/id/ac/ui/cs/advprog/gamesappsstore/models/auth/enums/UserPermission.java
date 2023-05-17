package id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums;

public enum UserPermission {
    APP_DATA_READ("app_data:read"),
    APP_DATA_CREATE("app_data:create"),
    APP_DATA_UPDATE("app_data:update"),
    APP_DATA_DELETE("app_data:delete"),
    VERIFICATION_READ("verification:read"),
    VERIFICATION_VERIFY("verification:verify"),
    VERIFICATION_REJECT("verification:reject"),
    VERIFICATION_REQUEST_REVERIFICATION("verification:request_reverification"),
    CART_READ("cart:read"),
    CART_ADD("cart:add"),
    CART_DELETE("cart:delete"),
    NOTIFICATION_SUBSCRIBE("notification:subscribe"),
    NOTIFICATION_UNSUBSCRIBE("notification:unsubscribe"),
    NOTIFICATION_BROADCAST("notification:broadcast"),
    NOTIFICATION_GET_NOTIF_BY_ID("notification:get_notification_by_id");



    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
