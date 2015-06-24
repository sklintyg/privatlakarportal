package se.inera.privatlakarportal.persistence.model;

import javax.persistence.*;

/**
 * Created by pebe on 2015-06-24.
 */
@Entity
@Table(name = "LEGITIMERAD_YRKESGRUPP")
public class LegitimeradYrkesgrupp {

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
            LegitimeradYrkesgrupp other = (LegitimeradYrkesgrupp) o;

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

    public LegitimeradYrkesgrupp() {
    }

    public LegitimeradYrkesgrupp(Privatlakare privatlakare, String namn, String kod) {
        this.privatlakare = privatlakare;
        this.namn = namn;
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

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }
}
