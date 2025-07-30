package org.aleks616.ogloszenia2025;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.time.LocalDateTime;

@Service
public class OgloszenieService{
    @ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Ogłoszenie nie znalezione")
    public static class EntityNotFoundException extends RuntimeException{
        public EntityNotFoundException(String message){
            super(message);
        }
    }

    @ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Id ogłoszenia musi być typu long")
    public static class NumberFormatException extends RuntimeException{
        public NumberFormatException(String message){
            super(message);
        }
    }


    private final OgloszenieRepository repository;

    public OgloszenieService(OgloszenieRepository repository){
        this.repository=repository;
    }

    public Ogloszenie viewOgloszenie(long id){
        repository.incrementViewsById(id);
        Ogloszenie ogloszenie=repository.findOgloszenieById(id);
        if(ogloszenie==null) throw new EntityNotFoundException("Nie znaleziono ogłoszenia o ID "+id);
        else return ogloszenie;
    }

    public void addOgloszenie(String tresc){
        LocalDateTime now=LocalDateTime.now();
        repository.addOgloszenie(tresc,now);
    }

    public void deleteOgloszenie(long id){
        if(repository.findOgloszenieById(id)==null)
            throw new EntityNotFoundException("Ogłoszenie o ID "+id+" już nie istnieje, nie można usunąć");

        repository.deleteOgloszenieById(id);
    }

    public void patchOgloszenieTresc(long id, String tresc){
        if(repository.findOgloszenieById(id)==null)
            throw new EntityNotFoundException("Ogłoszenie o ID "+id+" już nie istnieje, nie można zmodyfikować");
        //alternatywnie, jeśli nie istnieje, można dodać: addOgloszenie(tresc);

        repository.modifyOgloszenieTresc(id,tresc);
    }

}
