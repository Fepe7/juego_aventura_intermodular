import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class GuardarDatos {
    public static void guardarPersonajes(Personaje[] personajes, String nombreArchivo) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            gson.toJson(personajes, writer);
            System.out.println("Personajes guardados en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar los personajes: " + e.getMessage());
        }
    }
}
