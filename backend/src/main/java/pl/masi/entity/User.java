package pl.masi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.masi.entity.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "\"user\"")
public class User extends BaseEntity implements UserDetails {

    public enum Role implements GrantedAuthority {
        CANDIDATE,
        MODERATOR,
        REDACTOR;

        @Override
        public String getAuthority() {
            return "ROLE_" + name();
        }
    }

    @NotNull
    private String login;

    @NotNull
    private String email;

    @NotNull
    private String language;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String password;

    @JsonIgnore
    private String registrationToken;

    @Override
    @JsonIgnore
    public List<Role> getAuthorities() {
        return role == null ? Collections.emptyList() : Collections.singletonList(role);
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}
