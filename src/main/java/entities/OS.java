package entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "os", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "version"})})
public class OS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "os_id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "version", nullable = false)
    private String version;

    @OneToMany(mappedBy = "os")
    private List<Smartphone> smartphoneList;

    //CONSTRUCTORS
    public OS() {}

    public OS(String name, String version){
        this.name = name;
        this.version = version;
    }

    //GETTERS AND SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
}
