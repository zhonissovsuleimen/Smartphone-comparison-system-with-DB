package jframes;

import entities.Smartphone;
import services.ApplicationLogger;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class ComparisonFrame extends JFrame {
    List<Smartphone> selectedSmartphones;
    Integer cellWidth = 140, cellHeight = 50;
    //COLORS
    Color borderColor = new Color(86,99,247);
    Color backgroundColor = new Color(44, 47, 51);

    public ComparisonFrame(List<Smartphone> smartphones){
        this.selectedSmartphones = smartphones;
        String title = "Comparison of ";
        for (int i = 0; i < smartphones.size(); i++) {
            String symbol = i == smartphones.size() - 1 ? " " : ", ";
            title += smartphones.get(i).getName() + symbol;
        }
        this.setTitle(title);
        this.setResizable(false);
        this.setSize((smartphones.size()+1)*(cellWidth+9), 12 * (cellHeight+3));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.LEADING,1,0));
        this.setBackground(backgroundColor);

        //NAME
        AddDefaultLabel("Name");
        for (Smartphone s: selectedSmartphones) {
            AddNewLabel(s.getName(),false);
        }
        //BRANDS
        AddDefaultLabel("Brands");
        for (Smartphone s: selectedSmartphones) {
            AddNewLabel(s.getBrand().getName(),false);
        }
        //RELEASE DATE
        AddDefaultLabel("Release Date");
        for (Smartphone s: selectedSmartphones) {
            boolean bold = Objects.equals(s.getReleaseDate(), selectedSmartphones.stream().max((a, b) -> a.getReleaseDate().compareTo(b.getReleaseDate())).get().getReleaseDate());
            AddNewLabel(s.getReleaseDate().toString(),bold);
        }
        //OS
        AddDefaultLabel("OS");
        for (Smartphone s: selectedSmartphones) {
            AddNewLabel(s.getOS().getName() + " " + s.getOS().getVersion(), false);
        }
        //WEIGHT
        AddDefaultLabel("Weight (g)");
        for (Smartphone s: selectedSmartphones) {
            boolean bold = s.getWeight() == selectedSmartphones.stream().min(Comparator.comparingDouble(Smartphone::getWeight)).get().getWeight();
            AddNewLabel(s.getWeight().toString(), bold);
        }
        //RESOLUTION
        AddDefaultLabel("Resolution (px)");
        for (Smartphone s: selectedSmartphones) {
            AddNewLabel(s.getResolutionX() + "x" + s.getResolutionY(), false);
        }
        //DIMENSIONS
        AddDefaultLabel("Dimensions (mm)");
        for (Smartphone s: selectedSmartphones) {
            AddNewLabel(s.getWidth() + "x" + s.getHeight() + "x" + s.getDepth(), false);
        }
        //STORAGE
        AddDefaultLabel("Storage (GB)");
        for (Smartphone s: selectedSmartphones) {
            boolean bold = Objects.equals(s.getStorage(), selectedSmartphones.stream().max((a, b) -> a.getStorage()
                                                                                                   - b.getStorage()).get().getStorage());
            AddNewLabel(s.getStorage().toString(), bold);
        }
        //RAM
        AddDefaultLabel("RAM (GB)");
        for (Smartphone s: selectedSmartphones) {
            boolean bold = Objects.equals(s.getRAM(), selectedSmartphones.stream().max((a, b) -> a.getRAM()
                                                                                               - b.getRAM()).get().getRAM());
            AddNewLabel(s.getRAM().toString(), bold);
        }
        //CHIPSET
        AddDefaultLabel("Chipset");
        for (Smartphone s: selectedSmartphones) {
            AddNewLabel(s.getChipset().getName(), false);
        }
        //COLOR
        AddDefaultLabel("Color");
        for (Smartphone s: selectedSmartphones) {
            AddNewLabel(s.getColor(), false);
        }
        //BATTERY
        AddDefaultLabel("Battery (mAh)");
        for (Smartphone s: selectedSmartphones) {
            boolean bold = Objects.equals(s.getBattery(), selectedSmartphones.stream().max((a, b) -> a.getBattery()
                                                                                                   - b.getBattery()).get().getBattery());
            AddNewLabel(s.getBattery().toString(), bold);
        }

        this.setVisible(true);
        ApplicationLogger.log(Level.INFO, "Comparison JFrame created");
    }

    private void AddDefaultLabel(String string){
        JLabel label = new JLabel(string);
        label.setBackground(borderColor);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(cellWidth, cellHeight));
        this.add(label);
    }
    private void AddNewLabel (String string, boolean bold){
        JLabel label = new JLabel(string);
        label.setBackground(backgroundColor);
        label.setOpaque(true);
        label.setHorizontalAlignment(JLabel.CENTER);
        Font f = bold ? new Font("Comic Sans", Font.BOLD, 15) :  new Font("Comic Sans", Font.PLAIN, 15);
        Color c = bold ? borderColor : Color.WHITE;
        label.setForeground(c);
        label.setFont(f);
        label.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        label.setPreferredSize(new Dimension(cellWidth, cellHeight));
        this.add(label);
    }
}
