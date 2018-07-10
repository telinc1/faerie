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

package com.telinc1.faerie.display;

import com.telinc1.faerie.util.TypeUtils;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class represents a loaded palette file.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class Palette {
    /**
     * The colors in this palette.
     */
    private int[] colors;

    /**
     * Constructs an empty (pure black) color palette.
     */
    public Palette(){
        this.colors = new int[512];
    }

    /**
     * Returns the RGB color at the given index.
     *
     * @param index the index to get
     * @return the RGB {@link Color}
     * @throws ArrayIndexOutOfBoundsException if the index is outside the palette
     */
    public Color getColor(int index){
        int color = this.getSNESColor(index);
        return new Color(((color << 19) & 0xF80000) | ((color << 6) & 0xF800) | ((color >> 7) & 0xF8));
    }

    /**
     * Returns the SNES color at the given index.
     *
     * @param index the index to get
     * @return the 5-bit SNES RGB color
     * @throws ArrayIndexOutOfBoundsException if the index is outside the palette
     */
    public int getSNESColor(int index){
        return this.colors[index * 2];
    }

    /**
     * Loads the given PAL file into the internal color array.
     *
     * @param input the data to load
     * @return the palette, for chaining
     * @throws IOException if an I/O error occurs
     */
    public Palette loadPALFile(InputStream input) throws IOException{
        try(BufferedInputStream stream = new BufferedInputStream(input)) {
            byte[] palette = new byte[768];
            int bytes = stream.read(palette);

            if(bytes < 768){
                throw new IOException("The given palette file is too short.");
            }

            for(int i = 0; i < 256; i++){
                this.setColor(i, new Color(
                    palette[i * 3] & 0xFF,
                    palette[i * 3 + 1] & 0xFF,
                    palette[i * 3 + 2] & 0xFF
                ));
            }
        }

        return this;
    }

    /**
     * Sets the RGB color at the given index.
     *
     * @param index the index to set
     * @param color the RGB {@link Color} to set the index to
     * @return the palette, for chaining
     * @throws ArrayIndexOutOfBoundsException if the index is outside the palette
     */
    public Palette setColor(int index, Color color){
        return this.setSNESColor(
            index,
            ((color.getRed() >> 3) & 31)
                | (((color.getGreen() >> 3) & 31) << 5)
                | (((color.getBlue() >> 3) & 31) << 10)
        );
    }

    /**
     * Sets the SNES color at the given index.
     *
     * @param index the index to set
     * @param color the 5-bit SNES RGB color to set the index to
     * @return the palette, for chaining
     * @throws ArrayIndexOutOfBoundsException if the index is outside the palette
     */
    public Palette setSNESColor(int index, int color){
        this.colors[index * 2] = color & 0x7FFF;
        return this;
    }

    /**
     * Loads the given TPL file into the internal color array.
     *
     * @param input the data to load
     * @return the palette, for chaining
     * @throws IOException if an I/O error occurs
     */
    public Palette loadTPLFile(InputStream input) throws IOException{
        try(BufferedInputStream stream = new BufferedInputStream(input)) {
            byte[] header = new byte[4];
            byte[] palette = new byte[512];
            int headerBytes = stream.read(header);
            int paletteBytes = stream.read(palette);

            if(headerBytes < 4 || paletteBytes < 512){
                throw new IOException("The given palette file is too short.");
            }

            if(header[0] != 0x54 || header[1] != 0x50 || header[2] != 0x4C || header[3] != 0x02){
                throw new IOException("The given palette file is malformed.");
            }

            for(int i = 0; i < 256; i++){
                this.setSNESColor(i, (palette[i * 2] & 0xFF) | ((palette[i * 2 + 1] & 0xFF) << 8));
            }
        }

        return this;
    }

    /**
     * Loads the given MW3 file into the internal color array.
     *
     * @param input the data to load
     * @return the palette, for chaining
     * @throws IOException if an I/O error occurs
     */
    public Palette loadMW3File(InputStream input) throws IOException{
        try(BufferedInputStream stream = new BufferedInputStream(input)) {
            byte[] palette = new byte[512];
            int bytes = stream.read(palette);

            if(bytes < 512){
                throw new IOException("The given palette file is too short.");
            }

            for(int i = 0; i < 256; i++){
                this.setSNESColor(i, (palette[i * 2] & 0xFF) | ((palette[i * 2 + 1] & 0xFF) << 8));
            }
        }

        return this;
    }

    /**
     * Attempts to load the given file into the internal color array.
     *
     * @param file the data to load
     * @return the palette, for chaining
     * @throws IOException if an I/O error occurs or the file is invalid
     */
    public Palette loadFile(File file) throws IOException{
        if(!TypeUtils.isPalette(file)){
            throw new IOException("The file is not a palette file.");
        }

        try(FileInputStream stream = new FileInputStream(file)) {
            String extension = TypeUtils.getExtension(file);

            if(extension.equalsIgnoreCase(TypeUtils.TYPE_RGB_PALETTE)){
                return this.loadPALFile(stream);
            }else if(extension.equalsIgnoreCase(TypeUtils.TYPE_TPL_PALETTE)){
                return this.loadTPLFile(stream);
            }else if(extension.equalsIgnoreCase(TypeUtils.TYPE_SNES_PALETTE)){
                return this.loadMW3File(stream);
            }else{
                throw new IOException("The file is not a palette file.");
            }
        }
    }
}
