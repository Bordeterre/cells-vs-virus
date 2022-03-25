class Cell extends Agent {
    private boolean immune; //True pour tuer un virus par simple contact
    private boolean infected = false; //true si cellule infectée par un virus
    private int infection_time = 0; //Nombre de tours consécutifs durant lesquels la cellule à été infectée
    private int infection_threshold; // Nombre de tours d'infections requis pour tuer la cellule
    private Virus virus = null; //Adresse d'un ventuel virus infectant la cellule
    
    // Création
    public Cell(int pos_x, int pos_y,boolean immune, int infection_threshold, String icon){
        super(pos_x, pos_y,"("+icon+")");
        this.immune = immune;
        this.infection_threshold = infection_threshold; 
    }

    // Soin avant la vrai mort, pour faire sortir le virus. 
    public void death (){
        cure();
        true_death();
    }

    // Publier paramètres de position et d'infection
    public void debug(){
        int x = position()[0];
        int y = position()[1];
        String tmp = display();
        tmp += "[ " + (x+1) + ", " + (y+1) + "]";
        if(infected){
            tmp += "Infectée (Il reste " + (infection_threshold-infection_time) + " tours)";
        }
        System.out.println(tmp);
    }

    public Virus getVirus(){
        return virus;
    }

    public boolean get_immune(){
        return immune;
    }

    public int get_infection_threshold(){
        return infection_threshold;
    }

    // Infection des cellules par un virus
    // Changement de la cellule Z en cellule Y après une infection et mort du virus
    // Mort du virus si infection d'une cellule déjà infectée
    // Infection si cellule Y ou X
    public boolean initial_infection(Virus virus){
        if (immune){
            System.out.println("La cellule a étée affaiblie par le virus ! ");
            virus.death();
            setIcon("(Y)");
            immune = false;
            infection_threshold=3;

            return false;
        } else if (infected) {
            System.out.println("La cellule est déja infectée par un virus !");
            virus.death();
            return false;
        } else {
            System.out.println("Infection réussie !");
            infected = true;
            this.virus = virus;
            return true;
        }
    }

    // Gestion de l'infection en fin de tour
    public boolean ongoing_infection(){
        infection_time += 1;
        if (infection_time >= infection_threshold){
            death();
            return true;
        }
        return false;
    }
    
    // Mouvement de la cellule
    public boolean move(String movement){
        boolean legal_move = true_move(movement);
        if (legal_move && virus != null){
            virus.true_move(movement);
        }
        return legal_move;
    }

    //Séparation de la cellule et du virus
    public void cure(){
        if(virus != null){
            virus.exit();
            infection_time = 0;
            virus = null;
            infected = false;
        }

    }
}
