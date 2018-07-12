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
 * A {@code SpriteTile} represents a specific tile from Lunar Magic's internal
 * sprite Map16 data. It's the building block of {@code TileDisplayData}.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class SpriteTile {
    /**
     * The position of the tile relative to the top left of the sprite.
     */
    private final Point position;

    /**
     * The sprite Map16 tile number for the tile.
     */
    private int tile;

    /**
     * Constructs a new sprite tile.
     *
     * @param x the X position of the tile
     * @param y the Y position of the tile
     * @param tile the Map16 tile number to use
     */
    public SpriteTile(int x, int y, int tile){
        this.position = new Point(x, y);
        this.tile = tile;
    }

    /**
     * Returns the position of the tile relative to the sprite's top right
     * corner. This is a mutable live object.
     */
    public Point getPosition(){
        return this.position;
    }

    /**
     * Returns the Map16 tile number.
     *
     * @return the sprite Map16 tile number used for the tile
     */
    public int getTile(){
        return this.tile;
    }

    /**
     * Sets a new Map16 tile number of the sprite.
     *
     * @param tile the new tile to set
     * @return the same tile, for chaining
     */
    public SpriteTile setTile(int tile){
        this.tile = tile;
        return this;
    }
}
