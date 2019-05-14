package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.masi.service.FilesService;

import java.io.IOException;

@Controller
@RequestMapping(value = "/files")
public class FilesController {

    @Autowired
    private FilesService service;

    @RequestMapping(path = "/import", method = {RequestMethod.POST})
    public ResponseEntity importTest(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        //TODO MC Zabezpieczenia
        service.importTest(multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
