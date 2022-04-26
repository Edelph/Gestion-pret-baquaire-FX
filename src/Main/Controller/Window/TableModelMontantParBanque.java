package Main.Controller.Window;

public class TableModelMontantParBanque {
    private String banque;
    private int taux;
    private String effectif;
    private int total;

    public String getBanque() {
        return banque;
    }

    public void setBanque(String banque) {
        this.banque = banque;
    }

    public int getTaux() {
        return taux;
    }

    public void setTaux(int taux) {
        this.taux = taux;
    }

    public String getEffectif() {
        return effectif;
    }

    public void setEffectif(String effectif) {
        this.effectif = effectif;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTotalAPayer() {
        Double dmontant ;
        dmontant = Double.parseDouble(Integer.toString(this.total));
        return Double.toString(this.total + (dmontant / this.taux));

    }

}
