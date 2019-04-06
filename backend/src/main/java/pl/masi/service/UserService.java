package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.masi.entity.User;
import pl.masi.repository.UserRepository;
import pl.masi.service.base.EntityService;

@Service
public class UserService extends EntityService<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected JpaRepository<User, Long> getEntityRepository() {
        return userRepository;
    }
}
