class Cell extends Agent {
    private boolean immune;
    private boolean infected = false;
    private int infection_time = 0;
    private int infection_threshold;
    private Virus virus = null;
    // Création
    public Cell(int pos_x, int pos_y,boolean immune, int infection_threshold, String icon){
        super(pos_x, pos_y,"("+icon+")");
        this.immune = immune;
        this.infection_threshold = infection_threshold; 
    }

    public void death (){
        cure();
        true_death();
    }

    // Publier paramètres
    public void debug(){
        int x = position()[0];
        int y = position()[1];
        String tmp = display();
        tmp += "[ " + x + ", " + y + "] ; ";
        tmp += "infection : " + infected + ", " + infection_time;
        if (virus != null){
            tmp += " " + virus.display();
        }
        System.out.println(tmp);
    }

    public Virus getVirus(){
        return virus;
    }
    // Interaction
    public boolean get_immune(){
        return immune;
    }

    public int get_infection_threshold(){
        return infection_threshold;
    }

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

    public boolean ongoing_infection(){
        infection_time += 1;
        if (infection_time >= infection_threshold){
            death();
            return true;
        }
        return false;
    }

    public boolean move(String movement){
        boolean legal_move = true_move(movement);
        if (legal_move && virus != null){
            infected = false;
            virus.true_move(movement);
        }
        return legal_move;
    }

    public void cure(){
        if(virus != null){
            virus.exit();
            infection_time = 0;
            virus = null;
            infected = false;
        }

    }
}