package org.aleks616.ogloszenia2025.controllers;

import org.aleks616.ogloszenia2025.services.OgloszenieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller{
    private final OgloszenieService ogloszenieService;
    @Autowired
    public Controller(OgloszenieService ogloszenieService){
        this.ogloszenieService=ogloszenieService;
    }


    @GetMapping("/ogloszenie/{id}")
    public ResponseEntity<?> getOgloszenie(@PathVariable long id){
        try{
            return ResponseEntity.ok(ogloszenieService.viewOgloszenie(id));
        }
        catch(OgloszenieService.NumberFormatException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch(OgloszenieService.EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/dodajogloszenie")
    public ResponseEntity<?> addOgloszenie(@RequestBody String tresc) {
        try{
            ogloszenieService.addOgloszenie(tresc);
            return ResponseEntity.noContent().build();
        }
        catch(OgloszenieService.IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/usunogloszenie/{id}")
    public ResponseEntity<?> deleteOgloszenie(@PathVariable long id){
        try{
            ogloszenieService.deleteOgloszenie(id);
            return ResponseEntity.noContent().build();
        }
        catch(OgloszenieService.NumberFormatException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/zmodyfikujogloszenie/{id}")
    public ResponseEntity<?> patchOgloszenie(@PathVariable long id, @RequestBody String tresc){
        try{
            ogloszenieService.patchOgloszenieTresc(id, tresc);
            return ResponseEntity.noContent().build();
        }
        catch(OgloszenieService.NumberFormatException|OgloszenieService.IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
//todo: integration tests
