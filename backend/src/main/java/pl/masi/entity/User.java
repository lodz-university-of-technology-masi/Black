package pl.masi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
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
        MODERATOR,
        REDACTOR,
        CANDIDATE;

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
    @NotNull
    private Role role;

    @JsonIgnore
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
        return login;
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

    @JsonIgnore
    public boolean isModerator() {
        return role == Role.MODERATOR;
    }

    @JsonIgnore
    public boolean isRedactor() {
        return role == Role.REDACTOR;
    }

    @JsonIgnore
    public boolean isCandidate() {
        return role == Role.CANDIDATE;
    }

}
