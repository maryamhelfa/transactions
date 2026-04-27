package remise;

import org.springframework.stereotype.Component;

@Component("fixe")
public class RemiseFixe implements Remise {

    @Override
    public double calculerRemise(double montant) {
        return 50;
    }
}