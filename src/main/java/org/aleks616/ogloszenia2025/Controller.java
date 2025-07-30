package org.aleks616.ogloszenia2025;


import org.aleks616.ogloszenia2025.OgloszenieService;
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
    public OgloszenieService getOgloszenieService(){
        return ogloszenieService;
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
        catch(OgloszenieService.EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/dodajogloszenie")
    public void addOgloszenie(@RequestBody String tresc) {
        ogloszenieService.addOgloszenie(tresc);
        //todo: throw if not string
    }

    @DeleteMapping("/usunogloszenie/{id}")
    public void deleteOgloszenie(@PathVariable long id){
        ogloszenieService.deleteOgloszenie(id);
    }

    @PatchMapping("/zmodyfikujogloszenie/{id}")
    public void patchOgloszenie(@PathVariable long id, @RequestBody String tresc){
        //todo: throw if not string
        ogloszenieService.patchOgloszenieTresc(id, tresc);
    }

}
//delete mapping
//put/patch mapping
//todo: tests, postman collection
