package org.aleks616.ogloszenia2025;


import org.aleks616.ogloszenia2025.services.OgloszenieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class Ogloszenia2025ApplicationTests{
    @Test
    void contextLoads(){
    }

    @Autowired
    private OgloszenieService ogloszenieService;

    /**
     * Tworzy nowe ogłoszenie, przypisuje jego id do zmiennej,
     * wyświetla je i sprawdza,
     * czy ilość wyświetleń jest równa 1
     */
    @Test
    void wyswietlenia_Increments(){
        ogloszenieService.addOgloszenie("test wyświetleń");
        long id=ogloszenieService.getNewestId();
        int views=ogloszenieService.viewOgloszenie(id).getIloscWyswietlen();

        assertThat(views).isEqualTo(1);
    }


    /**
     * Test integracyjny:
     * tworzy nowe ogłoszenie,
     * modyfikuje je,
     * usuwa je,
     * sprawdza, czy zostało usunięte (EntityNotFoundException)
     */
    @Test
    void ogloszenie_fullPath(){
        /// tworzenie
        ogloszenieService.addOgloszenie("testowe");
        long id=ogloszenieService.getNewestId();

        assertNotNull(ogloszenieService.viewOgloszenie(id));

        ogloszenieService.patchOgloszenieTresc(id,"nowa treść testowa");
        assertEquals("nowa treść testowa",ogloszenieService.viewOgloszenie(id).getTresc());

        /// 3, ponieważ 2 wyżej + to wyświetlenie
        assertEquals(3,ogloszenieService.viewOgloszenie(id).getIloscWyswietlen());

        ogloszenieService.deleteOgloszenie(id);
        assertThrows(OgloszenieService.EntityNotFoundException.class,
                ()->ogloszenieService.deleteOgloszenie(id));
        /// Jeśli faktycznie zostało usunięte to powinien być błąd
    }

    /// Sprawdza, czy tresc null powoduje IllegalArgumentException.
    //region IllegalArgumentException
    @Test
    void add_nullTresc_ThrowsException(){
        assertThrows(IllegalArgumentException.class,()->ogloszenieService.addOgloszenie(null));
    }

    @Test
    void patch_nullTresc_ThrowsException(){
        assertThrows(IllegalArgumentException.class,
                ()->ogloszenieService.patchOgloszenieTresc(1L,null));
    }
    //endregion

    /// Niewłaściwe id, czyli poniżej 1 -> powinien być NumberFormatException.
    //region NumberFormatException
    @Test
    void get_notNumericId_ThrowsException(){
        assertThrows(OgloszenieService.NumberFormatException.class,
                ()->ogloszenieService.viewOgloszenie(-1));
    }

    @Test
    void patch_notNumericId_ThrowsException(){
        assertThrows(OgloszenieService.NumberFormatException.class,
                ()->ogloszenieService.patchOgloszenieTresc(-1,"cokolwiek"));
    }

    @Test
    void delete_notNumericId_ThrowsException(){
        assertThrows(OgloszenieService.NumberFormatException.class,
                ()->ogloszenieService.deleteOgloszenie(-1));
    }
    //endregion

    /// Nie ma ogłoszenia o danym id. EntityNotFoundException
    //region EntityNotFoundException
    @Test
    void view_notFoundId_ThrowsException(){
        assertThrows(OgloszenieService.EntityNotFoundException.class,
                ()->ogloszenieService.viewOgloszenie(6005002003004L)); //zakładając, że tylu nie będzie, w teorii może być
    }

    @Test
    void delete_notFoundId_ThrowsException(){
        assertThrows(OgloszenieService.EntityNotFoundException.class,
                ()->ogloszenieService.deleteOgloszenie(6005002003004L));
    }

    @Test
    void patch_notFoundId_ThrowsException(){
        assertThrows(OgloszenieService.EntityNotFoundException.class,
                ()->ogloszenieService.patchOgloszenieTresc(6005002003004L,"cokolwiek"));
    }
    //endregion
}
