package remise;

import org.springframework.stereotype.Component;

@Component("fixe")
public class RemiseFixe implements Remise {

    @Override
    public double calculerRemise(double montant) throws RemiseException {
        if (montant <= 0) {
            throw new RemiseException("Montant doit être > 0");
        }
        return 50;
    }
}