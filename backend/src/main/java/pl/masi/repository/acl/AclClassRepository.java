package pl.masi.repository.acl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.masi.entity.acl.AclClass;

import java.util.Optional;

@Repository
public interface AclClassRepository extends JpaRepository<AclClass, Long> {
    Optional<AclClass> getByClassName(String className);
}
