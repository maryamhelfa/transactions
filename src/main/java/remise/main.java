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

        f.afficherFacture(2000);

        Transaction t = ts.save(3000, 2700, null);
        ts.update(t.getId(), 2500);
        ts.deleteById(t.getId());
    }
}