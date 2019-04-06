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
    public void delete(ENTITY entity) {
        beforeDelete(entity);
        getEntityRepository().delete(entity);
        afterDelete(entity);
    }

    @Transactional
    public void update(ENTITY entity) {
        beforeUpdate(entity);
        ENTITY e = getEntityRepository().save(entity);
        afterUpdate(entity);
    }

    @Transactional
    public ENTITY create(ENTITY entity) {
        beforeCreate(entity);
        ENTITY e = getEntityRepository().save(entity);
        afterCreate(e);
        return e;
    }

    protected void beforeCreate(ENTITY entity) { }
    protected void afterCreate(ENTITY entity) { }
    protected void beforeUpdate(ENTITY entity) { }
    protected void afterUpdate(ENTITY entity) { }
    protected void beforeDelete(ENTITY entity) { }
    protected void afterDelete(ENTITY entity) { }


    protected abstract JpaRepository<ENTITY, Long> getEntityRepository();
}
