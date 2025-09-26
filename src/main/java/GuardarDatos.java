import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;

public class GuardarDatos {
    public static void main(String[] args) {
        Personaje jugador = Personaje.CrearPersonaje();

        Gson gson = new Gson();
        String json = gson.toJson(jugador);

        System.out.println("JSON generado:");
        System.out.println(json);

        try (FileWriter writer = new FileWriter("jugador.json")) {
            writer.write(json);
            System.out.println("Datos guardados en jugador.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
