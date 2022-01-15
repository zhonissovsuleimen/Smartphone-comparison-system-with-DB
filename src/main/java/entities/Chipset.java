package entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Chipset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chipset_id")
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "chipset")
    private List<Smartphone> smartphoneList;

    //CONSTRUCTORS
    public Chipset() {}

    public Chipset(String name) {
        this.name = name;
    }

    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Smartphone> getSmartphoneList() {
        return smartphoneList;
    }
    public void setSmartphoneList(List<Smartphone> smartphoneList) {
        this.smartphoneList = smartphoneList;
    }
}
