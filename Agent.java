
import java.io.*;
import java.util.*;
abstract class Agent{
    private boolean alive = true; //Détermine le status de l'agent
    private int x; //Position x
    private int y; //Position y
    private String icon; //Icon affichée
    
    // Création de l'agent
    public Agent (int pos_x, int pos_y, String icon){ 
        x = pos_x; 
        y = pos_y; 
        this.icon = icon;                                           
    } 
    
    // Change l'icone de l'Agent (par exemple, quand une cellule X devient Y, ou quand un virus infecte un cellule)
    public void setIcon(String new_icon){
        icon = new_icon;
    }

    // Renvoie la position de l'agent 
    public int[] position(){
        int[] pos = {x,y};
        return pos;
    }
    
    //Renvoie l'icone de l'Agent
    public String display(){
        return icon;
    }
    
    // Renvoie true si l'Agent est vivant, false sinon
    public boolean isAlive(){
        return alive;
    }

    abstract void debug();

    
    abstract void death();
    
    // Destruction : change les coordonnées de l'Agent pour le déplacer en dehors du plateau. Appelé par death()
    public void true_death(){
        x = -1;
        y = -1;
        this.alive = false;
    }

    // Déplacement : ajouter ou enlever 1 aux positions x et y en fonction du mouvement effectué par l'Agent
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
