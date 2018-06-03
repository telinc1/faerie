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

import com.telinc1.faerie.sprite.EnumSpriteSubType;
import com.telinc1.faerie.sprite.EnumSpriteType;
import com.telinc1.faerie.sprite.Sprite;
import com.telinc1.faerie.util.TypeUtils;
import com.telinc1.faerie.util.locale.Warning;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     * The index of the sprite which is currently loaded.
     */
    private int index;

    /**
     * An array of all currently loaded sprites.
     */
    private Sprite[] sprites;

    /**
     * A {@code Set} of the sprites which have been modified.
     */
    private Set<Sprite> modified;

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
        this.sprites = new Sprite[256];
        this.modified = new HashSet<>();

        if(this.getInput() == null){
            throw new NullPointerException("A ROM image file must be provided.");
        }

        if(!TypeUtils.isROM(this.getInput())){
            throw new LoadingException("Unknown ROM image type.", "rom.type");
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
    public Provider save(File file) throws SavingException{
        if(TypeUtils.isConfiguration(file)){
            Sprite sprite = this.getCurrentSprite();
            this.modified.remove(sprite);

            ConfigurationProvider provider = new ConfigurationProvider(file);
            provider.setSprite(sprite);
            provider.save(file);

            return provider;
        }

        if(this.getInput() != file){
            throw new SavingException("Different target file.", "rom.different");
        }

        if(!file.exists()){
            throw new SavingException("The file doesn't exist.", "rom.write");
        }

        try(RandomAccessFile rom = new RandomAccessFile(file, "rw")) {
            long offset = rom.length() & 0x200;
            byte[] title = new byte[21];
            rom.seek(0x7FC0 + offset);
            rom.read(title);

            if(!Arrays.equals(ROMProvider.ROM_TITLE, title)){
                throw new SavingException("Wrong ROM title.", "rom.title", "found", new String(title, StandardCharsets.US_ASCII));
            }

            for(Sprite sprite : this.modified){
                if(!sprite.hasBehavior()){
                    continue;
                }

                int index = sprite.getActsLike();
                int[] behavior = sprite.getBehavior().pack();

                this.writeByte(rom, 0x3F26C + index, behavior[0]);
                this.writeByte(rom, 0x3F335 + index, behavior[1]);
                this.writeByte(rom, 0x3F3FE + index, behavior[2]);
                this.writeByte(rom, 0x3F4C7 + index, behavior[3]);
                this.writeByte(rom, 0x3F590 + index, behavior[4]);
                this.writeByte(rom, 0x3F659 + index, behavior[5]);
            }
        }catch(IOException exception){
            throw new SavingException("Error reading the file.", "rom.write", exception);
        }

        this.input = file;
        this.modified.clear();
        return null;
    }

    @Override
    public File getInput(){
        return this.input;
    }

    @Override
    public void loadSprite(int index) throws ProvisionException{
        if(index < 0 || index > this.sprites.length){
            throw new ProvisionException("Index out of bounds: " + index + ".", "index");
        }

        if(this.sprites[index] != null){
            this.index = index;
            return;
        }

        Sprite sprite = new Sprite();
        sprite.setType(EnumSpriteType.TWEAK);
        sprite.setActsLike(index);

        if(index <= 0xC8){
            sprite.setSubtype(EnumSpriteSubType.REGULAR);

            try(RandomAccessFile rom = new RandomAccessFile(this.getInput(), "r")) {
                sprite.getBehavior().unpack(new int[]{
                    this.readByte(rom, 0x3F26C + index),
                    this.readByte(rom, 0x3F335 + index),
                    this.readByte(rom, 0x3F3FE + index),
                    this.readByte(rom, 0x3F4C7 + index),
                    this.readByte(rom, 0x3F590 + index),
                    this.readByte(rom, 0x3F659 + index)
                });
            }catch(IOException exception){
                throw new ProvisionException("Error reading the file.", "rom.io", exception);
            }
        }else if(index == 0xC9 || index == 0xCA){
            sprite.setSubtype(EnumSpriteSubType.SHOOTER);
        }else if(index <= 0xD9){
            sprite.setSubtype(EnumSpriteSubType.GENERATOR);
        }else if(index <= 0xE6){
            sprite.setSubtype(EnumSpriteSubType.INITIALIZER);
        }else{
            sprite.setSubtype(EnumSpriteSubType.SCROLLER);
        }

        this.index = index;
        this.sprites[index] = sprite;
    }

    @Override
    public String[] getAvailableSprites(){
        return ROMProvider.NAMES.toArray(new String[0]);
    }

    /**
     * Reads an unsigned byte at the given PC offset in a readable ROM image.
     *
     * @param rom the opened readable ROM image to read from
     * @param pc the unheadered PC offset to read, which will automatically be
     * adjusted if a header is found
     * @return the unsigned byte at the given offset
     * @throws IOException if reading from the file fails
     */
    private int readByte(RandomAccessFile rom, long pc) throws IOException{
        rom.seek(pc + (rom.length() & 0x200));
        return rom.readUnsignedByte();
    }

    /**
     * Writes an unsigned byte to the given PC offset of a readable ROM image.
     *
     * @param rom the opened readable ROM image to read from
     * @param pc the unheadered PC offset to read, which will automatically be
     * adjusted if a header is found
     * @param data the byte to write
     * @throws IOException if writing to the file fails
     */
    private void writeByte(RandomAccessFile rom, long pc, int data) throws IOException{
        rom.seek(pc + (rom.length() & 0x200));
        rom.writeByte(data & 0xFF);
    }

    @Override
    public Warning[] getWarnings(){
        return new Warning[0];
    }

    @Override
    public int getLoadedIndex(){
        return this.index;
    }

    @Override
    public Sprite getCurrentSprite(){
        return this.sprites[this.getLoadedIndex()];
    }

    @Override
    public Sprite startModification(){
        Sprite sprite = this.getCurrentSprite();
        this.modified.add(sprite);

        return sprite;
    }

    @Override
    public boolean isModified(){
        return !this.modified.isEmpty();
    }
}
