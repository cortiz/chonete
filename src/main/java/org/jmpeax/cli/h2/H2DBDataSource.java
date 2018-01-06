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

import com.zaxxer.hikari.HikariDataSource;
import org.jmpeax.cli.api.providers.DataSource;

/**
 * H2 Data source implementation.
 */
public class H2DBDataSource implements DataSource {

    /**
     * SQL datasource.
     */
    protected final HikariDataSource dataSource;

    /**
     * Creates H2DataSource.
     * @param sqlDataSource SQL Datasource to get the connections.
     */
    H2DBDataSource(final HikariDataSource sqlDataSource) {
        this.dataSource = sqlDataSource;
    }


    @Override
    public String exec(final String command) {
        return "";
    }

    @Override
    public void close() {
        if (!this.dataSource.isClosed()) {
            this.dataSource.close();
        }
    }
}
