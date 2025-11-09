package Juego_nuevo.mapa;

/*
 * @author Marcos
 */

import Juego_nuevo.Eventos.Evento;
import Juego_nuevo.Entidades.Personaje;

import java.util.Random;

public class Mapa {

    // tamaño del mapa. Siempre es cuadrado
    private final int mapSize = 7;

    // matriz
    private final MapTile[][] map = new MapTile[mapSize][mapSize];

    // número final de habitaciones que tendrá el Juego_nuevo.mapa
    private int finalRoomN;

    // seed para generar las cosas de forma chula
    private int seed;

    private final Random rng;


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
    public Mapa() {
        fillArray();
        seed = (int) (Math.random() * 99999999);
        rng = new Random(seed);
    }

    public Mapa(int seed) { //creates an array of MapTiles
        fillArray();
        this.seed = seed;
        rng = new Random(seed);
    }

    //######################## ---- Generar mapa ---- ########################\\

    /*
     * Solo se usa al principio de la función de generateLayout
     * Comienza la creación de las habitaciones.
     * Elige una posición random entre 4 y pone la primera habitación (habitación 0) en esa posición.
     * Las 4 posiciones son los bordes del mapa (esto es solo para que el mapa se genere de forma un poco más interesante)
     */
    private void layoutStart() { //chooses a random number between 4 and sets the room 0 at a specific location
        int halfSize = mapSize / 2;

        int positions[][] = {{0, halfSize}, {halfSize, mapSize - 1}, {mapSize - 1, halfSize}, {halfSize, 0}};

        int startPosition = (seed % positions.length);

        map[positions[startPosition][0]]
                [positions[startPosition][1]] = new Room();
    }


    /*
     * Una función para coger las coordenadas de una habitación.
     * Se le pasa la id de la habitación.
     */
    public int[] getRoomCoords(int generatedOrder) {
        int[] coords = new int[]{0, 0};

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
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

        int maxRooms = 10;
        int minRooms = 7;
        finalRoomN = (rng.nextInt() % (maxRooms + 1 - minRooms)) + minRooms;

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

            tries = 0;
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

                if (tries > 100) { // si no se puede mover tira para atrás
                    lastRoomGenerated--;
                    lastGeneratedCoords = getRoomCoords(lastRoomGenerated);
                    tries = 0;
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

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_RED = "\u001B[31m";

        StringBuilder outputMap = new StringBuilder();

        outputMap.append("Seed: ").append(seed)
                .append("\nRooms: ").append(finalRoomN)
                .append("\n\n");


        //Esto lo he cambiado para que se aplique a la posicin de la matriz
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int[] playerPos = player.getPosicion();
                if (playerPos != null && playerPos[0] == i && playerPos[1] == j) {
                    outputMap.append(ANSI_RED)
                            .append(map[i][j])
                            .append(ANSI_RESET);
                } else {
                    outputMap.append(map[i][j]);
                }
            }
            outputMap.append('\n');
            //IF la posicion de jugador == i & j se cambia color  a rojo


        }

        return outputMap.toString();
    }


    //Coge los eventso de cada sala y los devuelve en una matriz de eventos exactamente igual que el mapa
    public Evento[][] extraerEventos() {
        //Crea matriz con el tamaño del mapa
        Evento[][] eventos = new Evento[mapSize][mapSize];

        //Rellena la matriz con los eventos de cada sala
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                //Asigna el evento de la sala a la matriz de eventos
                if (map[i][j] instanceof Room) {
                    eventos[i][j] = ((Room) map[i][j]).getEvento();
                } else {
                    eventos[i][j] = null;
                }
            }
        }
        return eventos;
    }

    //Calcula la nueva posicion del personaje, devuelve la posicion de la matriz del mapaa
    public int[] calcularNuevaPosicion(int[] posicionActual, int direccion) {
        int[][] movimientos = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        return new int[]{
                posicionActual[0] + movimientos[direccion][0],
                posicionActual[1] + movimientos[direccion][1]
        };
    }


    //Restaura los eventos en cada sala a partir de una matriz de eventos
    public void restaurarEventos(Evento[][] eventos) {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                //Verifica si es una Room antes de hacer el cast
                if (map[i][j] instanceof Room) {
                    ((Room) map[i][j]).setEvento(eventos[i][j]);
                }
            }
        }
    }


    //Verifica si el movimiento que se va a hhacer es valido o no
    public boolean esMovimientoValido(int fila, int columna) {
    return fila >= 0 && fila < mapSize && columna >= 0 && columna < mapSize &&
           map[fila][columna] instanceof Room;
}



    public MapTile[][] getMap() {
        return map;
    }



}
