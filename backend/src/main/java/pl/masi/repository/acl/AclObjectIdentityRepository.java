package pl.masi.repository.acl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.masi.entity.acl.AclClass;
import pl.masi.entity.acl.AclObjectIdentity;

import java.util.Optional;

@Repository
public interface AclObjectIdentityRepository extends JpaRepository<AclObjectIdentity, Long> {
    Optional<AclObjectIdentity> findByObjectIdClassAndObjectIdIdentity(AclClass aclClass, String objectId);
}
