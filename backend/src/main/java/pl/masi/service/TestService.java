package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.masi.entity.Test;
import pl.masi.repository.TestRepository;

import java.util.List;

@Component
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public List<Test> findAll() {
        return testRepository.findAll();
    }
}
