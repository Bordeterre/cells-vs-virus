import java.io.*;
import java.util.*;
abstract class Player{
    private boolean human_player; //true pour human, false pour ordi
    private boolean cells_team; //true pour cells, false pour virus
    private String agent_name; //Nom utilisé dans les messages pour les agents
    
    // Création
    public Player(boolean human_player, boolean cells_team){
        this.human_player = human_player;
        this.cells_team = cells_team;
        
        // Déterminer le nom des Agents du joueur
        if (cells_team){
            agent_name = "cellule";                         
        } else {
            agent_name = "virus";
        }
    }

    //Publier paramètres
    public String get_name(){
        return agent_name;
    }

    //Tour de jeu du joueur
    // Vérifier si une consition de victoire est remplie
    // Demander l'Agent à sélectionner tant que la position ne contient pas d'Agent possible ou que le mouvement n'est pas possible
    // Executer le mouvement possible
    // Vérifier l'interaction Agent-Agent
    public void turn(Board board, int moves){
        for(int i = 0; i<moves; i++){
            board.check_victory();
            Agent selected = null;
            boolean legal_movement = false;
            System.out.println("Mouvement " + (i+1) +"/" + moves +" : ");
            while (selected == null || !legal_movement){
                selected = select(board);
                selected.debug();
                if(selected != null){
                    legal_movement=move(selected,board);
                }
            }
            
            if(cells_team){
                board.cell_interacting( (Cell) selected);
            } else {
                board.virus_interacting( (Virus) selected);
            }
            board.show();
        }
    }

    // Déterminer le vecteur à utiliser par le joueur
    // Retourner l'Agent ayant la même position que celle entrée par le joueur s'il existe
    public Agent select(Board board){
        Vector <Agent> Agents;
        if(cells_team){
            Agents = board.Cells;
        } else {
            Agents = board.Virus;
        }
        
        int [] choice = select_choice(Agents);
        if (choice[0] != -1){ //Prevent from selecting dead agents
            for(Iterator e = Agents.iterator(); e.hasNext();){
                Agent item = (Agent) e.next();
                if (Arrays.equals(item.position(),choice)){
                    return item;
                }
            }
        }
        System.out.println("Il n'y a pas de "+agent_name+" à cette position !");
        return null;
    }

    public abstract int[] select_choice(Vector <Agent> Agents); 

    // Si le mouvement n'est pas possible (ex : en dehors du plateau), remettre l'Agent à sa place initiale 
    // Redemander d'entrer un mouvement
    public boolean move(Agent selected,Board board){
        String direction = move_choice(selected);
        boolean legal = selected.move(direction);
        int[] pos = selected.position();
        int size = board.getSize();

        Map<String,String> opposite = new HashMap<>();
        opposite.put("z","s");
        opposite.put("s","z");
        opposite.put("q","d");
        opposite.put("d","q");
        
        if (!legal){
            System.out.println("Veuillez entrer une direction valide ! (z/q/s/d)");
            return false;
        }
     
        if (pos[0] <0 || pos[1] <0 || pos[0] >= size || pos[1] >= size){
            selected.move(opposite.get(direction));
            if (human_player){
                System.out.println("Vous ne pouvez pas sortir du plateau !");
            }
            
            return false; 
        }
        //System.out.println("==========");
        //System.out.println("Direction : "+direction);
        //System.out.print("Selected : ");selected.debug();
        //System.out.println("==========");
        // Décommenter ces lignes pour avoir des informations de débugage
        return true;
    }

    public abstract String move_choice(Agent selected);
}
