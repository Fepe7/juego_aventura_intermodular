package Juego_nuevo.persistencia_datos_JSON;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class PersistenciaSeed {
    public static void guardarSeed(int seed) {
        try (final var bw = new BufferedWriter(new FileWriter("seed.txt"))) {
            bw.write(seed);
        } catch (Exception e) {
            System.out.println("Error al guardar la seed");
        }
    }

    public static int cargarSeed() {
        try (final var br = new BufferedReader(new FileReader("seed.txt"))) {
            return Integer.parseInt(br.readLine());
        } catch (Exception e) {
            System.out.println("Error al cargar la seed");
            return 0;
        }
    }
}
