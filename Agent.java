
import java.io.*;
import java.util.*;
abstract class Agent{
    public static int nb_agents = 0;

    private int id;
    private boolean alive = true;
    private int x;
    private int y;
    private String icon;
    
    // Création de l'agent et sa modification lorsqu'il bouge
    public Agent (int pos_x, int pos_y, String icon){
        id = nb_agents;
        nb_agents += 1;
        x = pos_x;
        y = pos_y;
        this.icon = icon;
    }  
    // Change l'icone de l'Agent lorsqu'il est infecté ou guéri
    public void setIcon(String new_icon){
        icon = new_icon;
    }

    public void setPosition(int[] pos){
        x = pos[0];
        y = pos[1];
    } 

    // Publier paramètres 
    public int[] position(){
        int[] pos = {x,y};
        return pos;
    }

    public String display(){
        return icon;
    }

    public boolean isAlive(){
        return alive;
    }

    abstract void debug();

    // Destruction : change les coordonnées de l'Agent pour le déplacer en dehors du plateau
    abstract void death();
    public void true_death(){
        x = -1;
        y = -1;
        this.alive = false;
    }

    // Déplacement 
    abstract boolean move (String movement);
    public boolean true_move(String movement){
        switch (movement){
            case "z" : { y += 1;break;}
            case "s" : { y -= 1;break;}
            case "d" : { x += 1;break;}
            case "q" : { x -= 1;break;}
            default : {return false;}
        }
        return true;
    }
}
