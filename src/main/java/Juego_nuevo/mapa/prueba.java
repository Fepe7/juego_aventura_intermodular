package Juego_nuevo.mapa;

import java.util.Scanner;

public class prueba {
    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Quieres insertar una seed?: (y/N)");
//        String seed = sc.nextLine();
//
//
//        System.out.println(seed);

        int seed = (int)(Math.random()*99999999);

        int maxRooms = 10;
        int minRooms = 7;
        int finalRoomN = (seed%(maxRooms+1-minRooms))+ minRooms;
        System.out.println(finalRoomN);
    }
}
