package remise;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final JdbcTemplate jdbcTemplate;

    public TransactionService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        createTableIfNotExists();
    }

    // Création de la table si elle n'existe pas
    private void createTableIfNotExists() {

        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS TRANSACTION_ENTITY (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                        "date TIMESTAMP," +
                        "montant_avant DOUBLE," +
                        "montant_apres DOUBLE," +
                        "remise_id BIGINT" +
                        ")"
        );
    }

    //  INSERT transaction
    public Transaction save(double montantAvant,
                            double montantApres,
                            RemiseEntity remise) {

        if (montantAvant <= 0) {
            throw new RemiseException("Montant invalide !");
        }

        String sql =
                "INSERT INTO TRANSACTION_ENTITY(date, montant_avant, montant_apres, remise_id) VALUES(?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                java.time.LocalDateTime.now(),
                montantAvant,
                montantApres,
                remise != null ? remise.getId() : null
        );

        Transaction t = new Transaction();

        t.setMontantAvant(montantAvant);
        t.setMontantApres(montantApres);
        t.setRemise(remise);

        return t;
    }

    //  UPDATE transaction
    public void update(Long id, double nouveauMontant) {

        String sql =
                "UPDATE TRANSACTION_ENTITY SET montant_avant=?, montant_apres=? WHERE id=?";

        jdbcTemplate.update(
                sql,
                nouveauMontant,
                nouveauMontant,
                id
        );
    }

    // DELETE transaction
    public void deleteById(Long id) {

        String sql =
                "DELETE FROM TRANSACTION_ENTITY WHERE id=?";

        jdbcTemplate.update(sql, id);
    }

    //  GET transaction by ID (IMPORTANT POUR L'API REST)
    public Transaction findById(Long id) {

        String sql =
                "SELECT * FROM TRANSACTION_ENTITY WHERE id=?";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (rs, rowNum) -> {

                    Transaction t = new Transaction();

                    t.setId(rs.getLong("id"));

                    t.setMontantAvant(
                            rs.getDouble("montant_avant"));

                    t.setMontantApres(
                            rs.getDouble("montant_apres"));

                    // récupération remise_id
                    Long remiseId =
                            rs.getLong("remise_id");

                    if (remiseId != 0) {

                        RemiseEntity r =
                                new RemiseEntity();

                        r.setId(remiseId);

                        t.setRemise(r);
                    }

                    return t;
                }
        );
    }
}