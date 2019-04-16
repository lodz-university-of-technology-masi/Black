package pl.masi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.masi.entity.TestAnswer;

@Repository
public interface TestAnswerRepository extends JpaRepository<TestAnswer, Long> {
}
