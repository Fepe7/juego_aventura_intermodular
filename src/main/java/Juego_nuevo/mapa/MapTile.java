package Juego_nuevo.mapa;

public class MapTile {
    private final String tileSymbol;

    final private int generatedOrder;

    public MapTile() {
        tileSymbol = "   ";
        this.generatedOrder = -1;
    }

    public MapTile(String tileSymbol, int generatedOrder) {
        this.tileSymbol = tileSymbol;
        this.generatedOrder = generatedOrder;
    }

    @Override
    public String toString() {
        return tileSymbol;
    }

    public int getGeneratedOrder() {
        return generatedOrder;
    }

    public String getTileSymbol() {
        return tileSymbol;
    }
}
