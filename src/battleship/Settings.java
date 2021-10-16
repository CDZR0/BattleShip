//Vilmos 

package battleship;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Scanner;

public class Settings {
    public static String ip;
    public static String port;   
    
    public static void Init(){
        try{
            File file = new File("settings.cfg");
            Scanner reader = new Scanner(file);
            Field[] fields = Settings.class.getFields();
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                for (int i = 0; i < fields.length; ++i){
                    if (data.contains(fields[i].getName())){
                        try{
                            String value = data.substring(data.indexOf(" ")+1);
                            fields[i].set(Settings.class.getClass(), value);
                        }
                        catch(IllegalAccessException ex){
                            System.out.println(ex.getMessage());
                        }
                    }
                }
            }
        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
            ip = "127.0.0.1";
            port = "65420";
        }
    }
}