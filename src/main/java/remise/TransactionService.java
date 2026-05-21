package remise;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final JdbcTemplate jdbcTemplate;

    public TransactionService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS TRANSACTION_ENTITY (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                        "date TIMESTAMP," +
                        "montant_avant DOUBLE," +
                        "montant_apres DOUBLE," +
                        "remise_id BIGINT," +
                        "user_id BIGINT" +
                        ")"
        );
    }

    public Transaction save(double montantAvant,
                            double montantApres,
                            RemiseEntity remise) {
        try {
            if (montantAvant <= 0) {
                throw new RemiseException("Montant invalide !");
            }

            String sql =
                    "INSERT INTO TRANSACTION_ENTITY(date, montant_avant, montant_apres, remise_id, user_id) VALUES(?, ?, ?, ?, ?)";

            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            User user = null;

            jdbcTemplate.update(connection -> {
                java.sql.PreparedStatement ps =
                        connection.prepareStatement(sql, new String[]{"id"});

                ps.setObject(1, java.time.LocalDateTime.now());
                ps.setDouble(2, montantAvant);
                ps.setDouble(3, montantApres);

                if (remise != null) {
                    ps.setLong(4, remise.getId());
                } else {
                    ps.setNull(4, java.sql.Types.BIGINT);
                }

                if (user != null) {
                    ps.setLong(5, user.getId());
                } else {
                    ps.setNull(5, java.sql.Types.BIGINT);
                }

                return ps;
            }, keyHolder);

            Transaction t = new Transaction();

            if (keyHolder.getKey() != null) {
                t.setId(keyHolder.getKey().longValue());
            }

            t.setMontantAvant(montantAvant);
            t.setMontantApres(montantApres);
            t.setRemise(remise);
            t.setUser(user);

            return t;

        } catch (RemiseException e) {
            System.err.println("Erreur dans save() : " + e.getMessage());
            return null;
        }
    }

    public Transaction findById(Long id) {
        try {
            String sql = "SELECT * FROM TRANSACTION_ENTITY WHERE id=?";

            return jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{id},
                    (rs, rowNum) -> {
                        Transaction t = new Transaction();
                        t.setId(rs.getLong("id"));
                        t.setMontantAvant(rs.getDouble("montant_avant"));
                        t.setMontantApres(rs.getDouble("montant_apres"));

                        Long remiseId = rs.getLong("remise_id");
                        if (remiseId != 0) {
                            RemiseEntity r = new RemiseEntity();
                            r.setId(remiseId);
                            t.setRemise(r);
                        }

                        Long userId = rs.getLong("user_id");
                        if (userId != 0) {
                            User u = new User();
                            u.setId(userId);
                            t.setUser(u);
                        }

                        return t;
                    }
            );

        } catch (Exception e) {
            System.err.println("Erreur dans findById() : " + e.getMessage());
            return null;
        }
    }

    public void deleteById(Long id) {
        try {
            jdbcTemplate.update("DELETE FROM TRANSACTION_ENTITY WHERE id=?", id);
        } catch (Exception e) {
            System.err.println("Erreur dans deleteById() : " + e.getMessage());
        }
    }

    public void update(Long id, double montantApres) {
        try {
            if (montantApres <= 0) {
                throw new RemiseException("Montant après invalide !");
            }
            jdbcTemplate.update(
                    "UPDATE TRANSACTION_ENTITY SET montant_apres=? WHERE id=?",
                    montantApres, id
            );
        } catch (RemiseException e) {
            System.err.println("Erreur dans update() : " + e.getMessage());
        }
    }

    public void update(Long id, double montantAvant, double montantApres) {
        try {
            if (montantAvant <= 0 || montantApres <= 0) {
                throw new RemiseException("Montants invalides !");
            }
            jdbcTemplate.update(
                    "UPDATE TRANSACTION_ENTITY SET montant_avant=?, montant_apres=? WHERE id=?",
                    montantAvant, montantApres, id
            );
        } catch (RemiseException e) {
            System.err.println("Erreur dans update() : " + e.getMessage());
        }
    }
}