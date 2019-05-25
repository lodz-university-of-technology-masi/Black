package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.masi.dto.RegistrationRequestDto;
import pl.masi.entity.*;
import pl.masi.repository.UserRepository;
import pl.masi.service.base.EntityService;
import pl.masi.utils.RegisterValidator;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends EntityService<User> implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisterValidator registerValidator;

    private static final HashSet<String> REDACTOR_CAN_CREATE = new HashSet<>();
    private static final HashSet<String> CANDIDATE_CAN_CREATE = new HashSet<>();

    static {
        CANDIDATE_CAN_CREATE.add(TestAnswer.class.getCanonicalName());
        CANDIDATE_CAN_CREATE.add(UsabilityData.class.getCanonicalName());

        REDACTOR_CAN_CREATE.add(Test.class.getCanonicalName());
        REDACTOR_CAN_CREATE.add(Evaluation.class.getCanonicalName());
        REDACTOR_CAN_CREATE.add(UsabilityData.class.getCanonicalName());
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

    public Optional<User> getByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public static User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && authentication.getPrincipal() instanceof User) ? (User) authentication.getPrincipal() : null;
    }

    public User getCurrentUser() {
        return currentUser();
    }


    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);

    @Transactional
    public void addUser(RegistrationRequestDto userEntity) {

        registerValidator.validate(userEntity);

        User user = new User();
        user.setLogin(userEntity.getLogin());
        user.setEmail(userEntity.getEmail());
        user.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        user.setRole(User.Role.CANDIDATE);
        user.setLanguage(userEntity.getLanguage());
        this.create(user);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public void addRedactor(RegistrationRequestDto userEntity) {
        registerValidator.validate(userEntity);

        User user = new User();
        user.setLogin(userEntity.getLogin());
        user.setEmail(userEntity.getEmail());
        user.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        user.setRole(User.Role.REDACTOR);
        user.setLanguage(userEntity.getLanguage());
        this.create(user);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public List<User> findAllByRole(User.Role role) {
        return userRepository.findAllByRole(role);
    }

    protected JpaRepository<User, Long> getEntityRepository() {
        return userRepository;
    }

}
