package Juego_nuevo.persistencia_datos_JSON;

import Juego_nuevo.Objeto;
import Juego_nuevo.Personaje;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class EstadoPartida {
    /**
     * La función <strong>{@code createGson()}</strong> crea el objeto {@link Gson} con {@link GsonBuilder} para obtener su
     * función {@code setPrettyPrinting()} el cual permite mostrarlo de forma ordenada y bonita.
     * @return  el objeto {@link Gson}
     */
    private static Gson createGson() { return new GsonBuilder().setPrettyPrinting().create(); }

    /**
     * La función <strong>guardarPartida()</strong> guarda la partida actual, contando los personajes, objetos y la
     * ubicación del mapa
     * <p>
     * Esta función usa el objeto {@link Gson}. Dentro del {@code try-catch-with-resources} declaramos {@link FileWriter}
     * con fichero de <strong>partida.json</strong>, y guardamos las listas pasadas. Si no, lanza una excepción de tipo {@link Exception}
     * el cual saca del programa de forma fatal.
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

    /**
     * La función <strong>{@code cargarPersonajes()}</strong> carga los personajes
     * <p>
     * Esta función usa el objeto {@link Gson}. Dentro del {@code try-catch-with-resources} declaramos {@link FileWriter}
     * y le pasamos el archivo de origen, el cual es <strong>partida.json</strong> y devuelve el resultado de
     * la función {@code gson.fromJson(FileWriter, Personaje[].class)}, lo cual lee la línea y mete el objeto en
     * el array de Personaje. Si no, lanza una excepción de tipo {@link Exception}, devolviendo null
     * @return La lista de personajes en forma de array | null
     */
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
        return null;
    }
}
