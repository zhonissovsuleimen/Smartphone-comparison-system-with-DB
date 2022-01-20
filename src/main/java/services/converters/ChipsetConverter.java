package services.converters;

import entities.Chipset;
import services.ApplicationLogger;
import services.IConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChipsetConverter implements IConverter {
    public List<Chipset> ConvertAll(String folderDirectory){
        File folder = new File(folderDirectory);
        File[] chipsetPaths = folder.listFiles();
        List<Chipset> chipsetList = new ArrayList<>();

        assert chipsetPaths != null;
        for (File path: chipsetPaths) {
            Chipset tmpChipset;
            try {
                tmpChipset = Convert(path.toString());
            } catch (Exception e) {
                ApplicationLogger.log(Level.WARNING, "Error happened while trying to convert file to Chipset (" + path.toString() + ")");
                ApplicationLogger.log(Level.WARNING,e.getMessage());
                continue;
            }

            if(tmpChipset == null) { continue; }
            chipsetList.add(tmpChipset);
        }
        return chipsetList;
    }

    public Chipset Convert(String txtDirectory) throws FileNotFoundException {
        File dir = new File(txtDirectory);
        Scanner scanner = new Scanner(dir);
        String name = null;

        while(scanner.hasNext()){
            String line = scanner.nextLine();
            String header = line.substring(0, line.indexOf(':'));
            String content = line.substring(line.indexOf(':') + 1);

            if(header.equals("") || content.equals("")) { continue; }
            switch (header){
                case "name" -> name = content;
            }
        }
        scanner.close();
        return new Chipset(name);
    }
}
