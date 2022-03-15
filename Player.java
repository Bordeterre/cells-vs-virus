import java.io.*;
import java.util.*;
abstract class Player{
    private boolean human_player; //true pour human, false pour ordi
    private boolean cell_team; //true pour cells, false pour virus
    private String agent_name; //Nom utilisé dans les messages pour les agents
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

    //Turn
    public void turn(Board board, int moves){
        for(int i = 0; i<moves; i++){
            Agent selected = null;
            while (selected == null){
                selected = select(board);
            }
            boolean legal_movement = false;
            while (!legal_movement){
                legal_movement=move(selected,board);
            }
            board.show();
        }
    }


    public Agent select(Board board){
        Vector <Agent> Agents;
        if(cell_team){
            Agents = board.Cells;
        } else {
            Agents = board.Virus;
        }
        
        int [] choice = select_choice(Agents);

        for(Iterator e = Agents.iterator(); e.hasNext();){
            Agent item = (Agent) e.next();
            if (Arrays.equals(item.position(),choice)){
                return item;
            }
        }
        System.out.println("Il n'y a pas de "+agent_name+" à cette position !");
        return null;
    }

    public abstract int[] select_choice(Vector <Agent> Agents); 


    public boolean move(Agent selected,Board board){
        String direction = move_choice(selected);
        selected.move(direction);
        int[] pos = selected.position();
        int size = board.getSize();
        if (pos[0] <0){
            selected.move("E");
            System.out.println("Vous ne pouvez pas sortir du plateau !");
            return false;
        }
        if (pos[1] <0){
            selected.move("N");
            System.out.println("Vous ne pouvez pas sortir du plateau !");
            return false;
        }
        if (pos[0] >= size){
            selected.move("W");
            System.out.println("Vous ne pouvez pas sortir du plateau !");
            return false;
        }
        if (pos[1] >= size){
            selected.move("N");
            System.out.println("Vous ne pouvez pas sortir du plateau !");
            return false;
        }
        return true;
    }

    public abstract String move_choice(Agent selected);
}
