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

package com.telinc1.faerie.sprite.emitter;

import com.telinc1.faerie.sprite.Sprite;
import com.telinc1.faerie.sprite.display.DisplayData;
import com.telinc1.faerie.sprite.display.LabelDisplayData;
import com.telinc1.faerie.sprite.display.SpriteTile;
import com.telinc1.faerie.sprite.display.TileDisplayData;

import java.io.IOException;
import java.io.Writer;

/**
 * Emits a CFG configuration file.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class CFGEmitter extends Emitter {
    /**
     * Constructs a CFG emitter for the given sprite.
     *
     * @param sprite the sprite to write
     */
    public CFGEmitter(Sprite sprite){
        super(sprite);
    }

    @Override
    public void emit(Writer writer) throws IOException{
        Sprite sprite = this.getSprite();
        int[] behavior = sprite.getBehavior().pack();
        String firstASM = sprite.getFirstASMFile();
        String secondASM = sprite.getSecondASMFile();
        DisplayData displayData = sprite.getDisplayData();

        writer.write(String.format(
            "%02X%n%02X%n%02X %02X %02X %02X %02X %02X%n%02X %02X%n%s%n00%n%02X%n%02X%n%02X%n%s%n",
            sprite.getType().asInteger(),
            sprite.getActsLike() & 0xFF,
            behavior[0], behavior[1], behavior[2], behavior[3], behavior[4], behavior[5],
            sprite.getFirstPropertyByte() & 0xFF, sprite.getSecondPropertyByte() & 0xFF,
            firstASM == null ? "" : firstASM,
            sprite.getSubType().asInteger(),
            sprite.getUniqueByte() & 0xFF,
            sprite.getExtraBytes() & 0xFF,
            secondASM == null ? "" : secondASM
        ));

        if(displayData != null){
            writer.write(String.format(
                "%n---%n[Name]%n%s%n[Description]%n%s%n[Position]%n%d,%d%n",
                displayData.getName(),
                displayData.getDescription(),
                displayData.getPosition().x,
                displayData.getPosition().y
            ));

            if(displayData instanceof TileDisplayData){
                TileDisplayData tileDisplayData = (TileDisplayData)displayData;
                writer.write(String.format("[Tiles]%n"));

                for(SpriteTile tile : tileDisplayData.getTiles()){
                    writer.write(String.format(
                        "%d,%d,%02X%n",
                        tile.getPosition().x,
                        tile.getPosition().y,
                        tile.getTile()
                    ));
                }
            }else if(displayData instanceof LabelDisplayData){
                LabelDisplayData labelDisplayData = (LabelDisplayData)displayData;
                writer.write(String.format("[Label]%n%s%n", labelDisplayData.getText()));
            }
        }

        writer.flush();
    }
}
