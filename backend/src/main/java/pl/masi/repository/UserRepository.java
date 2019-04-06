package pl.masi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.masi.entity.Test;
import pl.masi.entity.User;

@Repository
public
interface UserRepository extends JpaRepository<User, Long> {
}
