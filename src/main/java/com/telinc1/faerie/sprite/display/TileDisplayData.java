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

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code TileDisplayData} class defines data for a sprite which renders as
 * one or more internal sprite tiles within Lunar Magic.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class TileDisplayData extends DisplayData {
    /**
     * The list of tiles used by this sprite.
     */
    private List<SpriteTile> tiles;

    /**
     * Constructs an empty list of tiles with default properties.
     */
    public TileDisplayData(){
        super();
        this.tiles = new ArrayList<>();
    }

    /**
     * Returns the tiles used by this sprite.
     *
     * @return the {@link java.util.List} of tiles which this sprite uses.
     */
    public List<SpriteTile> getTiles(){
        return this.tiles;
    }
}
