package pl.masi.service.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.CumulativePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Service;
import pl.masi.entity.acl.AclClass;
import pl.masi.entity.acl.AclEntry;
import pl.masi.entity.acl.AclObjectIdentity;
import pl.masi.entity.acl.AclSid;
import pl.masi.entity.base.BaseEntity;
import pl.masi.repository.acl.AclClassRepository;
import pl.masi.repository.acl.AclEntryRepository;
import pl.masi.repository.acl.AclObjectIdentityRepository;
import pl.masi.repository.acl.AclSidRepository;
import pl.masi.service.UserService;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AclManagementService {

    private static CumulativePermission DEFAULT_PERMISSIONS;
    static {
        CumulativePermission perms = new CumulativePermission();
        perms.set(BasePermission.CREATE);
        perms.set(BasePermission.READ);
        perms.set(BasePermission.WRITE);
        perms.set(BasePermission.DELETE);

        DEFAULT_PERMISSIONS = perms;
    }

    @Autowired
    private AclSidRepository aclSidRepository;

    @Autowired
    private AclEntryRepository aclEntryRepository;

    @Autowired
    private AclObjectIdentityRepository aclObjectIdentityRepository;

    @Autowired
    private AclClassRepository aclClassRepository;

    @Transactional
    public void createDefaultPermissions(BaseEntity entity) {
        AclSid aclSid = getAclSid(getCurrentUserSid());

        AclClass aclClass = getAclClass(entity);

        Optional<AclObjectIdentity> aclObjectIdentity = aclObjectIdentityRepository.findByObjectIdClassAndObjectIdIdentity(aclClass, entity.getId().toString());

        // Jeżeli istnieje to obiekt posiada już domyślne uprawnienia
        if (aclObjectIdentity.isPresent()){
            return;
        }

        AclObjectIdentity newAclObjectIdentity = createAclObjectIdentity(aclClass, aclSid, entity.getId().toString());

        createAclEntry(DEFAULT_PERMISSIONS, aclSid, newAclObjectIdentity);
    }

    private void createAclEntry(Permission permission, AclSid aclSid, AclObjectIdentity aclAObjectIdentity) {
        AclEntry newAclEntry = new AclEntry();
        newAclEntry.setAceOrder(getOrder(aclAObjectIdentity));
        newAclEntry.setGranting(true);
        newAclEntry.setAuditFailure(true);
        newAclEntry.setAuditSuccess(false);
        newAclEntry.setAclObjectIdentity(aclAObjectIdentity);
        newAclEntry.setSid(aclSid);
        newAclEntry.setMask(permission.getMask());
        aclEntryRepository.save(newAclEntry);
    }


    private AclObjectIdentity createAclObjectIdentity(AclClass aclClass, AclSid aclSid, String objectId){
        AclObjectIdentity aclObjectIdentity = new AclObjectIdentity();
        aclObjectIdentity.setOwnerSid(aclSid);
        aclObjectIdentity.setObjectIdClass(aclClass);
        aclObjectIdentity.setObjectIdIdentity(objectId);
        aclObjectIdentity.setEntriesInheriting(false);
        return aclObjectIdentityRepository.save(aclObjectIdentity);

    }

    private AclClass getAclClass(BaseEntity entity) {
        String className = entity.getClass().getCanonicalName();

        Optional<AclClass> aclClass = aclClassRepository.getByClassName(className);

        if(aclClass.isPresent()){
            return aclClass.get();
        }

        AclClass newAclClass = new AclClass();
        newAclClass.setClassName(className);
        return aclClassRepository.save(newAclClass);
    }

    private AclSid getAclSid(String sid){

        Optional<AclSid> aclSid = aclSidRepository.findBySid(sid);

        if (aclSid.isPresent()){
            return aclSid.get();
        }

        AclSid newSid = new AclSid();
        newSid.setSid(sid);
        return aclSidRepository.save(newSid);
    }

    private int getOrder(AclObjectIdentity identity) {
        return aclEntryRepository.countByAclObjectIdentity(identity);
    }

    private String getCurrentUserSid() {
        return UserService.currentUser().getLogin();
    }
}
