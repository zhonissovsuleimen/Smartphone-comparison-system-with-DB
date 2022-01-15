package services.converters;

import entities.Brand;
import services.IConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BrandConverter implements IConverter {
    public List<Brand> ConvertAll(String folderDirectory){
        File folder = new File(folderDirectory);
        File[] brandPaths = folder.listFiles();
        List<Brand> brandList = new ArrayList<>();

        assert brandPaths != null;
        for (File path: brandPaths) {
            Brand tmpBrand;
            try {
                tmpBrand = Convert(path.toString());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error happened while trying to convert file to Brand (" + path.toString() + ")");
                continue;
            }

            if(tmpBrand== null) { continue; }
            brandList.add(tmpBrand);
        }
        return brandList;
    }

    public Brand Convert(String txtDirectory) throws FileNotFoundException {
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
        return new Brand(name);
    }
}
