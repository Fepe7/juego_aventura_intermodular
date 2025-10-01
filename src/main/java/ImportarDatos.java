import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.IOException;

public class ImportarDatos {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileReader reader_jugador = new FileReader("partida.json")) {
            Personaje personaje = gson.fromJson(reader_jugador, Personaje.class);

            System.out.println("Jugador cargado desde JSON:");
            System.out.println("Nombre: " + personaje.getNombre());
            System.out.println("Clase: " + personaje.getClase());
            System.out.println("Vida: " + personaje.getVida());
            System.out.println("Ataque: " + personaje.getAtaque());
            System.out.println("Velocidad: " + personaje.getVelocidad());
            System.out.println("Mana: " + personaje.getMana());
            System.out.println("Vivo: " + personaje.isVivo());
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());

        }

        try (FileReader reader_inventario = new FileReader("objetos.json")) {
            InventarioGlobal inventarioGlobal = gson.fromJson(reader_inventario, InventarioGlobal.class);

            System.out.println("Inventario cargado desde JSON:");
            System.out.println("Inventario: " + inventarioGlobal);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }
    }
}
