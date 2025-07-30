package org.aleks616.ogloszenia2025;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OgloszenieRepository extends JpaRepository<Ogloszenie, Long>{
    @Query("""
    SELECT o FROM Ogloszenie o WHERE o.id=:id
    """)
    Ogloszenie findOgloszenieById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("""
    UPDATE Ogloszenie SET iloscWyswietlen=iloscWyswietlen+1
    WHERE id=:id
    """)
    void incrementViewsById(@Param("id") long id);

    @Transactional
    @Modifying
    @Query(value="INSERT INTO ogloszenie (tresc, data_dodania) VALUES (:tresc, :dataDodania)", nativeQuery=true)
    void addOgloszenie(@Param("tresc") String tresc, @Param("dataDodania") LocalDateTime dataDodania);

    @Transactional
    @Modifying
    void deleteOgloszenieById(long id);
}