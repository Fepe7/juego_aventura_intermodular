import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    // Comprova si existeix el fitxer de partida guardada.
    public static boolean existePartidaGuardada() {
        return Files.exists(Paths.get("partida.json"));
    }
}
