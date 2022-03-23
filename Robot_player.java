import java.io.*;
import java.util.*;
class Robot_player extends Player{

    public Robot_player(boolean cells_team){
        super(false,cells_team);
    }


//Choisir un agent

public int[] select_choice(Vector <Agent> Agents){
    int max = Agents.size();
    Random ran = new Random();
    int nb = ran.nextInt(max);
    Agent selected = Agents.get(nb);
    return selected.position();
}

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