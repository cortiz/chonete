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

import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.ParameterDescription;
import org.springframework.shell.ParameterResolver;
import org.springframework.shell.ValueResult;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Asks for user input.
 */
@Component
public class UserInputParameterResolver implements ParameterResolver {




    @Override
    public boolean supports(final MethodParameter parameter) {
        return parameter.getParameterAnnotation(UserInput.class) != null;
    }

    @Override
    public ValueResult resolve(final MethodParameter methodParameter, final List<String> words) {
        String userInput;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));
        try {
            userInput = reader.readLine();
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
        return new ValueResult(methodParameter, userInput);
    }

    @Override
    public Stream<ParameterDescription> describe(final MethodParameter parameter) {
        return Stream.of(new ParameterDescription(parameter, "User input"));
    }

    @Override
    public List<CompletionProposal> complete(final MethodParameter parameter, final CompletionContext context) {
        return Collections.emptyList();
    }

}
