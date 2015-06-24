package se.inera.privatlakarportal.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.inera.privatlakarportal.persistence.model.Privatlakare;

/**
 * Created by pebe on 2015-06-24.
 */
public interface PrivatlakareRepository extends JpaRepository<Privatlakare, String> {
}
