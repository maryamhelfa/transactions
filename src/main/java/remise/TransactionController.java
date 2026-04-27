package remise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController//Déclare la classe comme contrôleur REST (retourne du JSON automatiquement)
@RequestMapping("/transactions")//toutes les routes
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private RemiseRepository remiseRepository;

    // POST
    @PostMapping//ec
    public Transaction createTransaction(
            @RequestBody TransactionDTO dto) {//lit le corp json etconvet

        double montant = dto.getMontant();

        RemiseEntity r =
                remiseRepository.findByMontant(montant);

        double remise = 0;

        if (r != null) {
            remise = montant * r.getTaux();
        }

        double total = montant - remise;

        return transactionService.save(
                montant,
                total,
                r
        );
    }

    //  GET by id
    @GetMapping("/{id}")
    public Transaction getTransaction(
            @PathVariable Long id) {

        return transactionService.findById(id);
    }

    //  DELETE by id
    @DeleteMapping("/{id}")//ecrl
    public String deleteTransaction(
            @PathVariable Long id) {//Extrait une valeur depuis l'URL

        transactionService.deleteById(id);

        return "Transaction supprimée avec succès";
    }
}