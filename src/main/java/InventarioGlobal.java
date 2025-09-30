import java.util.ArrayList;
import java.util.List;

public class InventarioGlobal {
    private static List<Objeto> inventarioGlobal = new ArrayList<>();

    public static void agregarAlInventarioGlobal(Objeto objeto, ArrayList<Objeto> inventarioGlobal) {
        inventarioGlobal.add(objeto);
    }

    public static void eliminarDelInventarioGlobal(Objeto objeto) {
        inventarioGlobal.remove(objeto);
    }

    public static List<Objeto> getInventarioGlobal() {
        return inventarioGlobal;
    }
}