package Application.Recipes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.ArrayList;

@Entity
public class Recipe {

    private @Id @GeneratedValue Long id;

    private String naam;
    private Integer bereidingstijd;
    private Integer aantalPersonen;

    @Lob
    private ArrayList<String> thumbsUp;

    @Lob
    private ArrayList<String> thumbsDown;

    @Lob
    private String ingredienten;
    @Lob
    private String bereidingswijze;

    public Recipe(){}

    public Recipe(Integer bereidingstijd, Integer aantalPersonen,
                  ArrayList<String> thumbsUp, ArrayList<String> thumbsdown,
                  String ingredienten, String bereidingswijze, String naam){
        this.bereidingstijd = bereidingstijd;
        this.bereidingswijze = bereidingswijze;
        this.ingredienten = ingredienten;
        this.aantalPersonen = aantalPersonen;
        this.thumbsDown = thumbsdown;
        this.thumbsUp = thumbsUp;
        this.naam = naam;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBereidingstijd() {
        return bereidingstijd;
    }

    public void setBereidingstijd(Integer bereidingstijd) {
        this.bereidingstijd = bereidingstijd;
    }

    public Integer getAantalPersonen() {
        return aantalPersonen;
    }

    public void setAantalPersonen(Integer aantalPersonen) {
        this.aantalPersonen = aantalPersonen;
    }

    public ArrayList<String> getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(ArrayList<String> thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public ArrayList<String> getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(ArrayList<String> thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    public String getIngredienten() {
        return ingredienten;
    }

    public void setIngredienten(String ingredienten) {
        this.ingredienten = ingredienten;
    }

    public String getBereidingswijze() {
        return bereidingswijze;
    }

    public void setBereidingswijze(String bereidingswijze) {
        this.bereidingswijze = bereidingswijze;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
}
