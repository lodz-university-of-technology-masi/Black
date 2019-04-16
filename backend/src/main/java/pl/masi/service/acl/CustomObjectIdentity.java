package pl.masi.service.acl;

import org.springframework.security.acls.model.ObjectIdentity;

import java.io.Serializable;
import java.util.Objects;


// Ta klasa jest konieczna do obejścia problemu https://github.com/spring-projects/spring-security/issues/5909
// hashCode musi być liczony dla wartości typu Long zamiast String
public class CustomObjectIdentity implements ObjectIdentity {

    private String identifier;
    private String type;

    public CustomObjectIdentity(String type, String identifier) {
        this.identifier = identifier;
        this.type = type;
    }

    @Override
    public Serializable getIdentifier() {
        return identifier;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectIdentity)) return false;
        ObjectIdentity that = (ObjectIdentity) o;
        return Objects.equals(getIdentifier().toString(), that.getIdentifier().toString()) &&
                Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        int code = 31;
        code ^= this.type.hashCode();
        code ^= Long.valueOf(this.identifier).hashCode();

        return code;
    }
}
