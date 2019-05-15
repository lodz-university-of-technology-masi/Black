package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.masi.service.FilesService;

import java.io.IOException;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> exportTest(@PathVariable Long id) {
        Optional<ByteArrayResource> optionalResource = service.exportTest(id);
        if (optionalResource.isPresent()) {
            ByteArrayResource resource = optionalResource.get();
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=test.csv")
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(resource.contentLength())
                    .body(resource);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
