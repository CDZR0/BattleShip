package battleship.Networking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ServerManager {
    private static final List<ServerAddress> serverList = new ArrayList<>();
    private static ServerManager serverManager = new ServerManager();
    
    private ServerManager() {}
    
    public static ServerManager getInstance()
    {
        readServersFromFile();
        return serverManager;
    }
    
    private static void WriteSavedServersToFile()
    {
        try (FileWriter fw = new FileWriter("servers.dat")) 
        {
            fw.write("Saved_Servers {" + System.lineSeparator());
            for (ServerAddress serverAddress : serverList) 
            {
                fw.write("\t" + serverAddress + System.lineSeparator());
            }
            fw.write("}");
        } 
        catch (IOException ex) 
        {
            System.out.println(ex.getMessage());
        }
    }
    
    private static void readServersFromFile()
    {
        try 
        {
            File file = new File("servers.dat");
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
                        String strArr[] = line.replaceAll("\\s+", "").split("\\$");
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
        WriteSavedServersToFile();
    }
    
    public static void editServer(String name, ServerAddress newAddress)
    {
        if (!name.equals(newAddress.getName()))
        {
            for (ServerAddress serverAddress : serverList) 
            {
                if (newAddress.getName().equals(serverAddress.getName())) 
                {
                    throw new RuntimeException("SZERVER NÉV MÁR LÉTEZIK");
                }
            }
        }
        
        for (ServerAddress serverAddress : serverList) 
        {
            if (name.equals(serverAddress.getName()))
            {
                serverAddress.setName(newAddress.getName());
                serverAddress.setIP(newAddress.getIP());
                serverAddress.setPort(newAddress.getPort());
                WriteSavedServersToFile();
                break;
            }
        }
    }
    
    public static void deleteServer(ServerAddress sAddress)
    {
        for (int i = 0; i < serverList.size(); ++i)
        {
            if (serverList.get(i).getName().equals(sAddress.getName()))
            {
                serverList.remove(serverList.get(i));
                WriteSavedServersToFile();
                break;
            }
        }
    }
}
