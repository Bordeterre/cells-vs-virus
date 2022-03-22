import java.io.*;
import java.util.*;
abstract class Robot_player extends Player{

    public Robot_player(boolean cells_team){
        super(true,cells_team);
    }


//Choisir un agent

public Agent random_agent(Vector <Agent> Agents){
    int max = Agents.size();
    Random ran = new Random();
    int nb = ran.nextInt(max);
    return Agents.get(nb);
}

public String random_move(Vector <Agent> Agents){
    Agent selected = random_agent(Agents);
    Random ran = new Random();
    int nb = ran.nextInt(4);
    switch (nb){
        case 1 : {return "N";}
        case 2 : {return "S";}
        case 3 : {return "E";}
        case 4 : {return "W";}
    }
    return "C";
}



}