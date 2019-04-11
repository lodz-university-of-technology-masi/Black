package pl.masi.entity.acl;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class AclEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer aceOrder;

    private Integer mask;

    private Boolean granting;

    private Boolean auditSuccess;

    private Boolean auditFailure;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "acl_object_identity", referencedColumnName = "id", nullable = false)
    private AclObjectIdentity aclObjectIdentity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sid", referencedColumnName = "id", nullable = false)
    private AclSid sid;
}

