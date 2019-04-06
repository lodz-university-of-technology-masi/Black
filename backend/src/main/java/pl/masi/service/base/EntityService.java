package pl.masi.service.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.masi.entity.base.BaseEntity;

import java.util.Optional;

public abstract class EntityService<ENTITY extends BaseEntity> {

    @Transactional(readOnly = true)
    public Optional<ENTITY> findById(Long id) {
        return getEntityRepository().findById(id);
    }

    @Transactional
    public void delete(Long id) {
        getEntityRepository().deleteById(id);
    }

    @Transactional
    public void update(ENTITY entity) {
        getEntityRepository().save(entity);
    }

    @Transactional
    public ENTITY create(ENTITY entity) {
        return getEntityRepository().save(entity);
    }

    protected abstract JpaRepository<ENTITY, Long> getEntityRepository();
}
