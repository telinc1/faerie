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
public class Sprite {
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
    private final SpriteBehavior spriteBehavior;

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
     * The properties used for sprite handle in Lunar Magic.
     */
    private DisplayData displayData;

    /**
     * Creates a new custom sprite with default properties.
     */
    public Sprite(){
        this.type = EnumSpriteType.CUSTOM;
        this.subtype = EnumSpriteSubType.REGULAR;
        this.actsLike = 0x36;
        this.spriteBehavior = new SpriteBehavior();
        this.statusHandling = EnumStatusHandling.HANDLE_STUNNED;
        this.firstASMFile = "";
        this.secondASMFile = "";
        this.displayData = null;
    }

    /**
     * Checks if a sprite has sufficient data.
     *
     * @return whether the sprite has all of the data it needs
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean verify(){
        if(this.getType() == EnumSpriteType.TWEAK){
            return true;
        }

        if(this.usesFirstASM() && (this.firstASMFile == null || this.firstASMFile.isEmpty())){
            return false;
        }

        return !this.usesSecondASM() || (this.secondASMFile != null && !this.secondASMFile.isEmpty());
    }

    /**
     * Returns the type of the sprite.
     *
     * @return an element of {@link EnumSpriteType} which represents this
     * sprite's type
     */
    public EnumSpriteType getType(){
        return this.type;
    }

    /**
     * Checks if this is a tweak of a vanilla sprite.
     */
    public boolean isTweak(){
        return this.getType() == EnumSpriteType.TWEAK;
    }

    /**
     * Sets a new type for the sprite.
     *
     * @param type the new type to set
     * @return the sprite, for chaining
     */
    public Sprite setType(EnumSpriteType type){
        this.type = type;
        return this;
    }

    /**
     * Returns the subtype of the sprite.
     */
    public EnumSpriteSubType getSubType(){
        return this.subtype;
    }

    /**
     * Checks if the sprite has an acts like property.
     */
    public boolean hasActsLike(){
        return this.getSubType() != EnumSpriteSubType.VANILLA;
    }

    /**
     * Checks if the sprite has behavior properties.
     */
    public boolean hasBehavior(){
        return this.getSubType().hasBehavior();
    }

    /**
     * Checks if the sprite has property bytes.
     */
    public boolean hasPropertyBytes(){
        return !this.isTweak() && this.getSubType().hasPropertyBytes();
    }

    /**
     * Checks if the sprite has a unique byte.
     */
    public boolean hasUniqueByte(){
        return !this.isTweak() && this.getSubType().hasUniqueByte();
    }

    /**
     * Returns the maximum amount of extra bytes the sprite can have.
     */
    public int getMaxExtraBytes(){
        return this.isTweak() ? 0 : this.getSubType().getMaxExtraBytes();
    }

    /**
     * Checks if the sprite uses its first ASM file.
     */
    public boolean usesFirstASM(){
        return !this.isTweak() && this.getSubType().usesFirstASM();
    }

    /**
     * Checks if the sprite uses its second ASM file.
     */
    public boolean usesSecondASM(){
        return !this.isTweak() && this.getSubType().usesSecondASM();
    }

    /**
     * Sets a new subtype for the sprite.
     *
     * @param subtype the new subtype to set
     * @return the sprite, for chaining
     */
    public Sprite setSubtype(EnumSpriteSubType subtype){
        this.subtype = subtype;
        return this;
    }

    /**
     * Returns the vanilla sprite number whose properties this sprite shares.
     * If the sprite doesn't have an acts like property, it returns
     * {@code (byte)-1}, or {@code 0xFF}.
     *
     * @return an integer in the range [-1; 255] representing a vanilla sprite
     */
    public int getActsLike(){
        return this.hasActsLike() ? this.actsLike & 0xFF : 0xFF;
    }

    /**
     * Sets a new acts like setting for the sprite.
     *
     * @param actsLike the new acts like setting
     * @return the sprite, for chaining
     */
    public Sprite setActsLike(int actsLike){
        this.actsLike = actsLike & 0xFF;
        return this;
    }

    /**
     * Returns the behavior (Tweaker settings) of the sprite.
     */
    public SpriteBehavior getBehavior(){
        return this.spriteBehavior;
    }

    /**
     * Returns the sprite's first property byte. If the subtype doesn't have
     * property bytes, it returns {@code (byte)-1}, or {@code 0xFF}.
     *
     * @return an integer in the range [-1; 255] representing the
     * first property byte
     */
    public int getFirstPropertyByte(){
        return this.hasPropertyBytes() ? this.firstPropertyByte & 0xFF : 0xFF;
    }

    /**
     * Sets a new first property byte for the sprite. Only the first 8 bits are
     * used.
     *
     * @param firstPropertyByte the new first property byte
     * @return the sprite, for chaining
     */
    public Sprite setFirstPropertyByte(int firstPropertyByte){
        this.firstPropertyByte = firstPropertyByte & 0xFF;
        return this;
    }

    /**
     * Returns the sprite's second property byte. The top two bits of the
     * second property byte are implicitly used as the status override
     * settings and are be masked out. If the subtype doesn't have property
     * bytes, this returns {@code (byte)-1}, or {@code 0xFF}.
     *
     * @return an integer in the range [-1; 63] representing the second
     * property byte
     */
    public int getSecondPropertyByte(){
        return this.hasPropertyBytes() ? this.secondPropertyByte & 0x3F : 0x3F;
    }

    /**
     * Sets a new second property byte for the sprite. Only the first 6 bits
     * are used; the last two are used for the status handling.
     *
     * @param secondPropertyByte the new second property byte
     * @return the sprite, for chaining
     */
    public Sprite setSecondPropertyByte(int secondPropertyByte){
        this.secondPropertyByte = secondPropertyByte & 0x3F;
        return this;
    }

    /**
     * Returns the type of handling this sprite uses for its status.
     */
    public EnumStatusHandling getStatusHandling(){
        return this.hasPropertyBytes() ? this.statusHandling : EnumStatusHandling.OVERRIDE_ALL;
    }

    /**
     * Sets new handling of the sprite status.
     *
     * @param statusHandling the new handling to set
     * @return the sprite, for chaining
     */
    public Sprite setStatusHandling(EnumStatusHandling statusHandling){
        this.statusHandling = statusHandling;
        return this;
    }

    /**
     * Returns the amount of extra bytes this sprite has. This should not be
     * greater than maximum amount defined by the subtype.
     *
     * @see EnumSpriteSubType#getMaxExtraBytes()
     */
    public int getExtraBytes(){
        return Math.min(Math.max(0, this.extraBytes), this.getMaxExtraBytes());
    }

    /**
     * Sets a new amount of extra bytes for the sprite. It is clamped to the
     * maximum amount of extra bytes for the subtype.
     *
     * @param extraBytes the new amount of extra bytes to set
     * @return the sprite, for chaining
     */
    public Sprite setExtraBytes(int extraBytes){
        this.extraBytes = Math.min(Math.max(0, extraBytes), this.getMaxExtraBytes());
        return this;
    }

    /**
     * Returns the sprite's additional unique byte. If the subtype doesn't have
     * a unique byte, this returns {@code (byte)-1}, or {@code 0xFF}.
     *
     * @return an integer in the range [0; 255] representing the unique byte (code)
     */
    public int getUniqueByte(){
        return this.hasUniqueByte() ? this.uniqueByte & 0xFF : 0xFF;
    }

    /**
     * Sets a new unique byte for the sprite. Only the first 8 bits are used.
     *
     * @param uniqueByte the new unique byte to set
     * @return the sprite, for chaining
     */
    public Sprite setUniqueByte(int uniqueByte){
        this.uniqueByte = uniqueByte & 0xFF;
        return this;
    }

    /**
     * Returns the name of the first ASM file used by the sprite. If the
     * subtype doesn't use its first ASM file, it returns {@code null}.
     */
    public String getFirstASMFile(){
        return this.usesFirstASM() ? this.firstASMFile : null;
    }

    /**
     * Sets a new first ASM file for the sprite.
     *
     * @param firstASMFile the new first ASM file
     * @return the sprite, for chaining
     */
    public Sprite setFirstASMFile(String firstASMFile){
        this.firstASMFile = firstASMFile;
        return this;
    }

    /**
     * Returns the name of the second ASM file used by the sprite. If the
     * subtype doesn't use its second ASM file, it returns {@code null}.
     */
    public String getSecondASMFile(){
        return this.usesSecondASM() ? this.secondASMFile : null;
    }

    /**
     * Sets a new second ASM file for the sprite.
     *
     * @param secondASMFile the new second ASM file
     * @return the sprite, for chaining
     */
    public Sprite setSecondASMFile(String secondASMFile){
        this.secondASMFile = secondASMFile;
        return this;
    }

    /**
     * Returns the Lunar Magic display data of the sprite.
     */
    public DisplayData getDisplayData(){
        return this.displayData;
    }

    /**
     * Sets new display data for the sprite.
     *
     * @param displayData the new handle data to set
     * @return the sprite, for chaining
     */
    public Sprite setDisplayData(DisplayData displayData){
        this.displayData = displayData;
        return this;
    }
}
