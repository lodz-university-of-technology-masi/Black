package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.masi.controller.base.EntityController;
import pl.masi.entity.Test;
import pl.masi.service.TestService;
import pl.masi.service.base.EntityService;

import java.util.Optional;

@Controller
@RequestMapping(value = "/tests")
public class TestController extends EntityController<Test> {

    @Autowired
    private TestService service;

    @Override
    protected EntityService<Test> getEntityService() {
        return service;
    }

    @PostMapping(path = "/translate/{id}")
    public ResponseEntity<Test> translate(@PathVariable(name = "id") Long id, @RequestParam(name = "lang" ) String targetLang) {
        Optional<Test> test = service.findById(id);
        if(!test.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.translate(test.get(), targetLang), HttpStatus.OK);
    }
}
