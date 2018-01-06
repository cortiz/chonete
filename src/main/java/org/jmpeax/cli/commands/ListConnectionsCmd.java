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
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Map;
import java.util.Set;

/**
 * List all current connections.
 */
@ShellComponent()
public class ListConnectionsCmd {

    /**
     * Connection Registry.
     */
    private Map<String, DataSource> connRegistry;

    /**
     * Creates a  {@link ListConnectionsCmd} instance.
     * @param connectionRegistry Connection Registry.
     */
    public ListConnectionsCmd(final Map<String, DataSource> connectionRegistry) {
        this.connRegistry = connectionRegistry;
    }

    /**
     * List all Open Connections.
     * @return A list of open Connections names.
     */
    @ShellMethod(group = "DB connections", value = "List all open Connections")
    public Set<String> listConn() {
        return connRegistry.keySet();
    }

}
