package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.masi.entity.UsabilityData;
import pl.masi.repository.UsabilityDataRepository;
import pl.masi.service.base.EntityService;

import java.time.ZonedDateTime;

@Service
public class UsabilityDataService extends EntityService<UsabilityData> {

    @Autowired
    private UsabilityDataRepository repository;

    @Autowired
    private UserService userService;

    @Override
    protected JpaRepository<UsabilityData, Long> getEntityRepository() {
        return repository;
    }

    @Override
    protected void beforeCreate(UsabilityData entity) {
        entity.setUsername(userService.getCurrentUser().getUsername());
        entity.setSaveTime(ZonedDateTime.now());
    }
}
