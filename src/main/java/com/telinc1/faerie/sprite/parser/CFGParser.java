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

import com.telinc1.faerie.sprite.EnumSpriteSubType;
import com.telinc1.faerie.sprite.EnumSpriteType;
import com.telinc1.faerie.sprite.EnumStatusHandling;
import com.telinc1.faerie.sprite.Sprite;
import com.telinc1.faerie.sprite.display.DisplayData;
import com.telinc1.faerie.sprite.display.LabelDisplayData;
import com.telinc1.faerie.sprite.display.SpriteTile;
import com.telinc1.faerie.sprite.display.TileDisplayData;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Parses a CFG configuration file.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class CFGParser extends Parser {
    /**
     * Whether the parsed CFG file was a SpriteTool file.
     */
    private boolean isLegacy;

    /**
     * Constructs a CFG file parser.
     *
     * @param input the input file
     */
    public CFGParser(Reader input){
        super(input);
        this.isLegacy = false;
    }

    /**
     * Returns whether the parsed CFG file was a legacy SpriteTool file.
     *
     * @return whether the file was a legacy file
     */
    public boolean isLegacy(){
        return this.isLegacy;
    }

    @Override
    public Sprite parse() throws ParseException{
        Sprite sprite = new Sprite();
        Scanner scanner = new Scanner(this.getInput());

        boolean hasDisplayData = this.parseConfiguration(sprite, scanner);

        if(!sprite.verify()){
            throw new ParseException("Incomplete sprite data.", "incomplete", null);
        }

        if(hasDisplayData){
            sprite.setDisplayData(this.parseDisplayData(scanner));
        }

        return sprite;
    }

    /**
     * Parses the configuration part of a CFG file.
     * <p>
     * This includes the type, subtype, ASM files, property bytes, and others.
     * <p>
     * This is almost directly ported over from the GIEPY source code, see
     * ({@code src/mewthree/ParseCfg.c}).
     *
     * @param sprite the sprite to parse into
     * @param scanner the input to parse
     * @return whether the input contains display data
     * @throws ParseException if the scanner has malformed data
     */
    private boolean parseConfiguration(Sprite sprite, Scanner scanner) throws ParseException{
        int lines = 0;
        boolean previousEmpty = false;
        boolean hasDisplayData = false;

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            lines += 1;

            if(previousEmpty && line.startsWith("---")){
                lines -= 2;
                hasDisplayData = true;
                break;
            }

            line = this.removeComments(line);
            previousEmpty = line.isEmpty();

            switch(lines){
                case 1:
                    // Line 1: Sprite Type
                    try {
                        sprite.setType(EnumSpriteType.fromInteger(Integer.parseInt(line, 16)));
                    }catch(NumberFormatException exception){
                        throw new ParseException("Invalid sprite type.", "cfg.type", exception, 1);
                    }

                    break;
                case 2:
                    // Line 2: Acts Like
                    try {
                        sprite.setActsLike(Integer.parseInt(line, 16));
                    }catch(NumberFormatException exception){
                        throw new ParseException("Invalid acts like setting.", "cfg.actsLike", exception, 2);
                    }

                    break;
                case 3:
                    // Line 3: Behavior
                    try {
                        sprite.getBehavior().unpack(this.parseIntegers(line, " ", 6));
                    }catch(IllegalArgumentException exception){
                        throw new ParseException("Invalid behavior bytes.", "cfg.behavior", exception, 3);
                    }

                    break;
                case 4:
                    // Line 4: Property Bytes
                    try {
                        int[] properties = this.parseIntegers(line, " ", 2);

                        sprite.setFirstPropertyByte(properties[0]);
                        sprite.setSecondPropertyByte(properties[1]);
                        sprite.setStatusHandling(EnumStatusHandling.fromBits(properties[1] >> 6));
                    }catch(IllegalArgumentException exception){
                        throw new ParseException("Invalid property bytes.", "cfg.properties", exception, 4);
                    }catch(NoSuchElementException exception){
                        throw new ParseException("Unexpected end of input.", "cfg.eof", exception, 4);
                    }

                    break;
                case 5:
                    // Line 5: First ASM File
                    sprite.setFirstASMFile(line);
                    break;
                case 6:
                    // Line 6: Assembler or PIXI extra bytes, ignored
                    break;
                case 7:
                    // Line 7: Sprite Subtype
                    try {
                        sprite.setSubtype(EnumSpriteSubType.fromInteger(Integer.parseInt(line, 16)));
                    }catch(NumberFormatException exception){
                        throw new ParseException("Invalid sprite subtype.", "cfg.subtype", exception, 1);
                    }catch(NoSuchElementException exception){
                        throw new ParseException("Unexpected end of input", "cfg.eof", exception, 1);
                    }

                    break;
                case 8:
                    // Line 8: Unique Byte
                    try {
                        sprite.setUniqueByte(Integer.parseInt(line, 16));
                    }catch(NumberFormatException exception){
                        throw new ParseException("Invalid unique byte.", "cfg.uniqueByte", exception, 1);
                    }catch(NoSuchElementException exception){
                        throw new ParseException("Unexpected end of input.", "cfg.eof", exception, 1);
                    }

                    break;
                case 9:
                    // Line 9: Extra Bytes
                    try {
                        sprite.setExtraBytes(Integer.parseInt(line, 16));
                    }catch(IllegalArgumentException exception){
                        throw new ParseException("Invalid extra byte count.", "cfg.extraBytes", exception, 1);
                    }catch(NoSuchElementException exception){
                        throw new ParseException("Unexpected end of input.", "cfg.eof", exception, 1);
                    }

                    break;
                case 10:
                    // Line 10: Second ASM File
                    sprite.setSecondASMFile(line);
                    break;
                default:
                    break;
            }
        }

        if(lines < 5){
            throw new ParseException("Incomplete configuration file.", "cfg.tooFew", null, "min", 7);
        }

        if(lines < 7){
            this.setLegacy(true);
        }

        return hasDisplayData;
    }

    /**
     * Sets whether the parsed file was a legacy SpriteTool file.
     *
     * @param legacy whether the file was a legacy file
     * @return the parser, for chaining
     */
    private CFGParser setLegacy(boolean legacy){
        this.isLegacy = legacy;
        return this;
    }

    /**
     * Parses the display data from a CFG file.
     * <p>
     * This includes the name, description, tiles, and others.
     * <p>
     * This is based on the GIEPY source code, see ({@code src/mewthree/ParseCfg.c}).
     *
     * @param scanner the input to parse
     * @return the parsed display data
     * @throws ParseException if the scanner has malformed data
     */
    @SuppressWarnings({"HardcodedLineSeparator", "StringConcatenation"})
    private DisplayData parseDisplayData(Scanner scanner) throws ParseException{
        Map<String, String> sections = new HashMap<>();
        String section = null;

        while(scanner.hasNextLine()){
            String line = this.removeComments(scanner.nextLine());

            if(line.isEmpty()){
                continue;
            }

            if(line.startsWith("[")){
                int end = line.indexOf(']');

                if(end < 2){
                    throw new ParseException("Malformed section definition.", "cfg.section.malformed", null, "line", line);
                }

                section = line.substring(1, end).toLowerCase();

                if(sections.containsKey(section)){
                    throw new ParseException("Duplicate section definition", "cfg.section.duplicate", null, "name", section);
                }
            }else{
                if(section == null){
                    throw new ParseException("Section data with no preceding section definition", "cfg.orphan", null, "line", line);
                }

                if(sections.containsKey(section)){
                    sections.put(section, sections.get(section) + "\n" + line);
                }else{
                    sections.put(section, line);
                }

                if("name".equals(section) || "position".equals(section)){
                    section = null;
                }
            }
        }

        DisplayData displayData;

        if(sections.containsKey("label")){
            displayData = new LabelDisplayData();
            ((LabelDisplayData)displayData).setText(sections.get("label"));
        }else if(sections.containsKey("tiles")){
            displayData = new TileDisplayData();

            for(String list : sections.get("tiles").split("\n")){
                try {
                    int[] integers = this.parseIntegers(list, ",", 3, 10, 10, 16);

                    SpriteTile tile = new SpriteTile(integers[0], integers[1], integers[2]);
                    ((TileDisplayData)displayData).getTiles().add(tile);
                }catch(IllegalArgumentException exception){
                    throw new ParseException("Malformed tile data", "cfg.display.tiles", exception, "list", list);
                }
            }
        }else{
            displayData = new TileDisplayData();
        }

        if(sections.containsKey("position")){
            try {
                int[] integers = this.parseIntegers(sections.get("position"), ",", 2, 10, 10);
                displayData.getPosition().setLocation(integers[0], integers[1]);
            }catch(IllegalArgumentException exception){
                throw new ParseException("Malformed position.", "cfg.display.position", exception, "list", sections.get("position"));
            }
        }

        if(sections.containsKey("name")){
            displayData.setName(sections.get("name"));
        }

        if(sections.containsKey("description")){
            displayData.setDescription(sections.get("description"));
        }

        return displayData;
    }

    /**
     * Removes all comments from an input string.
     *
     * @param input the string to remove comments from
     * @return the processed output
     */
    private String removeComments(String input){
        int semicolon = input.indexOf(';');

        if(semicolon != -1){
            input = input.substring(0, semicolon);
        }

        return input.trim();
    }

    /**
     * Parses a string of separated hexadecimal integers.
     *
     * @param line the line to parse
     * @param delimiter the delimiter between each integer
     * @param count how many integers to parse
     * @param radixes the radix for each integer (16 if not set for one)
     * @return the array of parsed integers
     * @throws IllegalArgumentException if the line has malformed or insufficient integers
     */
    private int[] parseIntegers(String line, String delimiter, int count, int... radixes){
        String[] numbers = line.split(delimiter);
        int[] integers = new int[count];

        if(numbers.length != integers.length){
            throw new IllegalArgumentException("Insufficient or too many numbers in list.");
        }

        for(int i = 0; i < integers.length; i++){
            integers[i] = Integer.parseInt(numbers[i], i >= radixes.length ? 16 : radixes[i]);
        }

        return integers;
    }
}
