package remise;

public class TransactionDTO {
//l sert à recevoir le montant envoyé par l’API REST.
    private double montant;

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}