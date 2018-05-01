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
        super(input);
        this.isModified = false;

        try(FileReader reader = new FileReader(input)) {
            String extension = TypeUtils.getExtension(this.getInput());

            if("cfg".equalsIgnoreCase(extension)){
                this.parser = new CFGParser(reader);
            }

            if(this.getParser() == null){
                throw new LoadingException("The type of the configuration file could not be determined.");
            }

            this.sprite = this.getParser().parse();
        }catch(IOException exception){
            throw new LoadingException("The configuration file could not be read.", exception);
        }catch(ParseException exception){
            throw new LoadingException("The configuration file is malformed.", exception);
        }
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
    }
}
