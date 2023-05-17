package id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserPermission.*;

public enum UserRole {
    ADMIN(Sets.newHashSet(
            APP_DATA_READ,
            APP_DATA_CREATE,
            APP_DATA_UPDATE,
            APP_DATA_DELETE,
            VERIFICATION_READ,
            VERIFICATION_VERIFY,
            VERIFICATION_REJECT
    )),
    DEVELOPER(Sets.newHashSet(
            APP_DATA_READ,
            APP_DATA_CREATE,
            APP_DATA_UPDATE,
            APP_DATA_DELETE,
            VERIFICATION_READ,
            VERIFICATION_REQUEST_REVERIFICATION,
            NOTIFICATION_BROADCAST
    )),
    USER(Sets.newHashSet(
            APP_DATA_READ,
            CART_READ,
            CART_ADD,
            CART_DELETE,
            NOTIFICATION_SUBSCRIBE,
            NOTIFICATION_UNSUBSCRIBE,
            NOTIFICATION_GET_NOTIF_BY_ID
    ));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(
                        permission.getPermission()
                ))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
