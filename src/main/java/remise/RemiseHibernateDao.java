package remise;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
//  DAO Hibernate
@Repository
@Transactional
public class RemiseHibernateDao implements RemiseDao {

    //  gestion Hibernate/JPA
    @PersistenceContext
    private EntityManager entityManager;

    // ✅récupérer toutes les remises
    @Override
    public RemiseEntity findByMontant(double montant) {

        String jpql =
                "SELECT r FROM RemiseEntity r " +
                        "WHERE :m BETWEEN r.montantMin AND r.montantMax";

        List<RemiseEntity> list =
                entityManager.createQuery(jpql, RemiseEntity.class)
                        .setParameter("m", montant)
                        .getResultList();

        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    @Override //   // insertion
    public RemiseEntity save(RemiseEntity remise) {
        entityManager.persist(remise);
        return remise;
    }

    @Override //modification
    public RemiseEntity update(RemiseEntity remise) {
        return entityManager.merge(remise);
    }

    @Override
    public void delete(Long id) {

        RemiseEntity r =
                entityManager.find(RemiseEntity.class, id);

        if (r != null) {
            entityManager.remove(r);
        }
    }

    @Override
    public List<RemiseEntity> findAll() {

        return entityManager
                .createQuery(
                        "SELECT r FROM RemiseEntity r",
                        RemiseEntity.class
                )
                .getResultList();
    }
}