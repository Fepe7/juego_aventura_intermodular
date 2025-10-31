package Juego_nuevo.persistencia_datos_JSON;

import Juego_nuevo.Objeto;
import Juego_nuevo.Personaje;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;

/**
 * La clase <strong>{@code EstadoPartida}</strong> es la clase que alberga los métodos de <strong>guardado y carga</strong>
 * de partida, esencialmente hay 2 métodos, homónimos a los antes mencionados que hacen lo homónimo a sus títulos bastante
 * descriptivo.
 */
public class EstadoPartida {
    /**
     * La función <strong>{@code createGson()}</strong> crea el objeto {@link Gson} con {@link GsonBuilder} para obtener su
     * función {@code setPrettyPrinting()} el cual permite mostrarlo de forma ordenada y bonita.
     * @return  el objeto {@link Gson}
     */
    private static Gson createGson() { return new GsonBuilder().setPrettyPrinting().create(); }

    /**
     * La función <strong>{@code guardarPartida()}</strong> guarda la partida actual, contando los personajes, objetos y la
     * ubicación del mapa
     * <p>
     * Esta función usa el objeto {@link Gson} y el objeto contenedor {@link Partida}, con las listas pasadas como
     * atributos de la clase. Dentro del {@code try-catch-with-resources} declaramos el {@link FileWriter}
     * con archivo en <strong>partida.json</strong> y ahí serializa el contenedor {@link Partida} que tiene ambas listas.
     * Si no, lanza una excepción dando un error.
     * @param personajes La lista de personajes que hay
     * @param objeto La lista de objetos que tienen globalmente todos
     */
    public static void guardarPartida(Personaje[] personajes, Objeto[] objeto) {
        final var gson = createGson();
        final var partida = new Partida(personajes, objeto);
        try (final var fw = new FileWriter("partida.json")) {
            gson.toJson(partida, fw);
        } catch (Exception e) {
            System.out.println("Error al guardar partida");
        }
    }

    /**
     * La función <strong>{@code cargarPartida()}</strong> carga la partida y la devuelve.
     * <p>
     * Esta función está dentro de un {@code try-catch-with-resources} donde declaramos {@link FileReader} con fichero
     * de <strong>partida.json</strong> y cargamos partidas, devolviendo el contenedor con los datos. Si no, lanza una
     * excepción de tipo {@link Exception}, el cual devolverá null si eso pasara
     * @return  El contenedor {@link Partida} que contiene personajes y objetos | null
     */
    public static Partida cargarPartida() {
        try (final var fr = new FileReader("partida.json")) {
            return new Gson().fromJson(fr, Partida.class);
        } catch (Exception e) {
            System.out.println("Error al cargar partida");
            return null;
        }
    }


}
