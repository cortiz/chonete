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


import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;

/**
 * Chonete User Input Prompt Provider.
 */
public class ChoneteUserInputPromptProvider implements PromptProvider {

    /**
     * Parameter Name.
     */
    private String paramName;

    /**
     * Creates a Chonete User input Prompt for the given parameter.
     * @param parameterName Name of the parameter to ask the user for.
     */
    public ChoneteUserInputPromptProvider(final String parameterName) {
        this.paramName = parameterName;
    }

    @Override
    public AttributedString getPrompt() {
        return new AttributedString("\t" + paramName + ">");
    }
}
