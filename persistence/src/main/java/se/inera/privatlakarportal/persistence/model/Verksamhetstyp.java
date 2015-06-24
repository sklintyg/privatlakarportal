package se.inera.privatlakarportal.persistence.model;

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
    @JoinColumn(name = "PERSONID", nullable = false)
    private Privatlakare privatlakare;

    @Column(name = "VERKSAMHET", nullable = false)
    private String verksamhet;

    @Column(name = "VARDFORM", nullable = false)
    private String vardform;

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

    public Verksamhetstyp(Privatlakare privatlakare, String verksamhet, String vardform) {
        this.privatlakare = privatlakare;
        this.verksamhet = verksamhet;
        this.vardform = vardform;
    }

    public Privatlakare getPrivatlakare() {
        return privatlakare;
    }

    public void setPrivatlakare(Privatlakare privatlakare) {
        this.privatlakare = privatlakare;
    }

    public String getVerksamhet() {
        return verksamhet;
    }

    public void setVerksamhet(String verksamhet) {
        this.verksamhet = verksamhet;
    }

    public String getVardform() {
        return vardform;
    }

    public void setVardform(String vardform) {
        this.vardform = vardform;
    }
}
