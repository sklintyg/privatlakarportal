package se.inera.privatlakarportal.persistence.model;

import javax.persistence.*;

/**
 * Created by pebe on 2015-06-24.
 */
@Entity
@Table(name = "BEFATTNING")
public class Befattning {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSONID", nullable = false)
    private Privatlakare privatlakare;

    @Column(name = "KOD", nullable = false)
    private String kod;

    @Column(name = "NAMN", nullable = false)
    private String namn;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            Befattning other = (Befattning) o;

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

    public Befattning() {
    }

    public Befattning(Privatlakare privatlakare, String kod, String namn) {
        this.privatlakare = privatlakare;
        this.kod = kod;
        this.namn = namn;
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

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }
}
