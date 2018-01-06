/**
 * Chonete
 * Copyright (C) 2018 Carlos Ortiz.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jmpeax.cli.h2;

import org.jmpeax.cli.api.providers.AbstractSQLDBProvider;
import org.jmpeax.cli.api.providers.DataSource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Implements DB provider.
 */
@Component("H2DBProvider")
public class H2DBProvider extends AbstractSQLDBProvider {

    /**
     * DB key.
     */
    private static final String DB_KEY = "h2";
    /**
     * DB name.
     */
    private static final String DB_NAME = "H2";
    /**
     * H2 DB driver name.
     */
    private static final String DB_DRIVER_NAME = "org.h2.Driver";

    @Override
    public String getName() {
        return DB_NAME;
    }

    @Override
    public String getKey() {
        return DB_KEY;
    }

    @Override
    public DataSource createDataSource(final String connectionString, final String username,
                                       final String password, final Map<String, Object> options) {
        return new H2DBDataSource(createSQLDataSource(connectionString, username, password,
                DB_DRIVER_NAME, options));
    }
}
