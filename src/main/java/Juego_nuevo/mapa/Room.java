package Juego_nuevo.mapa;

import java.util.ArrayList;

public class Room extends MapTile{

    private boolean unlocked = false;

    ArrayList<Room> connections;

    static int totalRooms = 0;


    public Room() {
        super("?", totalRooms);
        totalRooms++;
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

    static public int getTotalRooms() {
        return totalRooms;
    }
}

