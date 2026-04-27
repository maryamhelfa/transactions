package ma.ehei.gi4;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class C  implements I{
    @Override
    public void executer() {
        System.out.println("-- Méthode exécutée depuis C");
    }
}
