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
package org.jmpeax.cli.api.events;

import org.springframework.context.ApplicationEvent;

/**
 * Connection change Event.
 */
public class ConnectionChange extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ConnectionChange(final Object source) {
        super(source);
    }

    /**
     * Gets the current Connection Name.
     * @return current connection Name.
     */
    public String getConnectionName() {
        return super.source.toString();
    }
}
