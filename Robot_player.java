import java.io.*;
import java.util.*;
class Robot_player extends Player{

    public Robot_player(boolean cells_team){
        super(false,cells_team);
    }


//Choisir un agent

public int[] select_choice(Vector <Agent> Agents){
    boolean alive = false;
    int[] pos = {-1,-1};
    while(!alive){
        int max = Agents.size();            // Défini le maximu pour le nombre aléatoire à la taille du vecteur Agent
        Random ran = new Random();
        int nb = ran.nextInt(max);          // Donne un entier aléatoire entre 0 et max
        Agent selected = Agents.get(nb);    // Sélectionne l'Agent à la position nb
        alive = selected.isAlive();         // Cherche si l'Agent est vivant
        pos = selected.position();          // Prend la position de l'Agent sélectionné
    }
    
    return pos ;                            // Retourne la position de l'Agent sélectionné
}

public String move_choice(Agent selected){
    Random ran = new Random();
    int nb = ran.nextInt(4);                // Donne un entier aléatoire entre 0 et 3 inclus
    switch (nb){
        case 0 : {return "z";}
        case 1 : {return "s";}
        case 2 : {return "d";}
        case 3 : {return "q";}
    }
    return "Error";                         // Retour par défaut
}



}
