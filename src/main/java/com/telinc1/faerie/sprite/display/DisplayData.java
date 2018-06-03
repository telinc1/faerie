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

package com.telinc1.faerie.sprite.display;

import java.awt.Point;

/**
 * Defines how a sprite should handle in Lunar Magic.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public abstract class DisplayData {
    /**
     * The name of the sprite in the custom sprite list.
     */
    protected String name;

    /**
     * The description of the sprite in its tooltip.
     */
    protected String description;

    /**
     * The position of the sprite in the sprite window.
     */
    protected Point position;

    /**
     * Constructs new handle data for a sprite with default settings.
     */
    protected DisplayData(){
        this.name = "Sprite";
        this.description = "No description given.";
        this.position = new Point(7, 7);
    }

    /**
     * Returns the name of the sprite.
     *
     * @return the name of the sprite in custom sprite list
     */
    public String getName(){
        return this.name;
    }

    /**
     * Sets a new name for the sprite.
     *
     * @param name the new name in the custom sprite list
     * @return the same object, for chaining
     */
    public DisplayData setName(String name){
        this.name = name;
        return this;
    }

    /**
     * Returns the description of the sprite.
     *
     * @return the description of the sprite as shown in its tooltip
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Sets a new description for the sprite.
     *
     * @param description the new description to set
     * @return the same object, for chaining
     */
    public DisplayData setDescription(String description){
        this.description = description;
        return this;
    }

    /**
     * Returns the position of the sprite.
     *
     * @return a {@link Point} representing the position of the sprite within
     * the sprite window
     */
    public Point getPosition(){
        return this.position;
    }
}
