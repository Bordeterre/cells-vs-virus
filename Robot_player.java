import java.io.*;
import java.util.*;
class Robot_player extends Player{

    // Création
    public Robot_player(boolean cells_team){
        super(false,cells_team);
    }


    //Choisir un agent aléatoirement et retourne sa position
    public int[] select_choice(Vector <Agent> Agents){
        boolean alive = false;
        int[] pos = {-1,-1};
        while(!alive){
            int max = Agents.size();
            Random ran = new Random();
            int nb = ran.nextInt(max);
            Agent selected = Agents.get(nb);
            alive = selected.isAlive();
            pos = selected.position(); 
        }   
        return pos ;
    }

    // Choisir le déplacement de l'Agent aléatoirement
    public String move_choice(Agent selected){
        Random ran = new Random();
        int nb = ran.nextInt(4);
        switch (nb){
            case 0 : {return "z";}
            case 1 : {return "s";}
            case 2 : {return "d";} 
            case 3 : {return "q";}
        }
        return "Error"; 
    }
}
