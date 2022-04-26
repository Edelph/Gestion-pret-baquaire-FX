package Main.Model.Entity;

public class Banque {
    private String numBanque;
    private String designation;
    private int tauxDePret;
    private Double montantAPayer;

    public String getNumBanque() {
        return numBanque;
    }

    public void setNumBanque(String numBanque) {
        this.numBanque = numBanque;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getTauxDePret() {
        return tauxDePret;
    }

    public void setTauxDePret(int tauxDePret) {
        this.tauxDePret = tauxDePret;
    }

    public Double getMontantAPayer() {
        return montantAPayer;
    }

    public void setMontantAPayer(Double montantAPayer) {
        this.montantAPayer = montantAPayer;
    }
}
