package Juego_nuevo.mapa;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Quieres insertar una seed?: (y/N)");
        String seed = sc.nextLine();
        Map map;
        if (seed.isEmpty()) {

            map = new Map();
        } else {
            map = new Map(Integer.parseInt(seed));
        }


        map.generateLayout();

        System.out.println(map);
    }
}

