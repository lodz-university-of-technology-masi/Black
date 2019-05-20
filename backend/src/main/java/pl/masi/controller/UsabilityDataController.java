package pl.masi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.masi.entity.UsabilityData;
import pl.masi.service.UsabilityDataService;
import pl.masi.service.base.EntityService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@Primary
@RequestMapping(value = "/usability")
public class UsabilityDataController {

    @Autowired
    private UsabilityDataService service;

    protected EntityService<UsabilityData> getEntityService() {
        return service;
    }

    @GetMapping
    public ResponseEntity<List<UsabilityData>> findAll() {
        List<UsabilityData> entities = getEntityService().findAll();
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsabilityData> findById(@PathVariable Long id) {
        Optional<UsabilityData> entity = getEntityService().findById(id);
        if (entity.isPresent()) {
            return new ResponseEntity<>(entity.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UsabilityData> create(@RequestBody UsabilityData data, HttpServletRequest request) {
        data.setIp(request.getRemoteAddr());

        if (data.getId() != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UsabilityData created = getEntityService().create(data);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
