package pl.masi.service.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.CumulativePermission;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.masi.entity.base.BaseEntity;
import pl.masi.service.UserService;

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
    private MutableAclService aclService;

    @Transactional
    public void createDefaultPermissions(BaseEntity entity) {
        ObjectIdentity oid = new CustomObjectIdentity(entity.getClass().getCanonicalName(), entity.getId().toString());

        MutableAcl acl = aclService.createAcl(oid);

        acl.insertAce(0, DEFAULT_PERMISSIONS, new PrincipalSid(getCurrentUserSid()), true);

        aclService.updateAcl(acl);
    }

    private String getCurrentUserSid() {
        return UserService.currentUser().getLogin();
    }

    @Transactional
    public void removePermissions(BaseEntity entity) {
        ObjectIdentity oid = new CustomObjectIdentity(entity.getClass().getCanonicalName(), entity.getId().toString());
        aclService.deleteAcl(oid, true);
    }
}
