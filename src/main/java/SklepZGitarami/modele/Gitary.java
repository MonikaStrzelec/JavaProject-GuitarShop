package SklepZGitarami.modele;

import javax.persistence.*;

@Entity
@Table(name = "Gitary")
public class Gitary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "GITARA_ID")
    private int id;

    @Column(name = "producent")
    private String producent;

    @Column(name = "model")
    private String model;

    @Column(name = "cena")
    private float cena;

    @Column(name = "rodzaj")
    private int rodzaj;

    @Column(name = "ilosc")
    private int ilosc;


    public Gitary() {
    }

    public Gitary(String producent, String model, float cena, int rodzaj, int ilosc) {
        this.producent = producent;
        this.model = model;
        this.cena = cena;
        this.rodzaj = rodzaj;
        this.ilosc = ilosc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducent() {
        return producent;
    }

    public void setProducent(String producent) {
        this.producent = producent;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public int getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(int rodzaj) {
        this.rodzaj = rodzaj;
    }

    @Override
    public String toString() {
        return "Gitary [id=" + id + ", producent: " + producent + ", model: " + model + ", rodzaj: " + rodzaj + ", cena: " + cena + ", ilość: " + ilosc + "] \n";
    }


}
