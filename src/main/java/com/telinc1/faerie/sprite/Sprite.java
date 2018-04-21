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

import com.telinc1.faerie.sprite.display.DisplayData;

/**
 * Contains the configuration data for a vanilla sprite.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class Sprite implements IConfigurable {
    /**
     * The ID of the sprite in the range [0; 255].
     */
    private final int number;

    /**
     * The behavior (Tweaker settings) of the sprite.
     */
    private SpriteBehavior spriteBehavior;

    /**
     * The properties used for sprite display in Lunar Magic.
     */
    private DisplayData displayData;

    /**
     * Constructs a vanilla sprite.
     *
     * @param number the sprite number which is represented
     */
    public Sprite(int number){
        this.number = number;
        this.spriteBehavior = new SpriteBehavior();
        this.displayData = null;
    }

    @Override
    public EnumSpriteType getType(){
        return EnumSpriteType.TWEAK;
    }

    @Override
    public EnumSpriteSubType getSubType(){
        return EnumSpriteSubType.REGULAR;
    }

    @Override
    public int getActsLike(){
        return this.number;
    }

    @Override
    public SpriteBehavior getBehavior(){
        return this.spriteBehavior;
    }

    @Override
    public int getFirstPropertyByte(){
        return -1;
    }

    @Override
    public int getSecondPropertyByte(){
        return -1;
    }

    @Override
    public EnumStatusHandling getStatusHandling(){
        return EnumStatusHandling.HANDLE_STUNNED;
    }

    @Override
    public int getUniqueByte(){
        return -1;
    }

    @Override
    public int getExtraBytes(){
        return 0;
    }

    @Override
    public String getFirstASMFile(){
        return null;
    }

    @Override
    public String getSecondASMFile(){
        return null;
    }

    @Override
    public DisplayData getDisplayData(){
        return this.displayData;
    }

    /**
     * Sets new display data for the sprite.
     *
     * @param displayData the new display data to set
     * @return the sprite, for chaining
     */
    public Sprite setDisplayData(DisplayData displayData){
        this.displayData = displayData;
        return this;
    }
}
