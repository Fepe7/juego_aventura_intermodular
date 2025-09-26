import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.IOException;

public class ImportarDatos {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileReader reader = new FileReader("jugador.json")) {
             Personaje personaje = gson.fromJson(reader, Personaje.class);

            System.out.println("Jugador cargado desde JSON:");
            System.out.println("Nombre: " + personaje.getNombre());
            System.out.println("Clase: " + personaje.getClase());
            System.out.println("Vida: " + personaje.getVida());
            System.out.println("Ataque: " + personaje.getAtaque());
            System.out.println("Velocidad: " + personaje.getVelocidad());
            System.out.println("Nivel: " + personaje.getNivel());
            System.out.println("Mana: " + personaje.getMana());
            System.out.println("Vivo: " + personaje.isVivo());
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON: " +   e.getMessage());

        }
    }
}
