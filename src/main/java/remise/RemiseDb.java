package remise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("db")
public class RemiseDb implements Remise {

    @Autowired
    private RemiseRepository repository;

    @Override
    public double calculerRemise(double montant) {
        if (montant <= 0) {
            throw new RemiseException("Montant doit être > 0");
        }

        RemiseEntity r = repository.findByMontant(montant);

        if (r != null) return montant * r.getTaux();
        return 0;
    }
}