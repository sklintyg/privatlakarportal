package se.inera.privatlakarportal.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.model.PrivatlakareId;

/**
 * Created by pebe on 2015-06-24.
 */
public interface PrivatlakareIdRepository extends JpaRepository<PrivatlakareId, Integer> {
}
