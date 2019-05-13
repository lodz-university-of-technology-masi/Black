package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.masi.service.FilesService;

@Controller
@RequestMapping(value = "/files")
public class FilesController {

    @Autowired
    private FilesService service;

    @RequestMapping(path = "/import", method = {RequestMethod.POST})
    public ResponseEntity importTest(@RequestBody String csv) {
        //TODO MC Zabezpieczenia
        service.importTest(csv);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
