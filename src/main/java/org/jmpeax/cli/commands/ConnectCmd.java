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
package org.jmpeax.cli.commands;

import org.jmpeax.cli.api.events.ConnectionChange;
import org.jmpeax.cli.api.providers.DBProvider;
import org.jmpeax.cli.api.providers.DataSource;
import org.jmpeax.cli.ext.UserInput;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Connect to a Database.
 */
@ShellComponent()
public class ConnectCmd {


    /**
     * Defines a Unknown DB provider.
     */
    private static final String UNKNOWN = "unknown";
    /**
     * Position of the db provider in the JDBC url.
     */
    private static final int DB_JDBC_POSITION = 1;
    /**
     * Default connection name.
     */
    public static final String DEFAULT_CONN = "default";

    /**
     * List of register providers.
     */
    private Set<DBProvider> registerProviderList;

    /**
     * Connection Registry.
     */
    private Map<String, DataSource> connRegistry;

    /**
     * Event Publisher.
     */
    private ApplicationEventPublisher eventPublisher;


    /**
     * Creates the ConnectCmd instantce.
     *
     * @param providerList List of Providers to use.
     * @param connectionRegistry Connection Registry.
     * @param applicationEventPublisher {@link ApplicationEventPublisher}
     */
    public ConnectCmd(final Set<DBProvider> providerList,
                      final Map<String, DataSource> connectionRegistry,
                      final ApplicationEventPublisher applicationEventPublisher) {
        this.registerProviderList = providerList;
        this.connRegistry = connectionRegistry;
        this.eventPublisher = applicationEventPublisher;
    }

    /**
     * Connects and creates the JDBC.
     *
     * @param jdbcurl        Url to connect.
     * @param username       DB username
     * @param password       DB password.
     * @param connectionName name of the connection, defaults to 'default'"
     */
    @ShellMethod(group = "DB connections", value = "Connects to the given JDBC URL")
    @SuppressWarnings("unused")//Dynamic call by Spring-Shell
    public void connect(final String jdbcurl,
                        @ShellOption(defaultValue = DEFAULT_CONN,
                                help = "Name of the connection") final String connectionName,
                        @UserInput final String username,
                        @UserInput final String password
                       ) {
        final String givenProvider = extractDBProvider(jdbcurl);
        DBProvider provider = registerProviderList.stream().filter(dbProvider ->
                givenProvider.equals(dbProvider.getKey()))
                .findAny()
                .orElse(null);
        if (provider != null) {
            createConnection(jdbcurl, username,
                    password, provider, connectionName);
            eventPublisher.publishEvent(new ConnectionChange(connectionName));
        } else {
            throw new IllegalArgumentException("There are not register providers for " + givenProvider);
        }
    }

    /**
     * Given the jdbcUrl gets the DB provider.
     *
     * @param jdbcUrl URL to get which provider use.
     * @return the Provider to be use.
     */
    protected String extractDBProvider(final String jdbcUrl) {
        final String[] spitedUrl = jdbcUrl.split(":");
        if (spitedUrl.length > DB_JDBC_POSITION) {
            return spitedUrl[DB_JDBC_POSITION];
        } else {
            return UNKNOWN;
        }
    }

    /**
     * Create a DB connection.
     *
     * @param jdbcurl  JDBC to use.
     * @param username DB username.
     * @param password DB password.
     * @param provider Provider to Initialize connection.
     * @param connectionName Connection Name.
     */
    private void createConnection(final String jdbcurl,
                                  final String username,
                                  final String password,
                                  final DBProvider provider,
                                  final String connectionName) {

        if (connRegistry.containsKey(connectionName)) {
            connRegistry.get(connectionName).close();
            connRegistry.remove(connectionName);
        }

        final DataSource ds = provider.createDataSource(jdbcurl, username, password, Collections.emptyMap());
        connRegistry.put(connectionName, ds);
    }


}
