package services.converters;

import dao.BrandDAO;
import dao.ChipsetDAO;
import dao.OSDAO;
import entities.Brand;
import entities.Chipset;
import entities.OS;
import entities.Smartphone;
import services.IConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class SmartphoneConverter implements IConverter {
    public List<Smartphone> ConvertAll(String folderDirectory){
        File folder = new File(folderDirectory);
        File[] smartphonePaths = folder.listFiles();
        List<Smartphone> smartphoneList = new ArrayList<>();

        assert smartphonePaths != null;
        for (File path: smartphonePaths) {
            Smartphone tmpSmartphone;
            try {
                tmpSmartphone = Convert(path.toString());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error happened while trying to convert file to Smartphone (" + path.toString() + ")");
                continue;
            }

            if(tmpSmartphone == null) { continue; }
            smartphoneList.add(tmpSmartphone);
        }
        return smartphoneList;
    }

    public Smartphone Convert(String txtDirectory) throws FileNotFoundException {
        File dir = new File(txtDirectory);
        Scanner scanner = new Scanner(dir);
        String name = null, color = null;
        Double weight = null;
        Double depth = null, width = null, height = null;
        Integer resolutionX = null, resolutionY = null;
        Integer storage = null, RAM = null, battery = null;
        LocalDate releaseDate = null;
        OS os = null;
        Brand brand = null;
        Chipset chipset = null;
        while(scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            String header = line.substring(0,line.indexOf(':'));
            String content = line.substring(line.indexOf(':') + 1);

            if(header.equals("") || content.equals("")) { continue; }

            try {
                switch (header.toLowerCase(Locale.ROOT)) {
                    case "name" -> name = content;
                    case "brand" -> {
                        BrandDAO dao = new BrandDAO();
                        Brand tmp = null;
                        try {
                            tmp = dao.getBrandList().stream().filter(a -> a.getName().replace(" ", "")
                                     .equals(content.replace(" ", ""))).findFirst().get();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        brand = tmp;
                    }
                    case "releasedate" -> releaseDate = LocalDate.parse(content);
                    case "os" -> {
                        OSDAO dao = new OSDAO();
                        OS tmp = null;
                        try {
                            tmp =  dao.getOSList().stream().filter(a -> a.getName().concat(" " + a.getVersion()).replace(" ", "")
                                      .equals(content.replace(" ", ""))).findFirst().get();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        os = tmp;
                    }
                    case "weight" -> weight = Double.parseDouble(content);
                    case "resolution" -> {
                        resolutionX = Integer.parseInt(content.substring(0, content.indexOf('x')));
                        resolutionY = Integer.parseInt(content.substring(content.indexOf('x') + 1));
                    }
                    case "dimensions" -> {
                        height = Double.parseDouble(content.substring(0, content.indexOf('x')));
                        width = Double.parseDouble(content.substring(content.indexOf('x') + 1, content.lastIndexOf('x')));
                        depth = Double.parseDouble(content.substring(content.lastIndexOf('x') + 1));
                    }
                    case "storage" -> storage = Integer.parseInt(content);
                    case "ram" -> RAM = Integer.parseInt(content);
                    case "chipset" -> {
                        ChipsetDAO dao = new ChipsetDAO();
                        Chipset tmp = null;
                        try {
                            tmp = dao.getChipsetList().stream().filter(c -> c.getName().replace(" ", "")
                                     .equals(content.replace(" ", ""))).findFirst().get();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        chipset = tmp;
                    }
                    case "color" -> color = content;
                    case "battery" -> battery = Integer.parseInt(content);
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Error happened while trying to parse line for Smartphone (" + line + ")");
            }
        }
        scanner.close();
        return new Smartphone(name, brand, releaseDate, os, weight, resolutionX, resolutionY, depth, width, height, storage, RAM, chipset, color, battery);
    }
}
