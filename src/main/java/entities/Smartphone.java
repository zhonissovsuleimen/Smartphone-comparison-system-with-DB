package entities;

import dao.BrandDAO;
import dao.OSDAO;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

@Entity
@Table(name = "smartphones")
public class Smartphone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "smartphone_id")
    private int id;

    @Column(name = "name", nullable = true, unique = true)
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "brand_id", nullable = true, unique = false)
    private Brand brand;

    @Column(name = "releaseDate")
    private LocalDate releaseDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "os_id", nullable = true, unique = false)
    private OS os;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "resolutionX")
    private Integer resolutionX;

    @Column(name = "resolutionY")
    private Integer resolutionY;

    @Column(name = "depth")
    private Double depth;

    @Column(name = "width")
    private Double width;

    @Column(name = "height")
    private Double height;

    @Column(name = "storage")
    private Integer storage;

    @Column(name = "RAM")
    private Integer RAM;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "chipset_id", nullable = true, unique = false)
    private Chipset chipset;

    @Column(name = "color")
    private String color;

    @Column(name = "battery")
    private Integer battery;

    //METHODS
    public int getMaxStringLength(){
        int[] lengths = {
                name.length(),
                brand.getName().length(),
                releaseDate.toString().length(),
                weight.toString().length() + 1, //g
                resolutionX.toString().length() + resolutionY.toString().length() + 3, //x, px
                width.toString().length() + height.toString().length() + depth.toString().length() + 4, //x, x, mm
                storage.toString().length() + 2, //GB
                RAM.toString().length() + 2, //GB
                chipset.getName().length(),
                color.length(),
                battery.toString().length() + 3 //mAh
        };

        int maxStringLength = 0;
        for (int length:lengths) {
            maxStringLength = Math.max(maxStringLength, length);
        }
        return maxStringLength + 1;
    }

    @Override
    public String toString() {
        return  name + "\n" +
                brand + "\n" +
                releaseDate + "\n" +
                weight + "g\n" +
                resolutionX + "px\n" +
                resolutionY + "px\n" +
                height + "mm\n" +
                width + "mm\n" +
                depth + "mm\n" +
                storage + "GB\n" +
                RAM + "GB\n" +
                chipset + "\n" +
                battery + "mAh\n" +
                color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Smartphone that = (Smartphone) o;
        return Objects.equals(name, that.name) && Objects.equals(brand, that.brand) && Objects.equals(releaseDate, that.releaseDate) && Objects.equals(weight, that.weight) && Objects.equals(resolutionX, that.resolutionX) && Objects.equals(resolutionY, that.resolutionY) && Objects.equals(depth, that.depth) && Objects.equals(width, that.width) && Objects.equals(height, that.height) && Objects.equals(storage, that.storage) && Objects.equals(RAM, that.RAM) && Objects.equals(chipset, that.chipset) && Objects.equals(color, that.color) && Objects.equals(battery, that.battery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, brand, releaseDate, weight, resolutionX, resolutionY, depth, width, height, storage, RAM, chipset, color, battery);
    }

    //CONSTRUCTORS
    public Smartphone() {}

    public Smartphone(String name, Brand brand, LocalDate releaseDate, OS os, Double weight,
                      Integer resolutionX, Integer resolutionY, Double depth, Double width, Double height,
                      Integer storage, Integer RAM, Chipset chipset, String color, Integer battery) {
        this.name = name;
        this.brand = brand;
        this.releaseDate = releaseDate;
        this.os = os;
        this.weight = weight;
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.depth = depth;
        this.width = width;
        this.height = height;
        this.storage = storage;
        this.RAM = RAM;
        this.chipset = chipset;
        this.color = color;
        this.battery = battery;
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

    public Brand getBrand() {
        return brand;
    }
    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public OS getOS() {
        return os;
    }
    public void setOS(OS os) {
        this.os = os;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getWeight() {
        return weight;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getResolutionX() {
        return resolutionX;
    }
    public void setResolutionX(Integer resolutionX) {
        this.resolutionX = resolutionX;
    }

    public Integer getResolutionY() {
        return resolutionY;
    }
    public void setResolutionY(Integer resolutionY) {
        this.resolutionY = resolutionY;
    }

    public Double getDepth() {
        return depth;
    }
    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Double getWidth() {
        return width;
    }
    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }
    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getStorage() {
        return storage;
    }
    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public Integer getRAM() {
        return RAM;
    }
    public void setRAM(Integer RAM) {
        this.RAM = RAM;
    }

    public Chipset getChipset() {
        return chipset;
    }
    public void setChipset(Chipset chipset) {
        this.chipset = chipset;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public Integer getBattery() {
        return battery;
    }
    public void setBattery(Integer battery) {
        this.battery = battery;
    }
}
