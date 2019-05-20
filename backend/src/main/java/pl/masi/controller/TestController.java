package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.masi.controller.base.EntityController;
import pl.masi.dto.ChangePermsRequestDto;
import pl.masi.entity.Test;
import pl.masi.service.TestService;
import pl.masi.service.base.EntityService;

@Controller
@RequestMapping(value = "/tests")
public class TestController extends EntityController<Test> {

    @Autowired
    private TestService service;

    @Override
    protected EntityService<Test> getEntityService() {
        return service;
    }

    @PostMapping(path = "/{id}/translate")
    public ResponseEntity<Test> translate(@PathVariable(name = "id") Long id, @RequestParam(name = "lang" ) String targetLang) {
        Test test = service.findById(id).get();

        return new ResponseEntity<>(service.translate(test, targetLang), HttpStatus.OK);
    }

    @PostMapping(path = "/{id}/perms")
    public ResponseEntity changePerms(@PathVariable(name = "id") Long id, @RequestBody ChangePermsRequestDto changePermsRequest) {
        if (changePermsRequest.getTestId() == null || !changePermsRequest.getTestId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        service.changePerms(changePermsRequest);
        return ResponseEntity.ok().build();
    }
}
