package remise;

public class TransactionDTO {

    private double montant;
    private Long userId;

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}