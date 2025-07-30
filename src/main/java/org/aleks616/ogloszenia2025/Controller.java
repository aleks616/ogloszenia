package org.aleks616.ogloszenia2025;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api")
public class Controller{
    private final OgloszenieService ogloszenieService;
    @Autowired
    public Controller(OgloszenieService ogloszenieService){
        this.ogloszenieService=ogloszenieService;
    }

    @GetMapping(value="/", produces=MediaType.TEXT_HTML_VALUE)
    public String mainApi() throws IOException{
        Resource resource=new ClassPathResource("static/README.md");
        return new String(Files.readAllBytes(resource.getFile().toPath()));
    }


    @GetMapping("/ogloszenie/{id}")
    public ResponseEntity<?> getOgloszenie(@PathVariable long id){
        try{
            return ResponseEntity.ok(ogloszenieService.viewOgloszenie(id));
        }
        catch(OgloszenieService.NumberFormatException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch(OgloszenieService.EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/dodajogloszenie")
    public void addOgloszenie(@RequestBody String tresc) {
        ogloszenieService.addOgloszenie(tresc);
    }

    @DeleteMapping("/usunogloszenie/{id}")
    public ResponseEntity<?> deleteOgloszenie(@PathVariable long id){
        try{
            ogloszenieService.deleteOgloszenie(id);
            return ResponseEntity.noContent().build();
        }
        catch(OgloszenieService.NumberFormatException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/zmodyfikujogloszenie/{id}")
    public ResponseEntity<?> patchOgloszenie(@PathVariable long id, @RequestBody String tresc){
        try{
            ogloszenieService.patchOgloszenieTresc(id, tresc);
            return ResponseEntity.noContent().build();
        }
        catch(OgloszenieService.NumberFormatException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
//todo: tests
