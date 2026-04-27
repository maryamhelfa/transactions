package ma.ehei.gi4;

import org.springframework.stereotype.Component;

@Component
public class B implements I {

    public void executer() {
        System.out.println("-- Méthode exécutée depuis B");
    }
}