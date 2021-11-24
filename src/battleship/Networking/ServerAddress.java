package battleship.Networking;


public class ServerAddress {
    String name;
    String ip;
    int port;

    public ServerAddress(String name, String ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
    }
    
    @Override
    public String toString()
    {
        return name + "$" + ip + "$" + port;
    }

    public String getName() {
        return name;
    }

    public String getIP() {
        return ip;
    }

    public int getPort() {
        return port;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
