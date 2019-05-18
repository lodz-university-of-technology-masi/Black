package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.masi.service.SynonymService;

import java.util.List;

@Controller
public class UtilsController {

    @Autowired
    private SynonymService synonymService;

    @GetMapping(path = "/synonyms")
    public ResponseEntity<List<String>> translate(@RequestParam(name = "word") String word) {
        List<String> synonyms = synonymService.findSynonyms(word);

        return new ResponseEntity<>(synonyms, HttpStatus.OK);
    }
}
