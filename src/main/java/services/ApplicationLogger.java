package services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ApplicationLogger {
    private static Logger logger;
    private ApplicationLogger() throws IOException {
        logger = Logger.getLogger("Smartphone Comparison System logger");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date now = new Date();
        FileHandler fileHandler = new FileHandler(System.getProperty("user.dir") + "\\logs\\" + dateFormat.format(now) + ".log");
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }

    private static Logger getLogger(){
        try{
            if(logger == null){
                new ApplicationLogger();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return logger;
    }

    public static void log(Level level, String msg){
        getLogger().log(level, msg);
    }
}
