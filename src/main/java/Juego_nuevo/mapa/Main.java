package Juego_nuevo.mapa;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Quieres insertar una seed?: (y/N)");
        String seed = sc.nextLine();
        Mapa mapa;
        if (seed.isEmpty()) {

            mapa = new Mapa();
        } else {
            mapa = new Mapa(Integer.parseInt(seed));
        }


        mapa.generateLayout();

        System.out.println(mapa.toString());



    }
}

