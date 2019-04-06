package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import pl.masi.entity.Position;
import pl.masi.entity.Test;
import pl.masi.repository.PositionRepository;
import pl.masi.repository.TestRepository;
import pl.masi.service.base.EntityService;

@Component
public class PositionService extends EntityService<Position> {

    @Autowired
    private PositionRepository repository;

    @Override
    protected JpaRepository<Position, Long> getEntityRepository() {
        return repository;
    }
}
