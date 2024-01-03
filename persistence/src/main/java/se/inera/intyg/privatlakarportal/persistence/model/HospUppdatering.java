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

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 * Created by pebe on 2015-09-03.
 */
@Entity
@Table(name = "HOSP_UPPDATERING")
public class HospUppdatering {

    @SuppressWarnings("unused")
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SENASTE_HOSP_UPPDATERING", nullable = true)
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime senasteHospUppdatering;

    public HospUppdatering() {
        id = 1L;
    }

    public HospUppdatering(LocalDateTime senasteHospUppdatering) {
        id = 1L;
        this.senasteHospUppdatering = senasteHospUppdatering;
    }

    public LocalDateTime getSenasteHospUppdatering() {
        return senasteHospUppdatering;
    }

    public void setSenasteHospUppdatering(LocalDateTime senasteHospUppdatering) {
        this.senasteHospUppdatering = senasteHospUppdatering;
    }
}
