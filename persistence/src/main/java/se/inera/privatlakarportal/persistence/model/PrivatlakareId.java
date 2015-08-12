package se.inera.privatlakarportal.persistence.model;

import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * Created by pebe on 2015-06-24.
 */
@Entity
@Table(name = "PRIVATLAKARE_ID")
public class PrivatlakareId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
