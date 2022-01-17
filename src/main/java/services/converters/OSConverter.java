package services.converters;

import entities.OS;
import services.ApplicationLogger;
import services.IConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OSConverter implements IConverter {
    public List<OS> ConvertAll(String folderDirectory){
        File folder = new File(folderDirectory);
        File[] osPaths = folder.listFiles();
        List<OS> osList = new ArrayList<>();

        assert osPaths != null;
        for (File path: osPaths) {
            OS tmpOS;
            try {
                tmpOS = Convert(path.toString());
            } catch (Exception e) {
                ApplicationLogger.log(Level.WARNING, "Error happened while trying to convert file to OS (" + path.toString() + ")");
                ApplicationLogger.log(Level.WARNING,e.getMessage());
                continue;
            }

            if(tmpOS == null) { continue; }
            osList.add(tmpOS);
        }
        return osList;
    }

    public OS Convert(String txtDirectory) throws FileNotFoundException {
        File dir = new File(txtDirectory);
        Scanner scanner = new Scanner(dir);
        String name = null, version = null;

        while(scanner.hasNext()){
            String line = scanner.nextLine();
            String header = line.substring(0, line.indexOf(':'));
            String content = line.substring(line.indexOf(':') + 1);

            if(header.equals("") || content.equals("")) { continue; }
            switch (header){
                case "name" -> name = content;
                case "version" -> version = content;
            }
        }
        scanner.close();
        return new OS(name, version);
    }
}
