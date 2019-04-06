package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.masi.controller.base.EntityController;
import pl.masi.entity.Test;
import pl.masi.service.TestService;
import pl.masi.service.base.EntityService;

@Controller
@RequestMapping(value = "/tests")
public class TestController extends EntityController<Test> {

    @Autowired
    private TestService testService;

    @Override
    protected EntityService<Test> getEntityService() {
        return testService;
    }
}
