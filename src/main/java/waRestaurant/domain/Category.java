package waRestaurant.domain;

import jakarta.persistence.*;

//POJO class
@Entity
public class Category {
    @Id
    private int ID;
    @Column(name = "name", length = 20)
    private String name;

    //Builder
    public Category() {
    }
    public Category(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    // Getters and Setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
