package Juego_nuevo.persistencia_datos_JSON;

import Juego_nuevo.Enemigo;
import Juego_nuevo.Evento;
import Juego_nuevo.Objeto;
import Juego_nuevo.Personaje;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * La clase <strong>{@link EstadoPartida}</strong> es la clase que alberga los métodos de <strong>guardado y carga</strong>
 * de partida, esencialmente hay 2 métodos, homónimos a los antes mencionados que hacen lo homónimo a sus títulos bastante
 * descriptivos.
 */
public class EstadoPartida {
    /**
     * La función <strong>{@code createGson()}</strong> crea el objeto {@link Gson} con {@link GsonBuilder} para obtener su
     * función {@code setPrettyPrinting()} el cual permite mostrarlo de forma ordenada y bonita.
     * @return  el objeto {@link Gson}
     */
    private static Gson createGson() { return new GsonBuilder().setPrettyPrinting().create(); }

    /**
     * La función <strong>{@code guardarPartida()}</strong> guarda la partida actual, contando los personajes, objetos
     * y la seed (semilla) del mapa
     * <p>
     * Esta función usa el objeto {@link Gson} y el objeto contenedor {@link DatosJuego}, con las listas pasadas como
     * atributos de la clase. Dentro del {@code try-catch-with-resources} declaramos el {@link FileWriter}
     * con archivo en <strong>Partida.json</strong> y ahí serializa el contenedor {@link DatosJuego} que tiene ambas
     * listas y la seed. Si no, lanza una excepción dando un error.
     * @param personajes La lista de personajes que hay
     * @param objeto La lista de objetos que tienen globalmente todos
     * @param seed La semilla que se usa para generar el mapa
     */
    public static void guardarPartida(Personaje[] personajes, ArrayList<Objeto> objeto, int seed, Evento[][] eventos) {
        final var gson = createGson();
        final var partida = new DatosJuego(personajes, objeto, seed, eventos);
        try (final var fw = new FileWriter("Partida.json")) {
            gson.toJson(partida, fw);
            IO.println("Se ha creado el JSON partida");

            //Guarda los eventos del mapa
            guardarMapa(seed, eventos);
        } catch (Exception e) {
            System.out.println("Error al guardar partida");
            System.out.println(e.getMessage());
        }
    }

    /**
     * La función <strong>{@code cargarPartida()}</strong> carga la partida y devuelve un contenedor con los datos.
     * <p>
     * Esta función está dentro de un {@code try-catch-with-resources} donde declaramos {@link FileReader} con fichero
     * de <strong>partida.json</strong> y cargamos partidas, devolviendo el contenedor con los datos. Si no, lanza una
     * excepción de tipo {@link Exception}, el cual devolverá null si eso pasara
     * @return  El contenedor {@link DatosJuego} que contiene personajes y objetos | {@code null} si hay un error
     */
    public static DatosJuego cargarPartida() {
        try (final var fr = new FileReader("Partida.json")) {
            return new Gson().fromJson(fr, DatosJuego.class);
        } catch (Exception e) {
            System.out.println("Error al cargar partida");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * La función <strong>{@code crearPersonaje()}</strong> crea un personaje mediante su nombre pasado, que está serializado
     * <p>
     * Esta función lee {@code Personajes.json} y desestructura el contenido en un array y después evalua ese array con
     * un {@code for-each} el cual compara nombres, si el nombre es el pasado por argumentos de la función, lo devuelve.
     * Si no, devuelve {@code null}.
     * @param nombrePersonaje Es el nombre del personaje que se desea.
     * @return El {@link Personaje} si se encuentra | {@code null} si no se ha encontrado | {@code null} debido a una excepción
     */
    public static Personaje crearPersonaje(String nombrePersonaje) {
        try (final var fr = new FileReader("Personajes.json")) {
            final var personajes = new Gson().fromJson(fr, Personaje[].class);
            for (final var p : personajes) {
                if (p.getNombre().equals(nombrePersonaje)) {
                    return p;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error al crear el personaje");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * La función <strong>{@code crearEnemigo()}</strong> crea un enemigo a partir del nombre pasado, que está serializado
     * <p>
     * Esta función lee {@code Enemigos.json} y lo desestructura en un array de {@link Enemigo}. Después evalua el array
     * con un {@code for-each} y, si el nombre del {@link Enemigo} coincide con el nombre pasado, devuelve él
     * {@link Enemigo}. Si no, devuelve {@code null}.
     * @param nombreEnemigo El nombre del {@link Enemigo} que se desea crear del JSON
     * @return Él {@link Enemigo} con el nombre | {@code null} si no lo encuentra | {@code null} si hay una excepción
     */
    public static Enemigo crearEnemigo(String nombreEnemigo) {
        try (final var fr = new FileReader("Enemigos.json")) {
            final var enemigos = new Gson().fromJson(fr, Enemigo[].class);
            for (final var e : enemigos) {
                if (e.getNombre().equals(nombreEnemigo)) {
                    return e;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error al crear el enemigo");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * La función <strong>{@code guardarMapa}</strong> guarda el mapa en el json <strong>Mapa.json</strong>
     * <p>
     * Esta función crea una clase (record) contenedor <strong>{@link DatosMap}</strong> y guarda los parámetros pasados
     * <strong>(seed y eventos)</strong> después serializa ese contenedor al JSON antes mencionado. Si no genera un error.
     * @param seed
     * @param eventos
     */
    public static void guardarMapa(int seed, Evento[][] eventos) {
        final var gson = createGson();
        final var datosMap = new DatosMap(seed, eventos);
        try (final var fw = new FileWriter("Mapa.json")) {
            gson.toJson(datosMap, fw);
        } catch (Exception e) {
            System.out.println("Error al guardar mapa: " + e.getMessage());
        }
    }
}


