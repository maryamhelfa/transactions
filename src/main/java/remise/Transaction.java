package remise;

import java.time.LocalDateTime;

public class Transaction {

    private Long id;
    private LocalDateTime date;
    private double montantAvant;
    private double montantApres;
    private RemiseEntity remise;

    public Transaction() {
        this.date = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDate() { return date; }

    public double getMontantAvant() { return montantAvant; }
    public void setMontantAvant(double montantAvant) { this.montantAvant = montantAvant; }

    public double getMontantApres() { return montantApres; }
    public void setMontantApres(double montantApres) { this.montantApres = montantApres; }

    public RemiseEntity getRemise() { return remise; }
    public void setRemise(RemiseEntity remise) { this.remise = remise; }
}