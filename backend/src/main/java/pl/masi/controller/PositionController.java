package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.masi.controller.base.EntityController;
import pl.masi.entity.Position;
import pl.masi.service.PositionService;
import pl.masi.service.base.EntityService;


@Controller
@RequestMapping(value = "/positions")
public class PositionController extends EntityController<Position> {

    @Autowired
    private PositionService service;

    @Override
    protected EntityService<Position> getEntityService() {
        return service;
    }
}
