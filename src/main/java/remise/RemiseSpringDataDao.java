package remise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemiseSpringDataDao
        extends JpaRepository<RemiseEntity, Long> {

    RemiseEntity findByMontantMinLessThanEqualAndMontantMaxGreaterThanEqual(
            double montant1,
            double montant2
    );
}