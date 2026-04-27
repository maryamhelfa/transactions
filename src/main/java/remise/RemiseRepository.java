package remise;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RemiseRepository {

    private final JdbcTemplate jdbcTemplate;

    public RemiseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RemiseEntity> findAll() {
        String sql = "SELECT * FROM REMISE_ENTITY";//taux;table a objet     h

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RemiseEntity r = new RemiseEntity();
            r.setId(rs.getLong("id"));
            r.setMontantMin(rs.getDouble("montant_min"));
            r.setMontantMax(rs.getDouble("montant_max"));
            r.setTaux(rs.getDouble("taux"));
            return r;
        });
    }

    public RemiseEntity findByMontant(double montant) {
        String sql = "SELECT * FROM REMISE_ENTITY WHERE montant_min <= ? AND montant_max >= ?";

        List<RemiseEntity> list = jdbcTemplate.query(
                sql,
                new Object[]{montant, montant},
                (rs, rowNum) -> {
                    RemiseEntity r = new RemiseEntity();
                    r.setId(rs.getLong("id"));
                    r.setMontantMin(rs.getDouble("montant_min"));
                    r.setMontantMax(rs.getDouble("montant_max"));
                    r.setTaux(rs.getDouble("taux"));
                    return r;
                }
        );

        if (list.isEmpty()) return null;
        return list.get(0);
    }
}