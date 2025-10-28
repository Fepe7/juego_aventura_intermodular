package mapa;

import java.util.ArrayList;

public class Map {

    private ArrayList<ArrayList<MapTile>> map = new ArrayList<>();
    private int maxRooms;


    public Map() { //creates a 5x5 array of MapTiles
        for (int i=0;i<5;i++){
            ArrayList<MapTile> file = new ArrayList<>();
            for (int j=0;j<5;j++){
                file.add(new MapTile());
            }
            map.add(file);
        }
    }

    private Room generateRoom(int totalRooms) { //generates a room and adds it to the HashMap
        return new Room(totalRooms);
    }

    private void layoutStart(int totalRooms) { //chooses a random number between 4 and sets the room 0 at a specific location

        int startPosition = (int) (Math.random()*4);
        switch (startPosition) {
            case 0:
                map.get(0).set(2, generateRoom(totalRooms));
                break;
            case 1:
                map.get(2).set(4, generateRoom(totalRooms));
                break;
            case 2:
                map.get(4).set(2, generateRoom(totalRooms));
                break;
            case 3:
                map.get(2).set(0, generateRoom(totalRooms));
        }
    }

    private int[] getRoomCoords(int generatedOrder) { //given a generated order, searches in the map a room with than generated order en return an int[] with its coordinates
        int[] coords = new int[]{0, 0};

        for (int i=0;i<5;i++) {
            for (int j=0;j<5;j++) {
                if (map.get(i).get(j) instanceof Room && map.get(i).get(j).getGeneratedOrder() == generatedOrder) {
                    coords[0] = i;
                    coords[1] = j;
                }
            }
        }

        return coords;
    }

    public void generateLayout() {
        maxRooms = (int) (Math.random()*4)+7;

        int totalRooms = 0;
        layoutStart(totalRooms);

        int lastRoomGenerated = 0;
        int[] lastGeneratedCoords = getRoomCoords(lastRoomGenerated);

        totalRooms++;

        boolean generationEnded = false;
        boolean roomGenerated = false;


        System.out.println("Starting generation of map...");
        do { // big loop to generate all rooms

//            System.out.println("Generating room "+ totalRooms);
//            System.out.println("room "+totalRooms+". Last room generated: "+ lastGeneratedCoords[0]+", "+lastGeneratedCoords[1]);


                do { // small loop to generate a room
                    int random = (int) (Math.random()*4);

                    switch (random) { //0^ - 1> - 2< - 3v
                        case 0:
                            if (lastGeneratedCoords[0] != 0 && // checks if there's space in the array to generate the room
                                    !(map.get(lastGeneratedCoords[0] - 1).get(lastGeneratedCoords[1]) instanceof Room)) { // checks if the space on the map is already occupied by a room

//                            System.out.println("case 0 trued");

                                map.get(lastGeneratedCoords[0]-1).set(lastGeneratedCoords[1], generateRoom(totalRooms));
                                roomGenerated = true;
                            }
                            break;
                        case 1:
                            if (lastGeneratedCoords[1] != 4 &&
                                    !(map.get(lastGeneratedCoords[0]).get(lastGeneratedCoords[1]+1) instanceof Room)) {

//                            System.out.println("case 1 trued");

                                map.get(lastGeneratedCoords[0]).set(lastGeneratedCoords[1]+1, generateRoom(totalRooms));
                                roomGenerated = true;
                            }
                            break;
                        case 2:
                            if (lastGeneratedCoords[0] != 4 &&
                                    !(map.get(lastGeneratedCoords[0]+1).get(lastGeneratedCoords[1]) instanceof Room)) {

//                            System.out.println("case 2 trued");

                                map.get(lastGeneratedCoords[0]+1).set(lastGeneratedCoords[1], generateRoom(totalRooms));
                                roomGenerated = true;
                            }
                            break;
                        case 3:
                            if (lastGeneratedCoords[1] != 0 &&
                                    !(map.get(lastGeneratedCoords[0]).get(lastGeneratedCoords[1]-1) instanceof Room)) {

//                            System.out.println("case 3 trued");

                                map.get(lastGeneratedCoords[0]).set(lastGeneratedCoords[1]-1, generateRoom(totalRooms));
                                roomGenerated = true;
                            }
                    }
                } while (!roomGenerated);


                roomGenerated = false; // set to false again to repeat loop
//            System.out.println("room generated");


                lastGeneratedCoords = getRoomCoords(lastRoomGenerated); //update last generated room
                lastRoomGenerated++;
                totalRooms++;

            System.out.println(this);

//            try {
//                Thread.sleep(500); // Espera 1 segundo
//            } catch (InterruptedException e) {
//                e.printStackTrace(); // Captura la excepción si el hilo es interrumpido
//            }

            if (totalRooms == maxRooms) {
                generationEnded = true;
            }

        } while (!generationEnded);

        System.out.println("generation of rooms ended");



        //random rooms
//        int totalRooms = 0;
//        maxRooms = (int) (Math.random()*4)+5;
//        for (int i=0;i<maxRooms;i++){
//            map.get((int) (Math.random()*5)).set((int) (Math.random()*5) ,generateRoom(totalRooms));
//            totalRooms++;
//        }
    }



    @Override
    public String toString() {
        String outputMap = "";
        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++){
                outputMap = outputMap+map.get(i).get(j);
            }
            outputMap = outputMap+"\n";
        }
        return outputMap;
    }

    public int getMaxRooms() {
        return maxRooms;
    }
}
