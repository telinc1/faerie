/*
 * Copyright (c) 2018 Telinc1
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.telinc1.faerie.sprite.parser;

import com.telinc1.faerie.sprite.Sprite;
import com.telinc1.faerie.util.Warning;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses a sprite configuration file into an actual sprite.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public abstract class Parser {
    /**
     * The input to the parser.
     */
    private Reader input;

    /**
     * The list of warnings created during the parsing.
     */
    private List<Warning> warnings;

    /**
     * Constructs a parser.
     *
     * @param input the input to the parser
     */
    public Parser(Reader input){
        this.input = input;
        this.warnings = new ArrayList<>();
    }

    /**
     * Returns the input to the parser.
     *
     * @return the input to the parser
     */
    public Reader getInput(){
        return this.input;
    }

    /**
     * Returns the list of warnings created during the parsing.
     *
     * @return any {@link List} of arbitrary {@link Warning} objects
     */
    public List<Warning> getWarnings(){
        return this.warnings;
    }

    /**
     * Parses the input into a complete sprite.
     *
     * @return the parsed sprite
     * @throws ParseException if the data is malformed in any way
     */
    public abstract Sprite parse() throws ParseException;
}
