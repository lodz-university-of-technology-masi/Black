package pl.masi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.masi.entity.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
}
