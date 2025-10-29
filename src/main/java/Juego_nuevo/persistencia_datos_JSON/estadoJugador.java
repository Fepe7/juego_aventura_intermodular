

package Juego_nuevo.persistencia_datos_JSON;

import Juego_nuevo.Objeto;
import Juego_nuevo.Personaje;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class estadoJugador {
    /**
     * La función createGson() crea el objeto GSON con prettyPrinting.
     * @return el Objeto GSON
     */
    private static Gson createGson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * La función <strong>guardarPartida()</strong> guarda la partida actual, contando los personajes, objetos y la ubicación del mapa
     * <p>
     * Esta función usa el objeto GSON, y después en el try with resources, donde declaramos el FileWriter con fichero
     * de partida.json, y guardamos las listas pasadas. Si no, tira una excepción
     * @param personajes La lista de personajes que hay
     * @param objeto La lista de objetos que tienen globalmente todos
     */
    public static void guardarPartida(Personaje[] personajes, Objeto[] objeto) {
        final var gson = createGson();
        try (final var fw = new FileWriter("partida.json")) {
            gson.toJson(personajes, fw);
            gson.toJson(objeto, fw);
        } catch (Exception e) {
            System.out.println("Error al guardar partida");
        }
    }

    public static Personaje[] cargarPersonajes() {
        final var gson = createGson();
        try (final var fr = new FileReader("partida.json")) {
            return gson.fromJson(fr, Personaje[].class);
        } catch (Exception e) {
            System.out.println("Error al cargar los personajes");
            return null;
        }
    }

    public static List<Objeto> cargarObjetos() {

    }

}
