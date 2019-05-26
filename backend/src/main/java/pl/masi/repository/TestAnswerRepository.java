package pl.masi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.masi.entity.Test;
import pl.masi.entity.TestAnswer;
import pl.masi.entity.User;

import java.util.Optional;

@Repository
public interface TestAnswerRepository extends JpaRepository<TestAnswer, Long> {

    Optional<TestAnswer> findByTestAndUser(Test test, User user);
}
