package remise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Facture {

    @Autowired
    @Qualifier("db")
    private Remise remise;

    @Autowired
    private RemiseRepository remiseRepository;

    @Autowired
    private TransactionService transactionService;

    public void afficherFacture(double montant) {

        double r = remise.calculerRemise(montant);
        double total = montant - r;

        System.out.println("Montant initial : " + montant);
        System.out.println("Remise : " + r);
        System.out.println("Total : " + total);

        RemiseEntity remiseEntity = remiseRepository.findByMontant(montant);
        transactionService.save(montant, total, remiseEntity);
    }
}