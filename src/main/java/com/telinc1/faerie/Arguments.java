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

import com.telinc1.faerie.cli.CommandLineInterface;
import com.telinc1.faerie.gui.GraphicalInterface;
import com.telinc1.faerie.preferences.FlatFileStore;
import com.telinc1.faerie.preferences.NullStore;
import com.telinc1.faerie.preferences.PreferenceStore;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
     * Stores the value of the {@code help} command line option.
     */
    private boolean printHelp;

    /**
     * Stores the value of the {@code headless} command line option.
     */
    private boolean headless;

    /**
     * Stores the value of the {@code cold} command line option.
     */
    private boolean cold;

    /**
     * Stores the value of the {@code verbose} command line option.
     */
    private boolean verbose;

    /**
     * Store the integer argument of the {@code sprite} command line option.
     */
    private int sprite;

    /**
     * Stores all of the trailing arguments from the command line options.
     */
    private String[] trailing;

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
        this.options.addOption("c", "cold", false, "disable preference storage");
        this.options.addOption("v", "verbose", false, "report all exceptions");
        this.options.addOption("s", "sprite", true, "load the sprite at an index");
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
        this.cold = line.hasOption("cold");
        this.verbose = line.hasOption("verbose");
        this.sprite = this.getArgument(line, "sprite", 0);

        this.trailing = line.getArgs();
    }

    /**
     * Prints the help message for the application.
     */
    public void printFormattedHelp(){
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("faerie", this.options);
    }

    /**
     * Creates an appropriate {@code PreferenceStore} for an
     * {@code Application} according to the given arguments.
     */
    public PreferenceStore createPreferenceStore(Application application){
        return this.isCold() ? new NullStore(application) : new FlatFileStore(application);
    }

    /**
     * Creates an appropriate {@code UserInterface} for an
     * {@code Application} according to the given arguments.
     */
    public UserInterface createUserInterface(Application application){
        return this.isHeadless() ? new CommandLineInterface(application) : new GraphicalInterface(application);
    }

    /**
     * Returns the input arguments for the parser.
     */
    public String[] getInput(){
        return this.input;
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

    /**
     * Returns whether the application should run in cold mode, i.e. disable
     * preference storage.
     */
    public boolean isCold(){
        return this.cold;
    }

    /**
     * Returns whether the application should save full error reports for all
     * exceptions.
     */
    public boolean isVerbose(){
        return this.verbose;
    }

    /**
     * Returns the index of the sprite which should be loaded after the user
     * interface is initialized.
     */
    public int getSprite(){
        return this.sprite;
    }

    /**
     * Returns an array of all unrecognized trailing arguments.
     */
    public String[] getTrailing(){
        return this.trailing;
    }

    /**
     * Extracts an integer argument from a parsed set of {@code CommandLine}
     * arguments and returns a fallback if the argument doesn't exist or isn't
     * a valid integer.
     */
    private int getArgument(CommandLine line, String option, int fallback){
        String argument = line.getOptionValue(option);

        if(argument == null){
            return fallback;
        }

        try {
            return Integer.valueOf(argument);
        }catch(NumberFormatException exception){
            return fallback;
        }
    }
}
