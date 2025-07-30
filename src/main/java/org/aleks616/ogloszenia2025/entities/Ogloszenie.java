package org.aleks616.ogloszenia2025.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="ogloszenie")
public class Ogloszenie{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable=false)
    private long id;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id=id;
    }


    @Column(name="data_dodania")
    private LocalDateTime dataDodania;

    public LocalDateTime getDataDodania(){
        return dataDodania;
    }

    public void setDataDodania(LocalDateTime dataDodania){
        this.dataDodania=dataDodania;
    }

    @Column(name="tresc")
    private String tresc;

    public String getTresc(){
        return tresc;
    }

    public void setTresc(String tresc){
        this.tresc=tresc;
    }

    @Column(name="ilosc_wyswietlen", columnDefinition="DEFAULT 0")
    private int iloscWyswietlen;

    public int getIloscWyswietlen(){
        return iloscWyswietlen;
    }

    public void setIloscWyswietlen(int iloscWyswietlen){
        this.iloscWyswietlen=iloscWyswietlen;
    }

}
