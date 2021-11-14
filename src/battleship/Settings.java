package battleship;

import battleship.Networking.ServerAddress;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Settings {
    private static final Settings settings = new Settings();
    
    public static String ip;   
    public static String port;
    private static final List<ServerAddress> serverList = new ArrayList<>();
    
    private Settings() {}
    
    public static Settings getInstance()
    {
        readServersFromFile();
        ReadFile();
        Settings.WriteFile();

        return settings;
    }
    
    private static void ReadFile()
    {
        try
        {
            File file = new File("settings.cfg");
            Scanner reader = new Scanner(file);
            Field[] fields = Settings.class.getFields();
            while (reader.hasNextLine()) 
            {
                String data = reader.nextLine();
                for (int i = 0; i < fields.length; ++i)
                {
                    if (data.contains(fields[i].getName()))
                    {
                        try
                        {
                            String value = data.substring(data.indexOf(" ")+1);
                            fields[i].set(Settings.class.getClass(), value);
                        }
                        catch(IllegalAccessException ex)
                        {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
            }
        }
        catch(FileNotFoundException | IllegalArgumentException | SecurityException  ex)
        {
            System.out.println(ex.getMessage());
            ip = "0.0.0.0";
            port = "25564";           
            WriteFile();
        }
    }
    
    public static void WriteFile()
    {
        try
        {
            File file = new File("settings.cfg");
            if(!file.createNewFile())
            {
                file.delete();
                file.createNewFile();
            }
            
            try (FileWriter fw = new FileWriter("settings.cfg")) 
            {
                Field[] fields = Settings.class.getFields();
                for (Field field : fields) 
                {
                    fw.write(field.getName() + " " + field.get(Settings.class.getClass()) + System.lineSeparator());
                }
            }
            WriteSavedServersToFile();
        }
        catch(IOException | IllegalAccessException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    private static void WriteSavedServersToFile()
    {
        try (FileWriter fw = new FileWriter("settings.cfg", true)) 
        {
            fw.write(System.lineSeparator() + "Saved_Servers {" + System.lineSeparator());
            for (ServerAddress serverAddress : serverList) 
            {
                fw.write("\t" + serverAddress + System.lineSeparator());
            }
            fw.write("}" + System.lineSeparator());
        } 
        catch (IOException ex) 
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public static String getIP() 
    {
        return ip;
    }

    public static int getPort() 
    {
        return Integer.parseInt(port);
    }
    
    private static void readServersFromFile()
    {
        try 
        {
            File file = new File("settings.cfg");
            Scanner reader = new Scanner(file);
            
            while (reader.hasNextLine()) 
            {
                String data = reader.nextLine();
                if (data.contains("Saved_Servers {"))
                {
                    while (true)
                    {
                        String line = reader.nextLine();
                        if (line.contains("}")) 
                            break;
                        String strArr[] = line.replaceAll("\\s+", "").split(":");
                        ServerAddress sAddress = new ServerAddress(strArr[0], strArr[1], Integer.parseInt(strArr[2]));
                        serverList.add(sAddress);
                    }
                }
            }
        } 
        catch (FileNotFoundException ex) 
        {
            ex.getMessage();
        }
    }
    
    public static List<ServerAddress> getServers()
    {
        return serverList;
    }
    
    public static void addServer(ServerAddress sAddress)
    {
        for (ServerAddress serverAddress : serverList) 
        {
            if (sAddress.getName().equals(serverAddress.getName()))
            {
                throw new RuntimeException("SZERVER NÉV MÁR LÉTEZIK");
            }
        }
        serverList.add(sAddress);
        WriteFile();
    }
    
    public static void editServer(String name, ServerAddress newAddress)
    {
        for (ServerAddress serverAddress : serverList) 
        {
            if (name.equals(serverAddress.getName()))
            {
                serverAddress.setName(newAddress.getName());
                serverAddress.setIP(newAddress.getIP());
                serverAddress.setPort(newAddress.getPort());
                WriteFile();
            }
        }
    }
    
    public static void deleteServer(String name)
    {
        for (ServerAddress serverAddress : serverList) 
        {
            if (serverAddress.getName().equals(name))
            {
                serverList.remove(serverAddress);
                WriteFile();
            }
        }
    }
}