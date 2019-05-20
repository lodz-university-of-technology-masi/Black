package pl.masi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.masi.entity.UsabilityData;

@Repository
public interface UsabilityDataRepository extends JpaRepository<UsabilityData, Long> {
}
