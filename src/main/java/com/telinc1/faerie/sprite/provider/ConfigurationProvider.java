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

package com.telinc1.faerie.sprite.provider;

import com.telinc1.faerie.sprite.Sprite;
import com.telinc1.faerie.sprite.emitter.CFGEmitter;
import com.telinc1.faerie.sprite.emitter.Emitter;
import com.telinc1.faerie.sprite.parser.CFGParser;
import com.telinc1.faerie.sprite.parser.ParseException;
import com.telinc1.faerie.sprite.parser.Parser;
import com.telinc1.faerie.util.TypeUtils;
import com.telinc1.faerie.util.locale.Warning;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A {@code ConfigurationProvider} provides a single sprite from any format of
 * configuration file.
 *
 * @see com.telinc1.faerie.sprite.parser.Parser
 */
public class ConfigurationProvider extends Provider {
    /**
     * The {@code Parser} which was used to parse the configuration file.
     */
    private Parser parser;

    /**
     * The sprite which was parsed from the configuration file.
     */
    private Sprite sprite;

    /**
     * The input file to this {@code Provider}.
     */
    private File input;

    /**
     * Stores whether the loaded sprite has been touched.
     */
    private boolean isModified;

    /**
     * Constructs a {@code ConfigurationProvider} for the given {@code File}.
     *
     * @param input the input file to the provider
     * @throws LoadingException if the file is unreadable or malformed
     */
    public ConfigurationProvider(File input) throws LoadingException{
        super();
        this.input = input;
        this.isModified = false;

        if(this.getInput() == null){
            this.sprite = new Sprite();
            return;
        }

        try(FileReader reader = new FileReader(input)) {
            String extension = TypeUtils.getExtension(this.getInput());

            if("cfg".equalsIgnoreCase(extension)){
                this.parser = new CFGParser(reader);
            }

            if(this.getParser() == null){
                throw new LoadingException("Unknown configuration file type.", "configuration.unknown");
            }

            this.sprite = this.getParser().parse();
        }catch(IOException exception){
            throw new LoadingException("Error reading configuration file.", "configuration.io", exception);
        }catch(ParseException exception){
            throw new LoadingException("Malformed configuration file.", "configuration.malformed", exception, "message", exception.getLocalizedMessage());
        }
    }

    @Override
    public File getInput(){
        return this.input;
    }

    /**
     * Returns the {@code Parser} which was used to parse the configuration file.
     *
     * @return the parser used
     */
    public Parser getParser(){
        return this.parser;
    }

    @Override
    public String[] getAvailableSprites(){
        if(this.getCurrentSprite().getDisplayData() != null){
            return new String[]{this.getCurrentSprite().getDisplayData().getName()};
        }

        return new String[]{"Sprite"};
    }

    @Override
    public void loadSprite(int index) throws ProvisionException, IndexOutOfBoundsException{
        if(index != 0){
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void save(File file) throws SavingException{
        String extension = TypeUtils.getExtension(file);
        Emitter emitter = null;

        if("cfg".equalsIgnoreCase(extension)){
            emitter = new CFGEmitter(this.getCurrentSprite());
        }

        if(emitter == null){
            throw new SavingException("The type of the configuration file isn't supported.");
        }

        try {
            file.createNewFile();
        }catch(IOException | SecurityException exception){
            throw new SavingException("The file couldn't be created.", exception);
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            emitter.emit(writer);
        }catch(IOException | SecurityException exception){
            throw new SavingException("The configuration file couldn't written to.", exception);
        }

        this.input = file;
        this.isModified = false;
    }

    @Override
    public int getLoadedIndex(){
        return 0;
    }

    @Override
    public Sprite getCurrentSprite(){
        return this.sprite;
    }

    @Override
    public Sprite startModification(){
        this.isModified = true;
        return this.getCurrentSprite();
    }

    /**
     * Returns any warnings created during the provision.
     *
     * @return an array of {@link Warning}s which were created
     */
    @Override
    public Warning[] getWarnings(){
        if(this.getParser() == null){
            return new Warning[0];
        }

        return this.getParser().getWarnings().toArray(new Warning[0]);
    }

    @Override
    public boolean isModified(){
        return this.isModified;
    }
}
