
import java.io.*;
import java.util.*;
abstract class Agent{
    public static int nb_agents = 0;

    private int id;
    private boolean alive = true;
    private int x;
    private int y;
    private String icon;
    
    // Création
    public Agent (int pos_x, int pos_y, String icon){
        id = nb_agents;
        nb_agents += 1;
        x = pos_x;
        y = pos_y;
        this.icon = icon;
    }   

    // Publier paramètres 
    public int[] position(){
        int[] pos = {x,y};
        return pos;
    }

    public String display(){
        return icon;
    }

    // Destruction
    public void death (){
        x = -1;
        y = -1;
        this.alive = false;
    }

    // Déplacement 
    public void move(String movement){
        switch (movement){
            case "N" : { y += 1;break;}
            case "S" : { y -= 1;break;}
            case "E" : { x += 1;break;}
            case "W" : { x -= 1;break;}
            default : {System.out.println("Direction invalide");}
        }
    }

    //Specific to cells
    abstract boolean get_immune();
    abstract int get_infection_threshold();
    abstract void fusion(boolean immune, int infection_threshold);
    abstract boolean initial_infection();
    abstract boolean ongoing_infection();
}