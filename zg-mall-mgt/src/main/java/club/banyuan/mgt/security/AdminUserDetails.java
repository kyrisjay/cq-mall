package club.banyuan.mgt.security;


import club.banyuan.mgt.dao.entity.UmsAdmin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AdminUserDetails implements UserDetails {
    private UmsAdmin umsAdmin;
    private List<ResourceConfigAttribute> resources;

    public AdminUserDetails() {
    }

    public AdminUserDetails(UmsAdmin umsAdmin,
                            List<ResourceConfigAttribute> resources) {
        this.umsAdmin = umsAdmin;
        this.resources = resources;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return resources;
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return String.valueOf(umsAdmin.getId());
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus() == 1;
    }
}

