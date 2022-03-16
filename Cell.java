class Cell extends Agent {
    private boolean immune;
    private boolean infected = false;
    private int infection_time = 0;
    private int infection_threshold;
    // Création
    public Cell(int pos_x, int pos_y,boolean immune, int infection_threshold, String icon){
        super(pos_x, pos_y,"("+icon+")");
        this.immune = immune;
        this.infection_threshold = infection_threshold; 
    }

    // Interaction
    public boolean get_immune(){
        return immune;
    }
    public int get_infection_threshold(){
        return infection_threshold;
    }

    public void fusion(boolean immune, int infection_threshold){
        this.immune = this.immune && immune;
        this.infection_threshold = Math.min(this.infection_threshold, infection_threshold);
    }

    public boolean initial_infection(){
        if (immune){
            System.out.println("La cellule est immunisée au virus ! ");
            return false;
        } else if (infected) {
            System.out.println("La cellule est déja infectée par un virus !");
            return false;
        } else {
            System.out.println("Infection réussie !");
            infected = true;
            return true;
        }
    }

    public boolean ongoing_infection(){
        infection_time += 1;
        if (infection_time < infection_threshold){
            death();
            return true;
        }
        return true;
    }
}