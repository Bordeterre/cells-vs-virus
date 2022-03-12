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

    // Publier paramètres 

    // Interaction
    public boolean infection(){
        if (immune){
            System.out.println("La cellule est immune au virus ! ");
            return false;
        } else if (!infected) {
            System.out.println("La cellule est déja infectée par un virus !");
            return false;
        } else {
            System.out.println("Infection réussie !");
            infected = true;
            return true;
        }
    }
}
