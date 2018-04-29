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

package com.telinc1.faerie.sprite;

/**
 * Contains the different possible subtypes of a sprite.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public enum EnumSpriteSubType {
    /**
     * Reserved for vanilla sprites.
     * <p>
     * Vanilla sprites only have behavior settings.
     */
    VANILLA(0, true, false, false, 0, false, false),

    /**
     * Denotes a custom regular sprite.
     * <p>
     * A regular sprite uses custom ASM and has extra property bytes, a unique
     * byte, and up to 4 extra bytes.
     */
    REGULAR(1, true, true, true, 4, true, false),

    /**
     * Denotes a custom shooter sprite.
     * <p>
     * A shooter sprite uses custom ASM and has a unique byte and up to 1
     * extra byte.
     */
    SHOOTER(2, false, false, true, 1, true, false),

    /**
     * Denotes a custom generator sprite.
     * <p>
     * A generator sprite uses custom ASM and has a unique byte and up to 1
     * extra byte.
     */
    GENERATOR(3, false, false, true, 1, true, false),

    /**
     * Denotes a custom initializer (run-once) sprite.
     * <p>
     * An initializer sprite uses custom ASM and has a unique byte and up to
     * 251 extra bytes.
     */
    INITIALIZER(4, false, false, true, 251, true, false),

    /**
     * Denotes a custom scroller sprite.
     * <p>
     * A scroller sprite uses both of its custom ASM files and has a unique
     * byte and up to 1 extra byte.
     */
    SCROLLER(5, false, false, true, 1, true, true);

    /**
     * The sprite's subtype as an integer.
     */
    private final int subtype;

    /**
     * Whether this subtype has behavior settings (Tweaker settings).
     */
    private final boolean hasBehavior;

    /**
     * Whether this subtype has extra property bytes.
     */
    private final boolean hasExtraProperties;

    /**
     * Whether this sprite subtype has a unique byte.
     */
    private final boolean hasUniqueByte;

    /**
     * The maximum amount of extra bytes this subtype can have.
     */
    private final int extraBytes;

    /**
     * Whether this sprite subtype uses its first ASM file.
     */
    private final boolean usesFirstASM;

    /**
     * Whether this sprite subtype uses its second ASM file.
     */
    private final boolean usesSecondASM;

    /**
     * Internally construct an enum element.
     *
     * @param subtype the integer representation of the subtype
     * @param hasBehavior whether this subtype has behavior settings
     * @param hasExtraProperties whether this subtype has extra property bytes
     * @param hasUniqueByte whether this subtype has a unique byte
     * @param extraBytes the total amount of extra bytes this subtype can use
     * @param usesFirstASM whether this subtype uses the first ASM file
     * @param usesSecondASM whether this subtype uses the second ASM file
     */
    EnumSpriteSubType(int subtype, boolean hasBehavior, boolean hasExtraProperties, boolean hasUniqueByte, int extraBytes, boolean usesFirstASM, boolean usesSecondASM){
        this.subtype = subtype;
        this.hasBehavior = hasBehavior;
        this.hasExtraProperties = hasExtraProperties;
        this.hasUniqueByte = hasUniqueByte;
        this.extraBytes = extraBytes;
        this.usesFirstASM = usesFirstASM;
        this.usesSecondASM = usesSecondASM;
    }

    /**
     * Retrieves an enum element from the given integer subtype.
     *
     * @param subtype the integer subtype to get
     * @return an element of {@link EnumSpriteSubType} which has this integer
     * (or equivalent) as its type
     */
    public static EnumSpriteSubType fromInteger(int subtype){
        for(EnumSpriteSubType element : EnumSpriteSubType.values()){
            if(element.asInteger() == subtype){
                return element;
            }
        }

        return EnumSpriteSubType.VANILLA;
    }

    /**
     * Returns the subtype of the sprite as an integer.
     *
     * @return an integer which represents this sprite.
     */
    public int asInteger(){
        return this.subtype;
    }

    /**
     * Returns whether this subtype has behavior settings.
     *
     * @return a boolean which represents the property
     */
    public boolean hasBehavior(){
        return this.hasBehavior;
    }

    /**
     * Returns whether this subtype has extra property bytes.
     *
     * @return a boolean which represents the property
     */
    public boolean hasExtraProperties(){
        return this.hasExtraProperties;
    }

    /**
     * Returns whether this subtype has a unique byte.
     *
     * @return a boolean which represents the property
     */
    public boolean hasUniqueByte(){
        return this.hasUniqueByte;
    }

    /**
     * Returns the total amount of extra bytes this subtype can have.
     *
     * @return an integer which represents the property
     */
    public int getExtraBytes(){
        return this.extraBytes;
    }

    /**
     * Returns whether this subtype uses its first ASM file.
     *
     * @return a boolean which represents the property
     */
    public boolean usesFirstASM(){
        return this.usesFirstASM;
    }

    /**
     * Returns whether this subtype uses its second ASM file.
     *
     * @return a boolean which represents the property
     */
    public boolean usesSecondASM(){
        return this.usesSecondASM;
    }

    /**
     * Returns the name of the subtype as a human-readable string.
     *
     * @return the subtype's lower-case name with only the first letter capitalized
     */
    public String readable(){
        return this.toString().charAt(0) + this.toString().substring(1).toLowerCase();
    }
}
