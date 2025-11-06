package Juego_nuevo.mapa;

/*
 * @author Marcos
 */

import Juego_nuevo.Personaje;

import java.util.Random;

public class Map {

    // tamaño del mapa. Siempre es cuadrado
    private final int mapSize = 7;

    // matriz
    private final MapTile[][] map = new MapTile[mapSize][mapSize];

    // número final de habitaciones que tendrá el Juego_nuevo.mapa
    private int finalRoomN;

    // seed para generar las cosas de forma chula
    private int seed;


    /*
     * Esta función solo se usa en el constructor.
     * Rellena la matriz con MapTiles.
     */
    private void fillArray() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new MapTile();
            }
        }
    }


    /*
     * Constructores
     * Uno para generar el mapa con una seed aleatoria y otro para darle una seed predefinida
     */
    public Map() {
        fillArray();
        seed = (int)(Math.random()*99999999);
    }

    public Map(int seed) { //creates an array of MapTiles
        fillArray();
        this.seed = seed;
    }

    //######################## ---- Generar mapa ---- ########################\\

    /*
     * Solo se usa al principio de la función de generateLayout
     * Comienza la creación de las habitaciones.
     * Elige una posición random entre 4 y pone la primera habitación (habitación 0) en esa posición.
     * Las 4 posiciones son los bordes del mapa (esto es solo para que el mapa se genere de forma un poco más interesante)
     */
    private void layoutStart() { //chooses a random number between 4 and sets the room 0 at a specific location
        int halfSize = mapSize/2;

        int positions[][] = {{0, halfSize}, {halfSize, mapSize-1}, {mapSize-1, halfSize}, {halfSize, 0}};

        int startPosition = (seed%positions.length);

        map[positions[startPosition][0]]
                [positions[startPosition][1]] = new Room();
    }


    /*
     * Una función para coger las coordenadas de una habitación.
     * Se le pasa la id de la habitación.
     */
    private int[] getRoomCoords(int generatedOrder) {
        int[] coords = new int[]{0, 0};

        for (int i = 0; i<mapSize; i++) {
            for (int j=0;j<mapSize;j++) {
                if (map[i][j] instanceof Room && map[i][j].getGeneratedOrder() == generatedOrder) {
                    coords[0] = i;
                    coords[1] = j;
                }
            }
        }
        return coords;
    }

    public int moveRoomN(int currentLocation, int direction) {
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int currentLocationCoord[] = getRoomCoords(currentLocation);

        int newRow = currentLocationCoord[0] + directions[direction][0];
        int newCol = currentLocationCoord[1] + directions[direction][1];

        return map[newRow][newCol].getGeneratedOrder();
    }


    /*
     * Función principal para generar las habitaciones.
     */
    public void generateLayout() {
        Random rng = new Random(seed);

        int maxRooms = 10;
        int minRooms = 7;
        finalRoomN = (rng.nextInt()%(maxRooms+1-minRooms))+ minRooms;

        layoutStart();

        int lastRoomGenerated = 0;
        int[] lastGeneratedCoords = getRoomCoords(lastRoomGenerated);

        boolean generationEnded = false;
        boolean roomGenerated = false;
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
                int random = rng.nextInt(directions.length);

                //adds a random set to the coords to get the next coords
                int newRow = lastGeneratedCoords[0] + directions[random][0];
                int newCol = lastGeneratedCoords[1] + directions[random][1];

                // Check límit of array
                if (newRow >= 0 && newRow < map.length &&
                        newCol >= 0 && newCol < map[newRow].length &&
                        !(map[newRow][newCol] instanceof Room)) {

                    map[newRow][newCol] = new Room();
                    roomGenerated = true;
                }

                if (tries>100) { // si no se puede mover tira para atrás
                    lastRoomGenerated--;
                    lastGeneratedCoords = getRoomCoords(lastRoomGenerated);
                    tries=0;
                }

            } while (!roomGenerated);

            if (Room.getTotalRooms() == finalRoomN) {
                generationEnded = true;
            }

            roomGenerated = false; // set to false again to repeat loop
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


    public int getSeed() {
        return seed;
    }

    public String toString(Personaje player) {
        System.out.println("\n\nSeed: "+seed+"\nRooms: "+finalRoomN);
        StringBuilder outputMap = new StringBuilder();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {

                if (player.getUbicacion() == map[i][j].getGeneratedOrder()){
                    outputMap.append("\033[0;33m").append(map[i][j]).append("\033[0;0m");
                } else {
                    outputMap.append(map[i][j]);
                }

                //IF la posicion de jugador == i & j se cambia color  a rojo
            }
            outputMap.append('\n');
        }

        return outputMap.toString();
    }
}
