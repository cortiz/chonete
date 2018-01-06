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


import java.util.Map;

/**
 * Describes the Provider information.
 */
public interface DBProvider {


    /**
     * Gets the name of the DB provider.
     *
     * @return Human readable DB provider.
     */
    String getName();

    /**
     * Gets the key of the DB provider.
     * This key is used to match the provider to a JDBC url.
     *
     * @return Key of the DB provider.
     */
    String getKey();

    /**
     * Creates a {@link DataSource} for this provider.
     *
     * @param connectionString Connection String to initialize the data source.
     * @param username         Datasource username.
     * @param password         Datasource password.
     * @param options          Options for the Datasource
     * @return A Initialized {@link DataSource}.
     */
    DataSource createDataSource(String connectionString, String username, String password, Map<String, Object> options);
}
