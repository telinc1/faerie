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
import com.telinc1.faerie.sprite.SpriteBehavior;
import com.telinc1.faerie.sprite.display.DisplayData;
import com.telinc1.faerie.sprite.display.LabelDisplayData;
import com.telinc1.faerie.sprite.display.SpriteTile;
import com.telinc1.faerie.sprite.display.TileDisplayData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.Writer;

/**
 * Emits a JSON configuration file.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class JSONEmitter extends Emitter {
    /**
     * Constructs a JSON emitter for the given sprite.
     *
     * @param sprite the sprite to write
     */
    public JSONEmitter(Sprite sprite){
        super(sprite);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void emit(Writer writer) throws IOException{
        Sprite sprite = this.getSprite();
        SpriteBehavior behavior = sprite.getBehavior();
        DisplayData displayData = sprite.getDisplayData();

        JSONObject json = new JSONObject();
        json.put("Type", sprite.getType().asInteger());
        json.put("SubType", sprite.getSubType().asInteger());
        json.put("ActLike", sprite.getActsLike());

        JSONObject $1656 = new JSONObject();
        $1656.put("Object Clipping", behavior.objectClipping);
        $1656.put("Can be jumped on", behavior.canBeJumpedOn);
        $1656.put("Dies when jumped on", behavior.diesWhenJumpedOn);
        $1656.put("Hop in/kick shell", behavior.hopInShells);
        $1656.put("Disappears in cloud of smoke", behavior.disappearInSmoke);
        json.put("$1656", $1656);

        JSONObject $1662 = new JSONObject();
        $1662.put("Sprite Clipping", behavior.spriteClipping);
        $1662.put("Use shell as death frame", behavior.useShellAsDeathFrame);
        $1662.put("Fall straight down when killed", behavior.fallsWhenKilled);
        json.put("$1662", $1662);

        JSONObject $166E = new JSONObject();
        $166E.put("Use second graphics page", behavior.useSecondGraphicsPage);
        $166E.put("Palette", behavior.palette);
        $166E.put("Disable fireball killing", behavior.disableFireballKilling);
        $166E.put("Disable cape killing", behavior.disableCapeKilling);
        $166E.put("Disable water splash", behavior.disableWaterSplash);
        $166E.put("Don't interact with Layer 2", behavior.disableSecondaryInteraction);
        json.put("$166E", $166E);

        JSONObject $167A = new JSONObject();
        $167A.put("Don't disable cliping when starkilled", behavior.processIfDead);
        $167A.put("Invincible to star/cape/fire/bounce blk.", behavior.invincibleToPlayer);
        $167A.put("Process when off screen", behavior.processWhileOffscreen);
        $167A.put("Don't change into shell when stunned", behavior.skipShellIfStunned);
        $167A.put("Can't be kicked like shell", behavior.disableKicking);
        $167A.put("Process interaction with Mario every frame", behavior.processInteractionEveryFrame);
        $167A.put("Gives power-up when eaten by Yoshi", behavior.isPowerup);
        $167A.put("Don't use default interaction with Mario", behavior.disableDefaultInteraction);
        json.put("$167A", $167A);

        JSONObject $1686 = new JSONObject();
        $1686.put("Inedible", behavior.inedible);
        $1686.put("Stay in Yoshi's mouth", behavior.stayInMouth);
        $1686.put("Weird ground behaviour", behavior.weirdGroundBehavior);
        $1686.put("Don't interact with other sprites", behavior.disableSpriteInteraction);
        $1686.put("Don't change direction if touched", behavior.preserveDirection);
        $1686.put("Don't turn into coin when goal passed", behavior.disappearOnGoal);
        $1686.put("Spawn a new sprite", behavior.spawnsSpriteWhenStunned);
        $1686.put("Don't interact with objects", behavior.disableObjectInteraction);
        json.put("$1686", $1686);

        JSONObject $190F = new JSONObject();
        $190F.put("Make platform passable from below", behavior.platformPassableFromBelow);
        $190F.put("Don't erase when goal passed", behavior.ignoreGoal);
        $190F.put("Can't be killed by sliding", behavior.disableSlideKilling);
        $190F.put("Takes 5 fireballs to kill", behavior.takesFiveFireballs);
        $190F.put("Can be jumped on with upwards Y speed", behavior.canBeJumpedOnFromBelow);
        $190F.put("Death frame two tiles high", behavior.tallDeathFrame);
        $190F.put("Don't turn into a coin with silver POW", behavior.ignoreSilverPSwitch);
        $190F.put("Don't get stuck in walls (carryable sprites)", behavior.escapeWalls);
        json.put("$190F", $190F);

        json.put("Extra Property Byte 1", sprite.getFirstPropertyByte());
        json.put("Extra Property Byte 2", sprite.getSecondPropertyByte());
        json.put("Unique Info", sprite.getUniqueByte());

        json.put("AsmFile", sprite.usesFirstASM() ? sprite.getFirstASMFile() : "");
        json.put("AsmFile2", sprite.usesSecondASM() ? sprite.getSecondASMFile() : "");

        json.put("Extra Bytes Length", sprite.getExtraBytes());

        if(displayData != null){
            json.put("Name", displayData.getName());

            JSONArray description = new JSONArray();
            description.add(displayData.getDescription());
            json.put("Description", description);

            json.put("X", displayData.getPosition().x);
            json.put("Y", displayData.getPosition().y);

            if(displayData instanceof TileDisplayData){
                JSONArray tiles = new JSONArray();
                TileDisplayData tileDisplayData = (TileDisplayData)displayData;

                for(SpriteTile spriteTile : tileDisplayData.getTiles()){
                    JSONObject tile = new JSONObject();
                    tile.put("X", spriteTile.getPosition().x);
                    tile.put("Y", spriteTile.getPosition().y);
                    tile.put("Tile", spriteTile.getTile());

                    tiles.add(tile);
                }

                json.put("Tiles", tiles);
            }else if(displayData instanceof LabelDisplayData){
                JSONArray label = new JSONArray();
                LabelDisplayData labelDisplayData = (LabelDisplayData)displayData;

                label.add(labelDisplayData.getText());
                json.put("Label", label);
            }
        }

        writer.write(json.toJSONString());
        writer.flush();
    }
}
