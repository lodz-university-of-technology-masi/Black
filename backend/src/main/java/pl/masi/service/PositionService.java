package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import pl.masi.entity.Position;
import pl.masi.repository.PositionRepository;
import pl.masi.service.base.EntityService;

import java.util.List;
import java.util.Optional;

@Component
public class PositionService extends EntityService<Position> {

    @Autowired
    private PositionRepository repository;

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
}
