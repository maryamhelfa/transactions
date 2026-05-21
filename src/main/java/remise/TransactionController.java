package remise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private RemiseRepository remiseRepository;

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO dto) {
        try {
            double montant = dto.getMontant();

            if (montant <= 0) {
                throw new RemiseException("Montant invalide !");
            }

            RemiseEntity r = remiseRepository.findByMontant(montant);

            double remise = 0;
            if (r != null) {
                remise = montant * r.getTaux();
            }

            double total = montant - remise;
            Transaction t = transactionService.save(montant, total, r);
            return ResponseEntity.ok(t);

        } catch (RemiseException e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransaction(@PathVariable Long id) {
        try {
            Transaction t = transactionService.findById(id);
            if (t == null) {
                throw new RemiseException("Transaction introuvable !");
            }
            return ResponseEntity.ok(t);

        } catch (RemiseException e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        try {
            transactionService.deleteById(id);
            return ResponseEntity.ok("Transaction supprimée avec succès");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur suppression : " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable Long id,
                                               @RequestBody TransactionDTO dto) {
        try {
            double montant = dto.getMontant();

            if (montant <= 0) {
                throw new RemiseException("Montant invalide !");
            }

            RemiseEntity r = remiseRepository.findByMontant(montant);

            double remise = 0;
            if (r != null) {
                remise = montant * r.getTaux();
            }

            double total = montant - remise;
            transactionService.update(id, montant, total);
            return ResponseEntity.ok("Transaction mise à jour avec succès");

        } catch (RemiseException e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }
}