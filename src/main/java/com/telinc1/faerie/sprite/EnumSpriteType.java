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

import com.telinc1.faerie.Resources;

/**
 * Contains the different possible types of a sprite.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public enum EnumSpriteType {
    /**
     * Denotes a tweak sprite.
     * <p>
     * A tweak sprite only uses its acts like setting and behavior to tweak a
     * vanilla sprite.
     */
    TWEAK(0),

    /**
     * Denotes a custom sprite.
     * <p>
     * A custom sprite uses its subtype to run custom code at a given time.
     */
    CUSTOM(1);

    /**
     * The sprite's type as an integer.
     */
    private final int type;

    /**
     * Internally construct an enum element.
     *
     * @param type the integer representation of the type
     */
    EnumSpriteType(int type){
        this.type = type;
    }

    /**
     * Retrieves an enum element from the given integer type.
     *
     * @param type the integer type to get
     * @return an element of {@link EnumSpriteType} which has this integer
     * (or equivalent) as its type
     */
    public static EnumSpriteType fromInteger(int type){
        return type == 0 ? EnumSpriteType.TWEAK : EnumSpriteType.CUSTOM;
    }

    /**
     * Returns the type of the sprite as an integer.
     *
     * @return an integer which represents this sprite.
     */
    public int asInteger(){
        return this.type;
    }

    /**
     * Returns the name of the type as a human-readable string.
     *
     * @return the type's lower-case name with only the first letter capitalized
     */
    public String toLocalizedString(){
        return Resources.getString("main", "sprite.insertion.type." + this.toString().toLowerCase());
    }
}
