package battleship.Networking;

import battleship.DataPackage.CellData;
import battleship.DataPackage.Data;
import battleship.DataPackage.DataConverter;
import battleship.DataPackage.GameEndedData;
import battleship.DataPackage.ShotData;
import java.net.*;
import java.io.*;
import java.util.List;
import java.util.Vector;
import battleship.Events.ClientEvent;
import java.util.ArrayList;

public class Client implements Runnable {

    private final List<String> messageQueue = new Vector<>();
    private String ip;
    private int port;
    private boolean close = false;
    private List<ClientEvent> listeners = new ArrayList<>();
    private boolean timedout = false;
    public Integer ID;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    
    public boolean isTimeout()
    {
        return timedout;
    }

    public void sendMessage(String message) {
        messageQueue.add(message);
    }

    public void sendMessage(Data data) {
        String message = DataConverter.encode(data);
        messageQueue.add(message);
    }

    public void close() {
        close = true;
    }

    public void addClientEventListener(ClientEvent cEvent) {
        listeners.add(cEvent);
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(ip, port);

            BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            Thread thread = new Thread(() -> {
                try {
                    while (!close) {
                        String inMsg = bfr.readLine();
                        if (inMsg.equals("0") || inMsg.equals("1")) {
                            ID = Integer.parseInt(inMsg);
                            continue;
                        }
                        //#####################################################
                        //Innentől kéne eldönteni milyen típusú üzenetet kapott
                        //és hogy utána milye nevent történjen.
                        //A lenti rész a ChatData lesz.
                        //#####################################################

                        Data data = DataConverter.decode(inMsg);

                        switch (data.getClass().getSimpleName()) {
                            case "ChatData":
                                System.out.println("Create event: ChatData");
                                for (ClientEvent listener : listeners) {
                                    listener.onMessageReceived(inMsg);
                                }
                                break;
                            case "PlaceShipsData":
                                System.out.println("SERVER EVENT RECEIVED IN CLIENT: PlaceShipsData");
                                break;
                            case "ConnectionData":
                                System.out.println("Create event: ConnectionData");
                                break;
                            case "ShotData":
                                System.out.println("Create event: ShotData");
                                if (((ShotData) data).getRecipientID() == ID) {
                                    for (ClientEvent listener : listeners) {
                                        listener.onEnemyHitMe(((ShotData) data).getI(), ((ShotData) data).getJ());
                                    }
                                }
                                break;
                            case "CellData":
                                System.out.println("Create event: CellData");
                                for (ClientEvent listener : listeners) {
                                    listener.onMyHit(((CellData) data).getI(), ((CellData) data).getJ(), ((CellData) data).getStatus());
                                }
                                break;
                            case "TurnData":
                                System.out.println("Create event: TurnData");
                                for (ClientEvent listener : listeners) {
                                    listener.onYourTurn();
                                }
                                break;
                            case "GameEndedData":
                                System.out.println("Create event: GameEndedData");
                                for (ClientEvent listener : listeners) {
                                    listener.onGameEnded(((GameEndedData) data).getStatus());
                                }
                                break;
                            default:
                                System.out.println("########## ISMERETLEN OSZTÁLY #########");
                                System.out.println("Nincs implementálva a Client-ben az alábbi osztály: " + data.getClass().getSimpleName());
                                break;
                        }

                        System.out.println(inMsg);
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            });
            thread.start();

            while (!close) {
                while (!messageQueue.isEmpty()) {
                    String message = messageQueue.get(0);
                    messageQueue.remove(0);
                    bfw.write(message);
                    bfw.newLine();
                    bfw.flush();
                }
            }
            try {
                socket.close();
                bfr.close();
                bfw.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            timedout = true;
        }
    }
}
