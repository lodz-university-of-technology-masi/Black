package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.masi.controller.base.EntityController;
import pl.masi.entity.Evaluation;
import pl.masi.service.EvaluationService;
import pl.masi.service.base.EntityService;

@Controller
@RequestMapping(value = "/evaluations")
public class EvaluationController extends EntityController<Evaluation> {

    @Autowired
    private EvaluationService service;

    @Override
    protected EntityService<Evaluation> getEntityService() {
        return service;
    }
}
