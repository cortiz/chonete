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
package org.jmpeax.cli.ext;


import org.apache.commons.lang3.StringUtils;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.jmpeax.cli.api.events.ConnectionChange;
import org.springframework.context.event.EventListener;
import org.springframework.shell.jline.PromptProvider;

/**
 * Chonete Prompt Provider Customizer.
 */
public class ChonetePromptProvider implements PromptProvider {

    /**
     * Current Connection name.
     */
    private String currentConnection;

    @Override
    public AttributedString getPrompt() {
        if (StringUtils.isEmpty(currentConnection)) {
            return new AttributedString("chonete>", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
        } else {
            return new AttributedString(currentConnection + ">",
                    AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
        }
    }

    /**
     * Listends and reacts to a connection Change.
     * @param connectionName connection Name.
     */
    @EventListener
    public void handle(final ConnectionChange connectionName) {
        this.currentConnection = connectionName.getConnectionName();
    }
}
