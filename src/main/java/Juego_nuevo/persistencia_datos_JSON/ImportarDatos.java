package Juego_nuevo.persistencia_datos_JSON;

import Juego_nuevo.Objeto;
import Juego_nuevo.Personaje;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ImportarDatos {

    public static Personaje[] cargarPersonajes() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileReader reader = new FileReader("partida.json")) {
            Personaje[] personajes = gson.fromJson(reader, Personaje[].class);
            return personajes;
        } catch (IOException e) {
            System.out.println("Error al leer partida.json: " + e.getMessage());
            return null;
        }
    }

    public static List<Objeto> cargarObjetos() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type listType = new TypeToken<List<Objeto>>() {
        }.getType();
        try (FileReader reader = new FileReader("objetos.json")) {
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.out.println("Error al leer objetos.json: " + e.getMessage());
            return null;
        }
    }

    public static boolean existePartidaGuardada() {
        return Files.exists(Paths.get("partida.json"));
    }

    public static boolean existeObjetosGuardados() {
        return Files.exists(Paths.get("objetos.json"));
    }
}
