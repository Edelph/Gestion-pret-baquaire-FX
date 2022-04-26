package Main.Controller.Window;

import Main.Model.Entity.Banque;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TableModelListePret {

    private String client;
    private Date date;
    private int montant;
    private Double montantAPayer;
    private int taux;
    private static Banque banque;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String getMontantAPayer() {
        Double dmontant ;
        dmontant = Double.parseDouble(Integer.toString(this.banque.getTauxDePret()));
        return Double.toString(montant + (montant / dmontant));
    }

    public Banque getBanque() {
        return banque;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
    }

    public void setDate(String datePret) {
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.date = simpleDateFormat.parse(datePret);

        }catch (ParseException e){
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                this.date = simpleDateFormat.parse(datePret);

            }catch (ParseException e2){
                e2.printStackTrace();
            }
        }
    }

    public String getDate(){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(this.date);
    }
}
