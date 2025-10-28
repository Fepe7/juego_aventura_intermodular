package mapa;

import java.util.ArrayList;

public class Room extends MapTile{

    private boolean unlocked = false;

    ArrayList<Room> connections;


    public Room(int generatedOrder) {
        super("?", generatedOrder);
    }


    @Override
    public String toString() {
        return "["+this.getGeneratedOrder()+"]";
    }

//    @Override
//    public String toString() {
//        return "["+this.getTileSymbol()+"]";
//    }

    public ArrayList<Room> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<Room> connections) {
        this.connections = connections;
    }
}

