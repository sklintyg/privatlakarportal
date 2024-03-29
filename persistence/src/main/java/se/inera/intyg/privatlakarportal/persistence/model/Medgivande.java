/*
 * Copyright (C) 2024 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.inera.intyg.privatlakarportal.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 * Created by pebe on 2015-09-09.
 */
@Entity
@Table(name = "MEDGIVANDE")
public class Medgivande {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "GODKAND_DATUM")
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime godkandDatum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEDGIVANDE_VERSION", nullable = false)
    @JsonBackReference(value = "medgivandeText")
    private MedgivandeText medgivandeText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRIVATLAKARE_ID", nullable = false)
    @JsonBackReference
    private Privatlakare privatlakare;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medgivande)) {
            return false;
        }

        Medgivande that = (Medgivande) o;

        if (id == null) {
            return false;
        } else {
            return id.equals(that.id);
        }
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getGodkandDatum() {
        return godkandDatum;
    }

    public void setGodkandDatum(LocalDateTime godkandDatum) {
        this.godkandDatum = godkandDatum;
    }

    public MedgivandeText getMedgivandeText() {
        return medgivandeText;
    }

    public void setMedgivandeText(MedgivandeText medgivandeText) {
        this.medgivandeText = medgivandeText;
    }

    public Privatlakare getPrivatlakare() {
        return privatlakare;
    }

    public void setPrivatlakare(Privatlakare privatlakare) {
        this.privatlakare = privatlakare;
    }
}
