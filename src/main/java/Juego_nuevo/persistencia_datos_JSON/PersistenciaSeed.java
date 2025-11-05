package Juego_nuevo.persistencia_datos_JSON;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class PersistenciaSeed {
    public static void guardarSeed(String seed) {
        try (final var bw = new BufferedWriter(new FileWriter("seed.txt"))) {
            bw.write(seed);
        } catch (Exception e) {
            System.out.println("Error al guardar la seed");
        }
    }

    public static String cargarSeed() {
        try (final var br = new BufferedReader(new FileReader("seed.txt"))) {
            return br.readLine();
        } catch (Exception e) {
            System.out.println("Error al cargar la seed");
            return null;
        }
    }
}
