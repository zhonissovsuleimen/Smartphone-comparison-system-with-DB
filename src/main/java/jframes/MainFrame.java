package jframes;

import dao.BrandDAO;
import dao.ChipsetDAO;
import dao.OSDAO;
import dao.SmartphoneDAO;
import entities.Brand;
import entities.Chipset;
import entities.OS;
import entities.Smartphone;
import services.ApplicationLogger;
import services.SessionFactory;
import services.converters.BrandConverter;
import services.converters.ChipsetConverter;
import services.converters.OSConverter;
import services.converters.SmartphoneConverter;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.*;

public class MainFrame extends JFrame implements ActionListener{
    boolean isConnected = false;
    Integer selectedNum = 0;
    String originalTitle;
    List<JCheckBox> checkboxes = new ArrayList<>();
    List<Smartphone> smartphones = new ArrayList<>();

    //JObjects
    JButton compareButton, checkDBButton, refillDBButton;
    JLabel isDBConnected = new JLabel("not connected");
    JPanel topPanel, topLabelPanel, topButtonPanel, bottomPanel, smartphonePanel;

    //Colors
    Color topPanelColor = new Color(86,99,247);
    Color backgroundColor = new Color(44, 47, 51);

    //CONSTRUCTORS
    public MainFrame(){
        originalTitle = "Smartphone Comparison System";
        this.setTitle(originalTitle);
        this.setResizable(true);
        this.setSize(500,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);

        //TOP PANEL
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(topPanelColor);

        topLabelPanel = new JPanel();
        topLabelPanel.setBackground(topPanelColor);
        JLabel DBConnectionLabel = new JLabel("Database status: ");
        DBConnectionLabel.setForeground(Color.WHITE);
        DBConnectionLabel.setHorizontalAlignment(JLabel.LEFT);
        isDBConnected.setForeground(Color.BLACK);
        isDBConnected.setHorizontalAlignment(JLabel.RIGHT);
        topLabelPanel.add(DBConnectionLabel);
        topLabelPanel.add(isDBConnected);

        topButtonPanel = new JPanel();
        topButtonPanel.setBackground(topPanelColor);
        checkDBButton = new JButton("Check connection");
        checkDBButton.setFocusable(false);
        checkDBButton.setHorizontalAlignment(JLabel.RIGHT);
        checkDBButton.setSize(new Dimension(100,100));
        checkDBButton.addActionListener(this);
        refillDBButton = new JButton("Refill database");
        refillDBButton.setFocusable(false);
        refillDBButton.setHorizontalAlignment(JLabel.RIGHT);
        refillDBButton.setSize(new Dimension(100,100));
        refillDBButton.addActionListener(this);
        topButtonPanel.add(checkDBButton);
        topButtonPanel.add(refillDBButton);

        topPanel.add(topLabelPanel, BorderLayout.WEST);
        topPanel.add(topButtonPanel,BorderLayout.EAST);

        //BOTTOM PANEL
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.GRAY);
        compareButton = new JButton("Compare");
        compareButton.setFocusable(false);
        compareButton.setHorizontalAlignment(JLabel.RIGHT);
        compareButton.addActionListener(this);
        bottomPanel.add(compareButton);

        //SMARTPHONES PANEL
        smartphonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 10));
        smartphonePanel.setBackground(backgroundColor);

        //disabling buttons
        refillDBButton.setEnabled(false);
        compareButton.setEnabled(false);

        //adding everything to the main JFrame
        this.add(smartphonePanel);
        this.add(topPanel,BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public MainFrame(boolean debug){
        this();
        if(debug){ PanelDebugMode(); }
    }

    //PUBLIC METHODS
    public void UpdateSmartphonePanel(){
        ApplicationLogger.log(Level.INFO, "Updating Smartphone Panel");
        checkboxes = new ArrayList<>();
        SmartphoneDAO dao = new SmartphoneDAO();
        smartphones = dao.getSmartphoneList();
        smartphonePanel.removeAll();
        for (Smartphone s : smartphones) {
            JPanel singleSmartphonePanel = new JPanel(new BorderLayout());
            JLabel singleSmartphoneLabel = new JLabel(s.getName());
            singleSmartphoneLabel.setForeground(Color.WHITE);
            singleSmartphoneLabel.setBackground(backgroundColor);
            singleSmartphoneLabel.setOpaque(true);
            singleSmartphoneLabel.setFont(new Font("Comic Sans", Font.BOLD, 20));
            JCheckBox singleSmartphoneCheck = new JCheckBox();
            singleSmartphoneCheck.addItemListener(e -> {
                Runnable checkNum =
                        () -> {
                            compareButton.setEnabled(selectedNum > 1);
                        };
                if(singleSmartphoneCheck.isSelected()){
                    selectedNum++;
                }
                else{
                    selectedNum--;
                }
                new Thread(checkNum).start();
            });
            singleSmartphoneCheck.setBackground(backgroundColor);
            singleSmartphoneCheck.setFocusable(false);
            singleSmartphoneLabel.setFocusable(false);

            singleSmartphonePanel.add(singleSmartphoneCheck,BorderLayout.EAST);
            singleSmartphonePanel.add(singleSmartphoneLabel);
            smartphonePanel.add(singleSmartphonePanel);
            checkboxes.add(singleSmartphoneCheck);
        }
        this.add(smartphonePanel);
    }

    //PRIVATE METHODS
    private void PanelDebugMode() {
        ApplicationLogger.log(Level.INFO, "Activating \"Panel Debug\" mode");
        int thickness = 3;
        topPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, thickness));
        topLabelPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, thickness));
        topButtonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, thickness));
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, thickness));
        smartphonePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, thickness));
    }

    private void UpdateButtons(boolean b){
        ApplicationLogger.log(Level.INFO, "Setting compareButton and refillButton enabled to " + b);
        compareButton.setEnabled(b);
        refillDBButton.setEnabled(b);
    }

    private void ResetTitle(){
        this.setTitle(originalTitle);
    }

    //BUTTON EVENT METHODS
    private void CheckDBConnection() {
        ApplicationLogger.log(Level.INFO, "checkDBButton pressed");
        Runnable checkDBConnection =
                () -> {
                    try {
                        isConnected = SessionFactory.getSession().isConnected();
                    }catch (Exception ex){
                        isDBConnected.setText("Error occurred, please restart the application");
                        ApplicationLogger.log(Level.WARNING, ex.toString());
                    }
                };
        Thread check = new Thread(checkDBConnection);
        check.start();
        WhileThreadAliveChangeStatus(check, "connecting to DB");
        DisableButtonAfterThreadFinish(check, checkDBButton);
        EnableButtonAfterThreadFinish(check, refillDBButton);
    }

    private void RefillDatabase(){
        ApplicationLogger.log(Level.INFO,"refillDBButton pressed");
        String directory = System.getProperty("user.dir") + "\\txtFiles\\";
        Runnable refillOS =
                () -> {
                    try{
                        OSDAO osdao = new OSDAO();

                        var osConverter = new OSConverter();
                        List<OS> osList = osConverter.ConvertAll(directory + "os\\");
                        for (OS os:osList) { osdao.addOS(os); }
                    }catch (Exception ex){
                        ApplicationLogger.log(Level.WARNING,ex.toString());
                    }
                };
        Runnable refillBrands =
                () -> {
                    try{
                        BrandDAO branddao = new BrandDAO();

                        var brandConverter = new BrandConverter();
                        List<Brand> brandList = brandConverter.ConvertAll(directory + "brands\\");
                        for (Brand brand:brandList) { branddao.addBrand(brand); }
                    }catch (Exception ex){
                        ApplicationLogger.log(Level.WARNING,ex.toString());
                    }
                };
        Runnable refillChipests =
                () -> {
                    try{
                        ChipsetDAO chipdao = new ChipsetDAO();

                        var chipConverter = new ChipsetConverter();
                        List<Chipset> chipList = chipConverter.ConvertAll(directory + "chipsets\\");
                        for (Chipset chip:chipList) chipdao.addChipset(chip);
                    }catch (Exception ex){
                        ApplicationLogger.log(Level.WARNING,ex.toString());
                    }
                };
        Thread OS = new Thread(refillOS);
        Thread Brands = new Thread(refillBrands);
        Thread Chipests = new Thread(refillChipests);
        OS.start();
        Brands.start();
        Chipests.start();
        Runnable refillSmartphones =
                () -> {
                    try{
                        SmartphoneDAO spdao = new SmartphoneDAO();
                        OS.join();
                        Brands.join();
                        Chipests.join();
                        var spConverter = new SmartphoneConverter();
                        List<Smartphone> spList = spConverter.ConvertAll(directory + "smartphones\\");
                        for (Smartphone sp:spList) { spdao.addSmartphone(sp); }
                    }catch (Exception ex){
                        isDBConnected.setForeground(Color.BLACK);
                        isDBConnected.setText("Error occurred, please restart the application");
                        ApplicationLogger.log(Level.WARNING, ex.toString());
                    }
                };
        Thread smartphones = new Thread(refillSmartphones);
        smartphones.start();
        refillDBButton.setEnabled(false);
        WhileThreadAliveChangeStatus(smartphones, "refilling the DB");
        DisableButtonAfterThreadFinish(smartphones, refillDBButton);
    }

    private void CompareSmartphones(){
        ApplicationLogger.log(Level.INFO, "compareButton pressed");
        List<Smartphone> selectedSmartphones = new ArrayList<>();
        for (int i = 0; i < checkboxes.size(); i++){
            if(checkboxes.get(i).isSelected()){
                selectedSmartphones.add(smartphones.get(i));
            }
        }
        new ComparisonFrame(selectedSmartphones);
    }

    private void WhileThreadAliveChangeStatus(Thread mainThread, String text){
        Runnable secondThread =
                () -> {
                    try {
                        while(mainThread.isAlive()){
                            isDBConnected.setText(text + ".");
                            Thread.sleep(333);
                            isDBConnected.setText(text + "..");
                            Thread.sleep(333);
                            isDBConnected.setText(text + "...");
                            Thread.sleep(333);
                        }
                        if(isConnected){
                            isDBConnected.setForeground(Color.GREEN);
                            isDBConnected.setText("connected");
                        }
                        else{
                            isDBConnected.setForeground(Color.BLACK);
                            isDBConnected.setText("not connected");
                        }
                    }catch (Exception ex){
                        ApplicationLogger.log(Level.WARNING,"Thread was interrupted.");
                        ApplicationLogger.log(Level.WARNING, ex.getMessage());
                    }
                };
        new Thread(secondThread).start();
    }

    private void EnableButtonAfterThreadFinish(Thread thread, JButton button){
        JButton b = button == null ? new JButton("temp") : button;
        Runnable buttonWait =
                () -> {
                    try{
                        thread.join();
                        b.setEnabled(true);
                    }catch (Exception ex){
                        ApplicationLogger.log(Level.WARNING,"Thread was interrupted.");
                        ApplicationLogger.log(Level.WARNING,ex.getMessage());
                    }
                };
        new Thread(buttonWait).start();
    }

    private void DisableButtonUntilThreadFinish(Thread thread, JButton button){
        JButton b = button == null ? new JButton("temp") : button;
        Runnable buttonWait =
                () -> {
                    try{
                        b.setEnabled(false);
                        thread.join();
                        b.setEnabled(true);
                    }catch (Exception ex){
                        ApplicationLogger.log(Level.WARNING,"Thread was interrupted.");
                        ApplicationLogger.log(Level.WARNING,ex.getMessage());
                    }
                };
        new Thread(buttonWait).start();
    }

    private void DisableButtonAfterThreadFinish(Thread thread, JButton button){
        JButton b = button == null ? new JButton("temp") : button;
        Runnable buttonWait =
                () -> {
                    try{
                        thread.join();
                        b.setEnabled(false);
                    }catch (Exception ex){
                        ApplicationLogger.log(Level.WARNING,"Thread was interrupted.");
                        ApplicationLogger.log(Level.WARNING,ex.getMessage());
                    }
                };
        new Thread(buttonWait).start();
    }



    //OVERRIDE METHODS
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == compareButton){
            CompareSmartphones();
        }
        if(e.getSource() == checkDBButton){
            CheckDBConnection();
            UpdateSmartphonePanel();
        }
        if(e.getSource() == refillDBButton){
            RefillDatabase();
            UpdateSmartphonePanel();
        }
    }
}
