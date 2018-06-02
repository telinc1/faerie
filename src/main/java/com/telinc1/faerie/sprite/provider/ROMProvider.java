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
import com.telinc1.faerie.util.locale.Warning;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A {@code ROMProvider} provides all of the sprites from a Super Mario World
 * ROM image.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class ROMProvider extends Provider {
    /**
     * The name of every possible sprite.
     */
    public static final List<String> NAMES = new ArrayList<>();

    /**
     * The bytes of the SNES header title of a Super Mario World ROM image.
     */
    private static final byte[] ROM_TITLE = "SUPER MARIOWORLD     ".getBytes(StandardCharsets.US_ASCII);

    /**
     * The input file to this {@code ROMProvider}.
     */
    private File input;

    /**
     * Constructs a {@code ROMProvider} for the given ROM file.
     *
     * @param input the input file to the provider
     * @throws LoadingException if the file is unreadable or malformed
     * @throws NullPointerException if the file is {@code null}
     */
    public ROMProvider(File input) throws LoadingException{
        super();
        this.input = input;

        if(this.getInput() == null){
            throw new NullPointerException("A ROM image file must be provided.");
        }

        try(RandomAccessFile rom = new RandomAccessFile(this.getInput(), "r")) {
            long offset = rom.length() & 0x200;
            byte[] title = new byte[21];
            rom.seek(0x7FC0 + offset);
            rom.read(title);

            if(!Arrays.equals(ROMProvider.ROM_TITLE, title)){
                throw new LoadingException("Wrong ROM title.", "rom.title", "found", new String(title, StandardCharsets.US_ASCII));
            }
        }catch(IOException exception){
            throw new LoadingException("Error reading the ROM file.", "rom.read", exception);
        }
    }

    @Override
    public File getInput(){
        return this.input;
    }

    @Override
    public void save(File file) throws SavingException{

    }

    @Override
    public String[] getAvailableSprites(){
        return ROMProvider.NAMES.toArray(new String[0]);
    }

    @Override
    public void loadSprite(int index) throws ProvisionException, IndexOutOfBoundsException{
        throw new ProvisionException();
    }

    @Override
    public Warning[] getWarnings(){
        return new Warning[0];
    }

    @Override
    public int getLoadedIndex(){
        return 0;
    }

    @Override
    public Sprite getCurrentSprite(){
        return null;
    }

    @Override
    public Sprite startModification(){
        return null;
    }

    @Override
    public boolean isModified(){
        return false;
    }
}
