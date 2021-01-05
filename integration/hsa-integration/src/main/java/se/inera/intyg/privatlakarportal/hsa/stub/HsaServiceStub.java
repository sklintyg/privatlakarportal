/*
 * Copyright (C) 2021 Inera AB (http://www.inera.se)
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
package se.inera.intyg.privatlakarportal.hsa.stub;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import se.inera.intyg.privatlakarportal.hsa.config.HsaStubConfiguration;

@Service
public class HsaServiceStub {

    private static final String HOSP_LAST_UPDATE = "HOSP_LAST_UPDATE";

    // inject the actual template
    @Autowired
    @Qualifier("rediscache")
    private RedisTemplate<Object, Object> redisTemplate;

    // inject the template as ValueOperations
    @Resource(name = "rediscache")
    private ValueOperations<String, HsaHospPerson> valueOps;

    @Resource(name = "rediscache")
    private ValueOperations<String, LocalDateTime> valueOpsLastUpdate;

    public HsaHospPerson getHospPerson(String personId) {
        return valueOps.get(assembleCacheKey(personId));
    }

    public void addHospPerson(HsaHospPerson hospPerson) {
        valueOps.set(assembleCacheKey(hospPerson.getPersonalIdentityNumber()), hospPerson);
        resetHospLastUpdate();
    }

    public void removeHospPerson(String id) {
        valueOps.getOperations().delete(assembleCacheKey(id));
        resetHospLastUpdate();
    }

    public LocalDateTime getHospLastUpdate() {
        return valueOpsLastUpdate.get(HOSP_LAST_UPDATE);
    }

    public void resetHospLastUpdate() {
        valueOpsLastUpdate.set(HOSP_LAST_UPDATE, LocalDateTime.now());
    }

    private String assembleCacheKey(String id) {
        return Stream.of(HsaStubConfiguration.CACHE_NAME, id)
            .collect(Collectors.joining(":"));
    }
}
