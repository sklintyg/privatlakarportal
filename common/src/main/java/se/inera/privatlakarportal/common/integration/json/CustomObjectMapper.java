/**
 * Copyright (C) 2016 Inera AB (http://www.inera.se)
 *
 * This file is part of privatlakarportal (https://github.com/sklintyg/privatlakarportal).
 *
 * privatlakarportal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * privatlakarportal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.inera.privatlakarportal.common.integration.json;

import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.LocalDateTimeSerializer;

public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        setSerializationInclusion(JsonInclude.Include.ALWAYS);
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        registerModule(new Module());
    }

    private static final class Module extends SimpleModule {
        private Module() {
            addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
            addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        }

    }
}
