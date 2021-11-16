package battleship.DataPackage;

public abstract class Data {

    protected int clientID;

    Data(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }

}
