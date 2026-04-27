package ma.ehei.gi4;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // creation de l'app contexte !
        // Creation des beans A & B
        System.out.println("## Initialisation d'app contexte");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("## Recuperation du bean A");
        // get bean ! recuperation d'un bean déjà initié !!
        A a = context.getBean(A.class);//-----
        System.out.println("## Appel de la fonction : faireAction");
        // appel de la fonction : faireAction
        a.faireAction();

    }
}
