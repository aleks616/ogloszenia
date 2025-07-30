package org.aleks616.ogloszenia2025.services;

import org.aleks616.ogloszenia2025.entities.Ogloszenie;
import org.aleks616.ogloszenia2025.repositories.OgloszenieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.time.LocalDateTime;

@Service
public class OgloszenieService{

    //region responses
    @ResponseStatus(code=HttpStatus.BAD_REQUEST, reason="Treść ogłoszenia nie może być nullem")
    public static class IllegalArgumentException extends RuntimeException{
        public IllegalArgumentException(String message){
            super(message);
        }
    }
    @ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Ogłoszenie nie znalezione")
    public static class EntityNotFoundException extends RuntimeException{
        public EntityNotFoundException(String message){
            super(message);
        }
    }

    @ResponseStatus(code=HttpStatus.BAD_REQUEST, reason="Id ogłoszenia musi być typu long")
    public static class NumberFormatException extends RuntimeException{
        public NumberFormatException(String message){
            super(message);
        }
    }
    //endregion


    private final OgloszenieRepository repository;
    public OgloszenieService(OgloszenieRepository repository){
        this.repository=repository;
    }

    public Ogloszenie viewOgloszenie(long id){
        if(id<1) throw new NumberFormatException("ID nie może być mniejsze od 1");
        repository.incrementViewsById(id);
        Ogloszenie ogloszenie=repository.findOgloszenieById(id);
        if(ogloszenie==null) throw new EntityNotFoundException("Nie znaleziono ogłoszenia o ID "+id);
        else return ogloszenie;
    }

    public void addOgloszenie(String tresc){
        if(tresc==null) throw new java.lang.IllegalArgumentException();
        LocalDateTime now=LocalDateTime.now();
        repository.addOgloszenie(tresc,now);
    }

    public void deleteOgloszenie(long id){
        if(id<1) throw new NumberFormatException("ID nie może być mniejsze od 1");
        if(repository.findOgloszenieById(id)==null)
            throw new EntityNotFoundException("Ogłoszenie o ID "+id+" już nie istnieje, nie można usunąć");

        repository.deleteOgloszenieById(id);
    }

    public void patchOgloszenieTresc(long id, String tresc){
        if(id<1) throw new NumberFormatException("ID nie może być mniejsze od 1");
        if(tresc==null) throw new java.lang.IllegalArgumentException();
        if(repository.findOgloszenieById(id)==null)
            throw new EntityNotFoundException("Ogłoszenie o ID "+id+" już nie istnieje, nie można zmodyfikować");
        //alternatywnie, jeśli nie istnieje, można dodać: addOgloszenie(tresc);

        repository.modifyOgloszenieTresc(id,tresc);
    }

    /// Normalnie na pewno by się przydało,
    /// ale w tym przypadku jest to funkcja tylko do testów
    public long getNewestId(){
        return repository.findTopById();
    }

}
