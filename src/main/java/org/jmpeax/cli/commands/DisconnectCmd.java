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

import org.jmpeax.cli.api.providers.DataSource;
import org.jmpeax.cli.api.events.ConnectionChange;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Map;

/**
 * Disconnect from a DataSoruce and release it's resources.
 */
@ShellComponent()
public class DisconnectCmd {

    /**
     * Connection Registry.
     */
    private Map<String, DataSource> connRegistry;

    /**
     * Event Publisher.
     */
    private ApplicationEventPublisher eventPublisher;

    /**
     * Creates the DisconnectCmd instantce.
     *
     * @param connectionRegistry Connection Registry.
     * @param applicationEventPublisher {@link ApplicationEventPublisher}
     **/
    public DisconnectCmd(final Map<String, DataSource> connectionRegistry,
                         final ApplicationEventPublisher applicationEventPublisher) {
        this.connRegistry = connectionRegistry;
        this.eventPublisher = applicationEventPublisher;
    }

    /**
     * Disconnects from the current Datasource.
     * @param connectionName connection to be terminate
     */
    @ShellMethod(group = "DB connections",
            value = "Dissconects of the given connection")
    public void disconnect(@ShellOption(defaultValue = "default") final String connectionName) {
        if (connRegistry != null && connRegistry.containsKey(connectionName)) {
            connRegistry.get(connectionName).close();
            connRegistry.remove(connectionName);
            eventPublisher.publishEvent(new ConnectionChange(""));
        } else {
            throw new IllegalArgumentException("No connection with name " + connectionName);
        }
    }


}
