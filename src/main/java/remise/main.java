package remise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(main.class, args);

        Facture f = context.getBean(Facture.class);
        TransactionService ts = context.getBean(TransactionService.class);

        // try-catch sur afficherFacture
        try {
            f.afficherFacture(2000);
        } catch (Exception e) {
            System.err.println("Erreur afficherFacture : " + e.getMessage());
        }

        // try-catch sur save
        try {
            Transaction t = ts.save(3000, 2700, null);
            if (t != null) {
                ts.update(t.getId(), 2500.0);
                ts.deleteById(t.getId());
            }
        } catch (Exception e) {
            System.err.println("Erreur transaction : " + e.getMessage());
        }
    }
}