package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.masi.entity.Evaluation;
import pl.masi.entity.Test;
import pl.masi.entity.TestAnswer;
import pl.masi.entity.User;
import pl.masi.repository.UserRepository;
import pl.masi.service.base.EntityService;

import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService extends EntityService<User> implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static final HashSet<String> REDACTOR_CAN_CREATE = new HashSet<>();
    private static final HashSet<String> CANDIDATE_CAN_CREATE = new HashSet<>();

    static {
        CANDIDATE_CAN_CREATE.add(TestAnswer.class.getCanonicalName());
        REDACTOR_CAN_CREATE.add(Test.class.getCanonicalName());
        REDACTOR_CAN_CREATE.add(Evaluation.class.getCanonicalName());
    }

    public static boolean canUserCreate(User principal, String targetType) {
        switch (principal.getRole()) {
            case MODERATOR:
                return true;
            case REDACTOR:
                return REDACTOR_CAN_CREATE.contains(targetType);
            case CANDIDATE:
                return CANDIDATE_CAN_CREATE.contains(targetType);
            default:
                return false;
        }
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
        return (authentication != null && authentication.getPrincipal() != null) ? (User) authentication.getPrincipal() : null;
    }

    public User getCurrentUser() {
        return currentUser();
    }



    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void addUser(User userEntity) {
        User user= new User();
        user.setLogin(userEntity.getLogin());
        user.setEmail(userEntity.getEmail());
        user.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        this.userRepository.saveAndFlush(userEntity);

    }

    @Override
    protected JpaRepository<User, Long> getEntityRepository() {
        return userRepository;
    }
}
