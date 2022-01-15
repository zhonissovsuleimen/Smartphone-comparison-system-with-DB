import dao.BrandDAO;
import dao.ChipsetDAO;
import dao.OSDAO;
import dao.SmartphoneDAO;
import entities.Brand;
import entities.Chipset;
import entities.OS;
import entities.Smartphone;
import services.converters.BrandConverter;
import services.converters.ChipsetConverter;
import services.converters.OSConverter;
import services.converters.SmartphoneConverter;

import java.util.List;

public class Main {
    public static void main(final String[] args) {
        String directory = System.getProperty("user.dir") + "\\txtFiles\\";

        //You must create database beforehand as I couldn't find a way to create it via code for the time I had.
        //Filling the database
        OSDAO osdao = new OSDAO();
        ChipsetDAO chipdao = new ChipsetDAO();
        BrandDAO branddao = new BrandDAO();
        SmartphoneDAO spdao = new SmartphoneDAO();

        var osConverter = new OSConverter();
        List<OS> osList = osConverter.ConvertAll(directory + "os\\");
        for (OS os:osList) { osdao.addOS(os); }

        var chipConverter = new ChipsetConverter();
        List<Chipset> chipList = chipConverter.ConvertAll(directory + "chipsets\\");
        for (Chipset chip:chipList) { chipdao.addChipset(chip); }

        var brandConverter = new BrandConverter();
        List<Brand> brandList = brandConverter.ConvertAll(directory + "brands\\");
        for (Brand brand:brandList) { branddao.addBrand(brand); }

        var spConverter = new SmartphoneConverter();
        List<Smartphone> spList = spConverter.ConvertAll(directory + "smartphones\\");
        for (Smartphone sp:spList) { spdao.addSmartphone(sp); }

        //Running the system
        SCS system = new SCS();
        system.Run();
    }
}
