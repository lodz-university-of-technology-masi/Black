package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.masi.entity.Test;
import pl.masi.service.TestService;

import java.util.List;

@Controller
@RequestMapping(value = "/tests")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/all")
    public ResponseEntity<List<Test>> getAllBikes() {
        return new ResponseEntity<>(testService.findAll(), HttpStatus.OK);
    }
}
