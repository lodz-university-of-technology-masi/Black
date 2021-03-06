package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.masi.entity.Position;
import pl.masi.repository.PositionRepository;
import pl.masi.service.base.EntityService;
import pl.masi.validation.PositionValidator;
import pl.masi.validation.base.EntityValidator;

import java.util.List;
import java.util.Optional;

@Service
public class PositionService extends EntityService<Position> {

    @Autowired
    private PositionRepository repository;

    @Autowired
    private PositionValidator positionValidator;

    @Override
    protected JpaRepository<Position, Long> getEntityRepository() {
        return repository;
    }

    @Override
    public List<Position> findAll() {
        return super.findAll();
    }

    @Override
    public Optional<Position> findById(Long id) {
        return super.findById(id);
    }

    @Override
    protected EntityValidator<Position> getEntityValidator() {
        return positionValidator;
    }
}
