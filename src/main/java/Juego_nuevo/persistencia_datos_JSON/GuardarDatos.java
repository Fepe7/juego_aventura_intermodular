package Juego_nuevo.persistencia_datos_JSON;

import Juego_nuevo.Objeto;
import Juego_nuevo.Personaje;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GuardarDatos {
    public static void guardarPersonajes(Personaje[] personajes) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String archivo = "partida.json";

        try (FileWriter writer = new FileWriter(archivo)) {
            gson.toJson(personajes, writer);
            System.out.println("Personajes guardados en " + archivo);
        } catch (IOException e) {
            System.out.println("Error al guardar los personajes: " + e.getMessage());
        }
    }

    public static void guardarObjetos(List<Objeto> objetos) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String archivo = "objetos.json";

        try (FileWriter writer = new FileWriter(archivo)) {
            gson.toJson(objetos, writer);
            System.out.println("Objetos guardados en " + archivo);
        } catch (IOException e) {
            System.out.println("Error al guardar los objetos: " + e.getMessage());
        }
    }

}
