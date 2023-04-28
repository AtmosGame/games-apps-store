package id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserPermission.*;

public enum UserRole {
    ADMIN(Sets.newHashSet(
            APPDATA_READ,
            APPDATA_CREATE,
            APPDATA_UPDATE,
            APPDATA_DELETE,
            VERIFICATION_READ,
            VERIFICATION_UPDATE
    )),
    DEVELOPER(Sets.newHashSet(
            APPDATA_READ,
            APPDATA_CREATE,
            APPDATA_UPDATE,
            APPDATA_DELETE,
            VERIFICATION_READ,
            VERIFICATION_UPDATE
    )),
    USER(Sets.newHashSet(
            APPDATA_READ,
            VERIFICATION_READ
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
