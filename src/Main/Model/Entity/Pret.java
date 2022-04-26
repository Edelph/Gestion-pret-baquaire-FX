package Main.Model.Entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pret {
    private int id;

    private Banque banque = null;
    private Client client = null;

    private String numBanque;
    private String numCompte;
    private int montant;
    private Date datePret;
    private String date;

    public String getNumBanque() {
        return numBanque;
    }

    public void setNumBanque(String numBanque) {
        this.numBanque = numBanque;
    }

    public String getNumCompte() {
        return numCompte;
    }

    public void setNumCompte(String numCompte) {
        this.numCompte = numCompte;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String getMontanPayer(){
        Double dmontant ;
        dmontant = Double.parseDouble(Integer.toString(this.montant));
        return Double.toString( dmontant + (dmontant / this.banque.getTauxDePret()));

    }

    public Date getDatePret() {
        return datePret;
    }

    public String getStringDate(){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return simpleDateFormat.format(this.datePret);
    }


    public void setDatePret(String datePret) {
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.datePret = simpleDateFormat.parse(datePret);

        }catch (ParseException e){
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                this.datePret = simpleDateFormat.parse(datePret);

            }catch (ParseException e2){
                e2.printStackTrace();
            }
        }

        this.date = this.getStringDate();
    }

    public int getId() {
        return id;
    }

    public Banque getBanque() {
        return banque;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
        this.numBanque = banque.getNumBanque();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        this.numCompte = client.getNumCompte();
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }
}
