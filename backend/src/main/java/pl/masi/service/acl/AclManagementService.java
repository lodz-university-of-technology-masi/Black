package pl.masi.service.acl;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.CumulativePermission;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import pl.masi.entity.User;
import pl.masi.entity.base.BaseEntity;
import pl.masi.service.UserService;

import java.util.Optional;

@Service
public class AclManagementService {

    public static CumulativePermission ALL_PERMISSIONS;
    static {
        CumulativePermission perms = new CumulativePermission();
        perms.set(BasePermission.READ);
        perms.set(BasePermission.WRITE);
        perms.set(BasePermission.DELETE);

        ALL_PERMISSIONS = perms;
    }

    @Autowired
    private MutableAclService aclService;

    @Transactional
    public void createDefaultPermissions(BaseEntity entity) {
        ObjectIdentity oid = new CustomObjectIdentity(entity.getClass().getCanonicalName(), entity.getId().toString());

        MutableAcl acl = aclService.createAcl(oid);

        acl.insertAce(0, ALL_PERMISSIONS, new PrincipalSid(getCurrentUserSid()), true);

        aclService.updateAcl(acl);
    }

    private String getCurrentUserSid() {
        return getUserSid(UserService.currentUser());
    }

    private String getUserSid(User user) {
        return user.getLogin();
    }

    @Transactional
    public void removePermissions(BaseEntity entity) {
        ObjectIdentity oid = new CustomObjectIdentity(entity.getClass().getCanonicalName(), entity.getId().toString());
        aclService.deleteAcl(oid, true);
    }

    @Transactional
    public void grantPermissions(BaseEntity entity, User user, Permission permissions) {
        ObjectIdentity oid = new CustomObjectIdentity(entity.getClass().getCanonicalName(), entity.getId().toString());
        PrincipalSid sid = new PrincipalSid(getUserSid(user));
        MutableAcl acl = null;

        acl = getMutableAcl(oid, sid);

        Optional<AccessControlEntry> userAce = acl.getEntries().stream().filter(ace -> ace.getSid().equals(sid)).findFirst();
        if (userAce.isPresent()) {
            int i = acl.getEntries().indexOf(userAce.get());
            CumulativePermission newPerms = new CumulativePermission();
            newPerms.set(userAce.get().getPermission())
                    .set(permissions);
            acl.updateAce(i, newPerms);
        } else {
            acl.insertAce(acl.getEntries().size(), permissions, sid, true);
        }

        aclService.updateAcl(acl);
    }

    private MutableAcl getMutableAcl(ObjectIdentity oid, PrincipalSid sid) {
        MutableAcl acl;
        try {
            Acl oldAcl = aclService.readAclById(oid, Lists.newArrayList(sid));
            Assert.isInstanceOf(MutableAcl.class, oldAcl, "MutableAcl should be been returned");
            acl = (MutableAcl) oldAcl;
        } catch (NotFoundException e) {
            acl = aclService.createAcl(oid);
        }
        return acl;
    }
}
