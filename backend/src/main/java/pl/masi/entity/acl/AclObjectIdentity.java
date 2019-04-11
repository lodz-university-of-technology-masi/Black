package pl.masi.entity.acl;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class AclObjectIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String objectIdIdentity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "object_id_class", referencedColumnName = "id", nullable = false)
    private AclClass objectIdClass;

    private Boolean entriesInheriting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_object", referencedColumnName = "id")
    private AclObjectIdentity parentObject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_sid", referencedColumnName = "id")
    private AclSid ownerSid;

    @OneToMany(targetEntity = AclEntry.class, fetch = FetchType.LAZY, mappedBy = "aclObjectIdentity", cascade = CascadeType.REMOVE)
    private Set<AclEntry> aclEntries = new HashSet<>();
}