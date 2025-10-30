package Juego_nuevo.mapa;

import java.util.ArrayList;

/*
 * @author Marcos
 */

public class MapDeprecated {
    // matriz
    private final ArrayList<ArrayList<MapTile>> map = new ArrayList<>();

    // tamaño del mapa. Siempre es cuadrado
    private final int mapSize = 7;

    // número final de habitaciones que tendrá el mapa
    private int finalRoomN;

    // seed para generar las cosas de forma chula
    private final int seed;


    /*
     * Esta función solo se usa en el constructor.
     * Rellena la matriz con MapTiles.
     */
    private void fillArray() {
        for (int i = 0; i< mapSize; i++){
            ArrayList<MapTile> file = new ArrayList<>();
            for (int j=0;j<mapSize;j++){
                file.add(new MapTile());
            }
            map.add(file);
        }
    }


    /*
     * Constructores
     * Uno para generar el mapa con una seed aleatoria y otro para darle una seed predefinida
     */
    public MapDeprecated() {
        fillArray();
        seed = (int)(Math.random()*99999999);
    }
    
    public MapDeprecated(int seed) { //creates an array of MapTiles
        fillArray();
        this.seed = seed;
    }


    /*
     * Solo se usa al principio de la función de generateLayout
     * Comienza la creación de las habitaciones.
     * Elige una posición random entre 4 y pone la primera habitación (habitación 0) en esa posición.
     * Las 4 posiciones son los bordes del mapa (esto es solo para que el mapa se genere de forma un poco más interesante)
     */
    private void layoutStart() { //chooses a random number between 4 and sets the room 0 at a specific location
        int halfSize = mapSize/2;

        int positions[][] = {{0, halfSize}, {halfSize, mapSize-1}, {mapSize-1, halfSize}, {halfSize, 0}};

        int startPosition = (int) (Math.random()*4);

        map.get(positions[startPosition][0])
                .set(positions[startPosition][1], new Room());
    }


    /*
     * Una función para coger las coordenadas de una habitación.
     * Se le pasa la id de la habitación.
     */
    private int[] getRoomCoords(int generatedOrder) {
        int[] coords = new int[]{0, 0};

        for (int i = 0; i<mapSize; i++) {
            for (int j=0;j<mapSize;j++) {
                if (map.get(i).get(j) instanceof Room && map.get(i).get(j).getGeneratedOrder() == generatedOrder) {
                    coords[0] = i;
                    coords[1] = j;
                }
            }
        }
        return coords;
    }


    /*
     * Función principal para generar las habitaciones.
     */
    public void generateLayout() {
        System.out.println(seed);
        int maxRooms = 10;
        int minRooms = 7;
        finalRoomN = (seed%(maxRooms +1)- minRooms)+ minRooms;

        layoutStart();

        int lastRoomGenerated = 0;
        int[] lastGeneratedCoords = getRoomCoords(lastRoomGenerated);

        boolean generationEnded = false;
        int roomGenerated = 0;
        int tries;


        System.out.println("Starting generation of map...");
        do { // big loop to generate all rooms

//            System.out.println("Generating room "+ totalRooms);
//            System.out.println("room "+totalRooms+". Last room generated: "+ lastGeneratedCoords[0]+", "+lastGeneratedCoords[1]);

            tries=0;
            do { //loop to generate a room
                tries++;
                //this array has the number that added to the current coords give the next coords to generate a room.
                int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

                // Directions: 0 ^, 1 >, 2 v, 3 <
                int random = (int) (Math.random() * directions.length);

                //adds a random set to the coords to get the next coords
                int newRow = lastGeneratedCoords[0] + directions[random][0];
                int newCol = lastGeneratedCoords[1] + directions[random][1];

                // Check límit of array
                if (newRow >= 0 && newRow < map.size() &&
                        newCol >= 0 && newCol < map.get(newRow).size() &&
                        !(map.get(newRow).get(newCol) instanceof Room)) {

                    map.get(newRow).set(newCol, new Room());
                    roomGenerated = 1;
                }

                if (tries>100) {
                    roomGenerated=-1;
                }

            } while (roomGenerated==0);

            if (Room.getTotalRooms() == finalRoomN || roomGenerated==-1) {
                generationEnded = true;
            }

            if (roomGenerated==-1) {
                System.out.println("Generation ended prematurely");
            }

            roomGenerated = 0; // set to false again to repeat loop
//            System.out.println("room generated");

            lastRoomGenerated++;
            lastGeneratedCoords = getRoomCoords(lastRoomGenerated); //update last generated room

//            System.out.println(this);
//            try {
//                Thread.sleep(500); // Espera 1 segundo
//            } catch (InterruptedException e) {
//                e.printStackTrace(); // Captura la excepción si el hilo es interrumpido
//            }


        } while (!generationEnded);


        System.out.println("generation of rooms ended");
    }

    public int getFinalRoomN() {
        return finalRoomN;
    }

    @Override
    public String toString() {
        String outputMap = "";
        for (int i=0;i<mapSize;i++){
            for (int j=0;j<mapSize;j++){
                outputMap = outputMap+map.get(i).get(j);
            }
            outputMap = outputMap+"\n";
        }
        return outputMap;
    }
}
