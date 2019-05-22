package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.masi.controller.base.EntityController;
import pl.masi.dto.ChangePermsRequestDto;
import pl.masi.dto.TestInfoRequest;
import pl.masi.entity.Test;
import pl.masi.exception.BadRequestException;
import pl.masi.exception.PdfException;
import pl.masi.service.PdfService;
import pl.masi.service.TestService;
import pl.masi.service.base.EntityService;

import javax.persistence.EntityNotFoundException;

@Controller
@RequestMapping(value = "/tests")
public class TestController extends EntityController<Test> {

    @Autowired
    private TestService service;

    @Autowired
    private PdfService pdfService;

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

    @PostMapping(path = "/{id}/pdfexport", produces = MediaType.APPLICATION_PDF_VALUE, consumes = MediaType.ALL_VALUE)
    ResponseEntity<byte[]> pdfCreator(@PathVariable(name=  "id") Long id, @RequestBody TestInfoRequest infoRequest) throws BadRequestException, PdfException {
        try {
            Test test= service.findById(id).get();

            byte[] contents = pdfService.pdfCreator(test);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);

            // Czy infoRequest jest potrzebne? Nie można tutaj użyć po prostu test.getName(), test.getPosition() itd?
            String testname = infoRequest.getName() + "_" +
                    infoRequest.getPosition() + "_" +
                    infoRequest.getLanguage() + ".pdf";
            testname = testname.replaceAll("\\s+", "_");
            headers.setContentDispositionFormData(testname, testname);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            headers.add("testname", testname);
            return new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new BadRequestException(BadRequestException.NOT_EXISTING_DATA_REQUESTED);
        } catch (PdfException e) {
            throw new PdfException(PdfException.ERROR_CREATING_DOCUMENT);
        }
    }


}
