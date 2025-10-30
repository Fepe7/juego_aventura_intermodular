package Juego_nuevo;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SistemaPostBatalla {

    private static final Random random = new Random();

    public static void opcionesPostBatalla(Personaje[] personajes, ArrayList<Objeto> inventarioGlobal) {
        Scanner scanner = new Scanner(System.in);
        boolean opcionValida = false;

        while (!opcionValida) {
            System.out.println("Todo parece despejado, hacia donde quieres ir ahora");
            System.out.println("1. Derecha");
            System.out.println("2. Izquierda");
            System.out.println("3. Arriba");
            System.out.println("4. Abajo");
            System.out.println("6. Salir");

            int eleccion = scanner.nextInt();

        }
    }










}