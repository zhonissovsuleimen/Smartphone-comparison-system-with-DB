package entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private int id;

    @Column(name="name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "brand")
    private List<Smartphone> smartphoneList;

    //CONSTRUCTORS
    public Brand() {}

    public Brand(String name) {
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
