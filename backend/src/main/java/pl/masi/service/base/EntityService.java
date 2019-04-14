package pl.masi.service.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import pl.masi.entity.base.BaseEntity;
import pl.masi.service.acl.AclManagementService;

import java.util.List;
import java.util.Optional;

public abstract class EntityService<ENTITY extends BaseEntity> {

    @Autowired
    protected AclManagementService aclManagementService;

    protected abstract JpaRepository<ENTITY, Long> getEntityRepository();

    @Transactional(readOnly = true)
    @PostFilter("hasRole('ROLE_MODERATOR') || hasPermission(filterObject, 'READ')")
    public List<ENTITY> findAll() {
        return getEntityRepository().findAll();
    }

    @Transactional(readOnly = true)
    @PostAuthorize("hasRole('ROLE_MODERATOR') || (returnObject.isPresent() && hasPermission(returnObject.get(), 'READ')) ")
    public Optional<ENTITY> findById(Long id) {
        return getEntityRepository().findById(id);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_MODERATOR') || hasPermission(#entity, 'DELETE')")
    public void delete(ENTITY entity) {
        beforeDelete(entity);
        getEntityRepository().delete(entity);
        aclManagementService.removePermissions(entity);
        afterDelete(entity);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_MODERATOR') || hasPermission(#entity, 'WRITE')")
    public void update(ENTITY entity) {
        beforeUpdate(entity);
        ENTITY e = getEntityRepository().save(entity);
        afterUpdate(e);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_MODERATOR') || hasPermission(null, #entity.getClass().getCanonicalName(), 'CREATE')")
    public ENTITY create(ENTITY entity) {
        beforeCreate(entity);
        ENTITY e = getEntityRepository().save(entity);
        aclManagementService.createDefaultPermissions(e);
        afterCreate(e);
        return e;
    }

    protected void beforeCreate(ENTITY entity) { }
    protected void afterCreate(ENTITY entity) { }
    protected void beforeUpdate(ENTITY entity) { }
    protected void afterUpdate(ENTITY entity) { }
    protected void beforeDelete(ENTITY entity) { }
    protected void afterDelete(ENTITY entity) { }

}
