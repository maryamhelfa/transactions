package remise;

import java.util.List;
// interface DAO commune
public interface RemiseDao {


    RemiseEntity findByMontant(double montant);

    // récupérer remise selon montant
    RemiseEntity save(RemiseEntity remise);


    RemiseEntity update(RemiseEntity remise);

    void delete(Long id);

    //  récupérer toutes les remises
    List<RemiseEntity> findAll();
}