package remise;

import org.springframework.stereotype.Component;

@Component("variable")
public class RemiseVariable implements Remise {

    @Override
    public double calculerRemise(double montant) {
        if (montant < 1000) {
            return montant * 0.05;
        } else if (montant < 5000) {
            return montant * 0.10;
        } else {
            return montant * 0.15;
        }
    }
}