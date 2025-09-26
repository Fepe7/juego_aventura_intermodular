import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

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
}
