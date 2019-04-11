package pl.masi.repository.acl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.masi.entity.acl.AclEntry;
import pl.masi.entity.acl.AclObjectIdentity;

@Repository
public interface AclEntryRepository extends JpaRepository<AclEntry, Long> {
    int countByAclObjectIdentity(AclObjectIdentity identity);
}
