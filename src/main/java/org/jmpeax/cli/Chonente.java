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
package org.jmpeax.cli;


import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;

/**
 * Class entry point.
 */
//CHECKSTYLE:OFF
@SpringBootApplication
public class Chonente {
//CHECKSTYLE:ON
    /**
     * Default hidden Ctr.
     */
    public Chonente() {
        //do not init chonente.
    }

    /**
     * Main entry point.
     * @param args Args to use for startup.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Chonente.class, args);
    }


    /**
     * Make.
     * @return prompt.
     */
    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("chonete>", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}
