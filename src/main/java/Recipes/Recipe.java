package Recipes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Recipe {

    private @Id @GeneratedValue Long id;

    private String naam;
    private Integer bereidingstijd;
    private Integer aantalPersonen;
    private Integer thumbsUp;
    private Integer thumbsDown;

    @Lob
    private String ingredienten;
    @Lob
    private String bereidingswijze;

    public Recipe(Integer bereidingstijd, Integer aantalPersonen,
                  Integer thumbsUp, Integer thumbsdown,
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

    public Integer getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(Integer thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public Integer getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(Integer thumbsDown) {
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
}
