package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.masi.dto.TestInfoRequest;
import pl.masi.entity.Test;
import pl.masi.exception.BadRequestException;
import pl.masi.exception.PdfException;
import pl.masi.service.PdfService;

import javax.persistence.EntityNotFoundException;



@Controller
@RequestMapping(value = "/files")
public class PdfController {


@Autowired
    private PdfService service;


    @PostMapping(path = "/{id}/pdfexport", produces = MediaType.APPLICATION_PDF_VALUE, consumes = MediaType.ALL_VALUE)
    ResponseEntity<byte[]> pdfCreator(@PathVariable(name=  "id")Long id, @RequestBody TestInfoRequest infoRequest) throws BadRequestException, PdfException {
        try {
            //tu nie dziala       \/
            Test test= service.findById(id);

            byte[] contents = service.pdfCreator(test);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
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
