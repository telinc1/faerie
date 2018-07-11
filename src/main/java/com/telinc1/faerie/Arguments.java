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

package com.telinc1.faerie;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * The {@code Arguments} class uses Apache's Commons CLI library to manage and
 * parse command line arguments and flags given to the application.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class Arguments {
    /**
     * The input arguments for the parser.
     */
    private final String[] input;

    /**
     * The {@code Options} object which stores the application's options.
     */
    private final Options options;

    /**
     * Stores the value of the "help" command line option.
     */
    private boolean printHelp;

    /**
     * Stores the value of the "headless" command line option.
     */
    private boolean headless;

    /**
     * Constructs an {@code Arguments} store and creates the default options.
     *
     * @param input the input to the {@code Arguments} parser
     */
    public Arguments(String[] input){
        this.input = input;

        this.options = new Options();
        this.options.addOption("h", "help", false, "display this message");
        this.options.addOption("H", "headless", false, "disable the graphical interface and operate on a single file");
    }

    /**
     * Parses the given {@code args} using the pre-declared options.
     *
     * @throws ParseException if any problems are encountered during parsing
     */
    public void parse() throws ParseException{
        CommandLineParser parser = new DefaultParser();
        CommandLine line;

        try {
            line = parser.parse(this.options, this.getInput());
        }catch(ParseException exception){
            this.printHelp = true;
            this.headless = true;

            throw exception;
        }

        this.printHelp = line.hasOption("help");
        this.headless = line.hasOption("headless") || this.printHelp;
    }

    /**
     * Returns the input arguments for the parser.
     */
    public String[] getInput(){
        return this.input;
    }

    /**
     * Returns the help message for the application.
     */
    public String getHelp(){
        HelpFormatter helpFormatter = new HelpFormatter();

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        helpFormatter.printHelp(printWriter, helpFormatter.getWidth(), "faerie", null, this.options, 1, 3, null);

        return stringWriter.toString();
    }

    /**
     * Returns whether the help message should be printed.
     */
    public boolean shouldPrintHelp(){
        return this.printHelp;
    }

    /**
     * Returns whether the application should be in headless mode.
     */
    public boolean isHeadless(){
        return this.headless;
    }
}
