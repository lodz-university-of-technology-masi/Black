package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.masi.entity.User;
import pl.masi.repository.UserRepository;
import pl.masi.service.base.EntityService;

import java.util.Optional;

@Service
public class UserService extends EntityService<User> implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected JpaRepository<User, Long> getEntityRepository() {
        return userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("No such username");
        }
    }

    public static User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && authentication.getPrincipal() != null) ? (User)authentication.getPrincipal() : null;
    }

    public User getCurrentUser() {
        return currentUser();
    }
}
