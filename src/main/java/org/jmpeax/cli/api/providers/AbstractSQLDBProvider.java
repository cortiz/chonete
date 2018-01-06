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
package org.jmpeax.cli.api.providers;

import com.zaxxer.hikari.HikariDataSource;
import org.jmpeax.cli.api.exceptions.RuntimeSQLException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

/**
 * Partial implementation of {@link DBProvider} for SQL based Providers.
 */
public abstract class AbstractSQLDBProvider implements DBProvider {


    /**
     * Creates a {@link javax.sql.DataSource} for this provider.
     *
     * @param connectionString Connection String to initialize the data source.
     * @param username         Datasource username.
     * @param password         Datasource password.
     * @param driverClassName  DB  Driver Class Name.
     * @param options          Options for the Datasource
     * @return A Initialized {@link DataSource}.
     */
    protected HikariDataSource createSQLDataSource(final String connectionString, final String username,
                                                   final String password, final String driverClassName,
                                                   final Map<String, Object> options) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(connectionString);
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        final Iterator<Map.Entry<String, Object>> entryIter = options.entrySet().iterator();
        while (entryIter.hasNext()) {
            final Map.Entry<String, Object> entry = entryIter.next();
            hikariDataSource.addDataSourceProperty(entry.getKey(), entry.getValue());
        }
        try (Connection connection = hikariDataSource.getConnection()) {
            // Empty, just a test that we can connect to the server.
            connection.clearWarnings();
        } catch (SQLException ex) {
            throw new RuntimeSQLException(ex);
        }
        return hikariDataSource;
    }
}
