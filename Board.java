import java.io.*;
import java.util.*;
import java.lang.*;

class Board{
    private int size; //Longueur d'un coté du plateau (en cases)
    public Vector <Agent> Cells; // Regroupe les cellules X, Y et Z
    public Vector <Agent> Virus; //Regroupe les virus

    // Vérifie si deux agents sont en collsion
    public boolean collision_check(Agent A, Agent B){
        if (Arrays.equals(A.position(),B.position())){
            return true;
        }
        return false;
    }
    // Renvoie l'éventuel agent B du vecteur team ayant rentré en collision avec l'agent A
    public Agent full_collision_check(Agent A, Vector <Agent> team){
        for(Iterator e = team.iterator(); e.hasNext();){
            Agent B = (Agent) e.next();
            if (A != B && collision_check(A,B)){
                return B;
            }
        }
        return null;
    }

    // Initialisation du plateau
    // Création d'Agent Cells et Virus
    //appel de populate()
    public Board(int size,int difficulty){
        this.size = size;

        Cells = new Vector<Agent>();
        Virus = new Vector<Agent>();

        populate(Cells, "X_cell",2*(difficulty-1) * size*size/25);
        populate(Cells, "Y_cell",4 * size*size/25);
        populate(Cells, "Z_cell",2*(4-difficulty) * size*size/25);
        populate(Virus,"Virus",3 * size*size/25 );
    }

    // Remplissage du vecteur team avec "amount" agent "type", en évitant les collisions. 
    public void populate(Vector<Agent> team, String type, int amount){
        amount += team.size();

        while(team.size() < amount){
            int x = (int)(Math.random()*size);
            int y = (int)(Math.random()*size);
            Agent item = null;
            switch(type){
                case "X_cell" :
                    item = new Cell(x,y,true,1,"X");
                    break;
                case "Y_cell" :
                    item = new Cell(x,y,false,3, "Y"); 
                    break;
                case "Z_cell" :
                    item = new Cell(x,y,false,1, "Z");
                    break;
                case "Virus" :
                    item = new Virus(x,y,5); 
                    break;
            }

            if(full_collision_check(item,Cells) == null && full_collision_check(item,Virus) == null){
                team.add(item);             
            }
        }
    }

    public int getSize(){
        return size;
    }

    // Affichage de du plateau et des agents
    public void show(){
        //Foreground
        String [] foreground = new String[size*size];
        Arrays.fill(foreground,"   ");
        for(Iterator e = Cells.iterator(); e.hasNext();){
            Agent item = (Agent) e.next();
            int x = item.position()[0];
            int y = item.position()[1];
            //item.debug();
            if (x != -1){
                foreground[size*(size-y-1) +x] = item.display();
            }  
        }
              
        for(Iterator e = Virus.iterator(); e.hasNext();){
            Agent item = (Agent) e.next();
            int x = item.position()[0];
            int y = item.position()[1];
            //item.debug();
            if (x != -1){
                foreground[size*(size-y-1) +x] = item.display();
            }
        }

        //Background
        String line = "───┼";
        for(int x = 0 ; x < size; x++){
            line += "───┼";
        }
        System.out.println(line);

        for(int y = 0 ; y < size; y++){
            line = "";
            String y_string = Integer.toString(size-y);
            line += y_string;
            for(int i = 0 ; i < 3 - y_string.length(); i++){
                line += " ";
            }
            line += "│";
            for(int x = 0 ; x < size; x++){
                line += foreground[y*size + x];
                line += "│";
            }  
            System.out.println(line) ;  

            line = "───┼";
            for(int x = 0 ; x < size; x++){
                line += "───┼";
            }
            System.out.println(line);
        }

        line = "   │";
        for (int x = 1; x <= size; x++){
            String x_string = Integer.toString(x);
            line += x_string;
            for(int i = 0 ; i < 3 - x_string.length(); i++){
                line += " ";
            }
            line += "│";
        }
        System.out.println(line);
    }

  

    // Interaction cellule-Agent
    // Fusion des cellules
    // Infection des cellules ou mort des virus suivant le type de cellule
    public void cell_interacting(Cell A){
        //Cells
        Cell C = (Cell) full_collision_check(A,Cells);
        if (C != null){
            cell_fusion(A,C);
        }

        //Virus  
        Virus V = (Virus) full_collision_check(A,Virus);
        if (V != null){
            if(A.getVirus() == null){
                infection(V,A);
            } else if (A.getVirus() != V){
                V.death();
            }
        }
    }

    // Interaction virus-Agent
    // Infection si collision avec cellule
    // Fusion si collision avec virus
    public void virus_interacting(Virus A){
        Cell C = (Cell) full_collision_check(A,Cells);
        if (C != null){
            infection(A,C);
        }
        //Virus  
        Virus V = (Virus) full_collision_check(A,Virus);
        if (V != null){
            virus_fusion(A,V);
            
        }
    }

    // Fusion des cellules et garder immunité la plus faible
    public void cell_fusion(Cell cell1, Cell cell2){

        if(cell1.get_immune()){
            cell1.death();
            cell_interacting(cell2);
        } else if (cell2.get_immune()){
            cell2.death();
            cell_interacting(cell1);
        } else if(cell1.get_infection_threshold() < cell2.get_infection_threshold()){
            cell2.cure();
            cell2.death();
            cell_interacting(cell1);
        } else {
            cell1.cure();
            cell1.death();
            cell_interacting(cell2);
        }
    }

    // Fusion des virus
    public void virus_fusion(Virus virus1, Virus virus2){
        virus1.set_turns(virus1.get_turns()+virus2.get_turns());
        virus2.death();
    }

    // Tentative d'infection cellule par virus
    public void infection(Virus virus, Cell cell){
        if (cell.initial_infection(virus)){
            virus.infect(cell);
        }
    }

    // Division d'un virus
    public void turn(Player virus_player){
        Virus item2 = null;
        for(Iterator e = Virus.iterator(); e.hasNext();){
            Virus item = (Virus) e.next();
            int [] position = item.update();
            if (position[0] != -1){
                int x = position[0];
                int y = position[1];
                item2 = new Virus(x,y,5);
                boolean legal_movement = false;
                
                System.out.println("Votre virus s'est cloné ! Vers quelle direction voulez vous déplacer le nouveau né ?");
                item.debug();
                while (!legal_movement){
                    legal_movement = virus_player.move(item2,this);
                }
                Virus.add(item2);
                virus_interacting(item2);
                break;
            }
        }
        show();
    }

    // Vérifier si une condition de victoire a été réalisée
    public void check_victory(){
        Virus.removeIf(e-> !e.isAlive());
        Cells.removeIf(e-> !e.isAlive());
        if(Virus.size() == 0){
            System.out.println("Tous les virus ont étés exterminés ! Player two wins !");
            System.exit(0);
        }
        if(Cells.size() == 0){
            System.out.println("Toutes les cellules ont étés exterminés ! Player one wins !");
            System.exit(0);
        }
    }
}
