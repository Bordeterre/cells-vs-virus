import java.io.*;
import java.util.*;
abstract class Player{
    private boolean human_player; //true pour human, false pour ordi
    private boolean cell_team; //true pour cells, false pour virus
    private String agent_name;
    // Création
    public Player(boolean human_player, boolean cell_team){
        this.human_player = human_player;
        this.cell_team = cell_team;

        if (cell_team){
            agent_name = "cellule";
        } else {
            agent_name = "virus";
        }
    }

    //publier paramètres

    public String get_name(){
        return agent_name;
    }
    //Choisir un agent
    public Agent select(Vector <Agent> Agents, int size){
        int [] choice = select_choice(Agents, size);

        for(Iterator e = Agents.iterator(); e.hasNext();){
            Agent item = (Agent) e.next();
            if (Arrays.equals(item.position(),choice)){
                System.out.println("Position ok !");
                return item;
            }
        }
        System.out.println("Position pas ok !");
        return null;
    }

    public abstract int[] select_choice(Vector <Agent> Agents,int size); 

    //déplacer un agent

    public abstract void move(Agent selected);

}

