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
 * Contains the configuration data for a custom sprite insertable with GIEPY.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class CustomSprite implements IConfigurable {
    /**
     * The type of this custom sprite.
     */
    private EnumSpriteType type;

    /**
     * The subtype of this custom sprite.
     */
    private EnumSpriteSubType subtype;

    /**
     * The ID of the sprite which this custom sprite extends in the range [0; 255].
     */
    private int actsLike;

    /**
     * The behavior (Tweaker settings) of the sprite.
     */
    private SpriteBehavior spriteBehavior;

    /**
     * The sprite's first property byte.
     */
    private int firstPropertyByte;

    /**
     * The sprite's second property byte.
     */
    private int secondPropertyByte;

    /**
     * The sprite's status handling.
     */
    private EnumStatusHandling statusHandling;

    /**
     * The sprite's unique byte.
     */
    private int uniqueByte;

    /**
     * The amount of extra bytes this sprite has.
     */
    private int extraBytes;

    /**
     * The first ASM file used by the sprite.
     */
    private String firstASMFile;

    /**
     * The second ASM file used by the sprite.
     */
    private String secondASMFile;

    /**
     * The properties used for sprite display in Lunar Magic.
     */
    private DisplayData displayData;

    /**
     * Create a new custom sprite with default properties.
     */
    public CustomSprite(){
        this.type = EnumSpriteType.CUSTOM;
        this.subtype = EnumSpriteSubType.REGULAR;
        this.actsLike = 0x36;
        this.spriteBehavior = new SpriteBehavior();
        this.displayData = null;
    }

    @Override
    public EnumSpriteType getType(){
        return this.type;
    }

    @Override
    public EnumSpriteSubType getSubType(){
        return this.subtype;
    }

    @Override
    public int getActsLike(){
        return this.actsLike & 0xFF;
    }

    @Override
    public SpriteBehavior getBehavior(){
        return this.spriteBehavior;
    }

    @Override
    public int getFirstPropertyByte(){
        return this.firstPropertyByte & 0xFF;
    }

    @Override
    public int getSecondPropertyByte(){
        return this.secondPropertyByte & 0x3F;
    }

    @Override
    public EnumStatusHandling getStatusHandling(){
        return this.statusHandling;
    }

    @Override
    public int getUniqueByte(){
        return this.uniqueByte & 0xFF;
    }

    @Override
    public int getExtraBytes(){
        return Math.min(Math.max(0, this.extraBytes), this.getSubType().getExtraBytes());
    }

    @Override
    public String getFirstASMFile(){
        return this.getSubType().usesFirstASM() ? this.firstASMFile : null;
    }

    @Override
    public String getSecondASMFile(){
        return this.getSubType().usesSecondASM() ? this.secondASMFile : null;
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
    public CustomSprite setDisplayData(DisplayData displayData){
        this.displayData = displayData;
        return this;
    }

    /**
     * Sets a new second ASM file for the sprite.
     *
     * @param secondASMFile the new second ASM file
     * @return the sprite, for chaining
     */
    public CustomSprite setSecondASMFile(String secondASMFile){
        this.secondASMFile = secondASMFile;
        return this;
    }

    /**
     * Sets a new first ASM file for the sprite.
     *
     * @param firstASMFile the new first ASM file
     * @return the sprite, for chaining
     */
    public CustomSprite setFirstASMFile(String firstASMFile){
        this.firstASMFile = firstASMFile;
        return this;
    }

    /**
     * Sets a new amount of extra bytes for the sprite.
     * <p>
     * It is clamped to the maximum amount of extra bytes for the subtype.
     *
     * @param extraBytes the new amount of extra bytes to set
     * @return the sprite, for chaining
     */
    public CustomSprite setExtraBytes(int extraBytes){
        this.extraBytes = Math.min(Math.max(0, extraBytes), this.getSubType().getExtraBytes());
        return this;
    }

    /**
     * Sets a new unique byte for the sprite.
     * <p>
     * Only the first 8 bits are used.
     *
     * @param uniqueByte the new unique byte to set
     * @return the sprite, for chaining
     */
    public CustomSprite setUniqueByte(int uniqueByte){
        this.uniqueByte = uniqueByte & 0xFF;
        return this;
    }

    /**
     * Sets new handling of the sprite status.
     *
     * @param statusHandling the new handling to set
     * @return the sprite, for chaining
     */
    public CustomSprite setStatusHandling(EnumStatusHandling statusHandling){
        this.statusHandling = statusHandling;
        return this;
    }

    /**
     * Sets a new second property byte for the sprite.
     * <p>
     * Only the first 6 bits are used.
     *
     * @param secondPropertyByte the new second property byte
     * @return the sprite, for chaining
     */
    public CustomSprite setSecondPropertyByte(int secondPropertyByte){
        this.secondPropertyByte = secondPropertyByte & 0x3F;
        return this;
    }

    /**
     * Sets a new first property byte for the sprite.
     * <p>
     * Only the first 8 bits are used.
     *
     * @param firstPropertyByte the new first property byte
     * @return the sprite, for chaining
     */
    public CustomSprite setFirstPropertyByte(int firstPropertyByte){
        this.firstPropertyByte = firstPropertyByte & 0xFF;
        return this;
    }

    /**
     * Sets a new acts like setting for the sprite.
     *
     * @param actsLike the new acts like setting
     * @return the sprite, for chaining
     */
    public CustomSprite setActsLike(int actsLike){
        this.actsLike = actsLike;
        return this;
    }

    /**
     * Sets a new type for the sprite.
     *
     * @param type the new type to set
     * @return the sprite, for chaining
     */
    public CustomSprite setType(EnumSpriteType type){
        this.type = type;
        return this;
    }

    /**
     * Sets a new subtype for the sprite.
     *
     * @param subtype the new subtype to set
     * @return the sprite, for chaining
     */
    public CustomSprite setSubtype(EnumSpriteSubType subtype){
        this.subtype = subtype;
        return this;
    }
}
