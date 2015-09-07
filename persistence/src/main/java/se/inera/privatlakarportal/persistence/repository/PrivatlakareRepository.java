package se.inera.privatlakarportal.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.inera.privatlakarportal.persistence.model.Privatlakare;

import java.util.List;

/**
 * Created by pebe on 2015-06-24.
 */
public interface PrivatlakareRepository extends JpaRepository<Privatlakare, String> {

    @Query("SELECT p from Privatlakare p WHERE p.hsaId = :hsaId")
    Privatlakare findByHsaId(@Param("hsaId") String hsaId);

    @Query("SELECT p from Privatlakare p WHERE p.personId = :personId")
    Privatlakare findByPersonId(@Param("personId") String personId);

    @Query("SELECT p FROM Privatlakare p WHERE p NOT IN (SELECT ly.privatlakare FROM LegitimeradYrkesgrupp ly WHERE ly.namn = 'Läkare')")
    List<Privatlakare> findWithoutLakarBehorighet();

}
