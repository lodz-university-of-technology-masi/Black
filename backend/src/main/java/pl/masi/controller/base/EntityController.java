package pl.masi.controller.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.masi.entity.base.BaseEntity;
import pl.masi.service.base.EntityService;

import java.util.List;
import java.util.Optional;

public abstract class EntityController<ENTITY extends BaseEntity> {

    protected abstract EntityService<ENTITY> getEntityService();

    @GetMapping
    public ResponseEntity<List<ENTITY>> findAll() {
        List<ENTITY> entities = getEntityService().findAll();
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ENTITY> findById(@PathVariable Long id) {
        Optional<ENTITY> entity = getEntityService().findById(id);
        if (entity.isPresent()) {
            return new ResponseEntity<>(entity.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<ENTITY> create(@RequestBody ENTITY entity) {
        if (entity.getId() != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ENTITY created = getEntityService().create(entity);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", method = {RequestMethod.POST, RequestMethod.PATCH})
    public ResponseEntity update(@PathVariable Long id, @RequestBody ENTITY entity) {
        if (entity.getId() == null || !entity.getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        getEntityService().update(entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable(name = "id") ENTITY entity) {
        getEntityService().delete(entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
