package pl.masi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.masi.entity.Position;
import pl.masi.entity.User;
import pl.masi.service.UserService;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator extends AclPermissionEvaluator {

    @Autowired
    public CustomPermissionEvaluator(AclService aclService) {
        super(aclService);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        String perm = (String) permission;
        User user = (User) authentication.getPrincipal();
        if (user.isModerator()) {
            return true;
        }
        if (perm.equals("CREATE")) {
            return UserService.canUserCreate(user, targetType);
        }

        return super.hasPermission(authentication, targetId, targetType, permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object domainObject, Object permission) {
        User user = (User) authentication.getPrincipal();
        if (user.isModerator()) {
            return true;
        }

        if ((domainObject instanceof Position || domainObject instanceof User) &&
                user.isRedactor() &&
                "READ".equals(permission)) {
            return true;
        }

        return super.hasPermission(authentication, domainObject, permission);
    }
}
