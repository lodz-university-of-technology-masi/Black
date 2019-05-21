package pl.masi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.masi.entity.UsabilityData;

@Repository
public interface UsabilityDataRepository extends JpaRepository<UsabilityData, Long> {

    @Query("select max (ud.measurementNumber) " +
            "from UsabilityData ud " +
            "where ud.username = :username")
    Integer getMeasurementNumber(@Param("username")String username);
}
