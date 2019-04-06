package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.masi.controller.base.EntityController;
import pl.masi.entity.User;
import pl.masi.service.UserService;
import pl.masi.service.base.EntityService;


@Controller
@RequestMapping(value = "/users")
public class UserController extends EntityController<User> {

    @Autowired
    private UserService service;

    @Override
    protected EntityService<User> getEntityService() {
        return service;
    }
}
