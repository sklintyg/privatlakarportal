package se.inera.privatlakarportal.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by pebe on 2015-06-24.
 */
@Entity
@Table(name = "VERKSAMHETSTYP")
public class Verksamhetstyp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRIVATLAKARE_ID", nullable = false)
    @JsonBackReference
    private Privatlakare privatlakare;

    @Column(name = "KOD", nullable = false)
    private String kod;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            Verksamhetstyp other = (Verksamhetstyp) o;

            if (id == null) {
                return false;
            } else {
                return id.equals(other.id);
            }
        }
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Verksamhetstyp() {
    }

    public Verksamhetstyp(Privatlakare privatlakare, String kod) {
        this.privatlakare = privatlakare;
        this.kod = kod;
    }

    public Privatlakare getPrivatlakare() {
        return privatlakare;
    }

    public void setPrivatlakare(Privatlakare privatlakare) {
        this.privatlakare = privatlakare;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }
}
