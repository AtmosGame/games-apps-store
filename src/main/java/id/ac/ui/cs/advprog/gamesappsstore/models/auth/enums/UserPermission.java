package id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums;

public enum UserPermission {
    APPDATA_READ("app_data:read"),
    APPDATA_CREATE("app_data:create"),
    APPDATA_UPDATE("app_data:update"),
    APPDATA_DELETE("app_data:delete"),
    VERIFICATION_READ("verification:read"),
    VERIFICATION_UPDATE("verification:update");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
