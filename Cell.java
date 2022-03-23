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

    // Interaction
    public boolean get_immune(){
        return immune;
    }
    public int get_infection_threshold(){
        return infection_threshold;
    }

    public boolean initial_infection(Virus virus){
        if (immune){
            System.out.println("La cellule est immunisée au virus ! ");
            virus.death();
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


}