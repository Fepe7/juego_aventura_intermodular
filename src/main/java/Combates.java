import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class Combates {


    //Devuelve el enemigo con el que se va a combatir segun la ronda en la que estes
    public static Enemigos combate(int ronda){
        Enemigos goblin = new Enemigos( "Goblin", 150, 20, 10, "Un goblin salvaje" );
        Enemigos orco = new Enemigos( "Orco", 250, 25, 5, "Un orco feroz" );
        Enemigos troll = new Enemigos( "Troll", 300, 30, 3, "Un troll gigante" );
        Enemigos dragon = new Enemigos( "Dragón", 400, 50, 25, "Un dragón aterrador" );
        Enemigos lobo = new Enemigos( "Lobo", 80, 20, 15, "Un lobo salvaje" );

        ArrayList<Enemigos> enemigos = new ArrayList<>();

        Collections.addAll(enemigos, lobo, goblin, orco, troll, dragon);

        Enemigos enemigoLuchar = enemigos.get(ronda);

        return  enemigoLuchar;


    }
}