package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.masi.controller.base.EntityController;
import pl.masi.entity.TestAnswer;
import pl.masi.service.TestAnswerService;
import pl.masi.service.base.EntityService;

@Controller
@RequestMapping(value = "/answers")
public class TestAnswerController extends EntityController<TestAnswer> {

    @Autowired
    private TestAnswerService service;

    @Override
    protected EntityService<TestAnswer> getEntityService() {
        return service;
    }
}
