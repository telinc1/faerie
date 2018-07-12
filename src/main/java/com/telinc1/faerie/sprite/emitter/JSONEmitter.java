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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.telinc1.faerie.sprite.Sprite;
import com.telinc1.faerie.sprite.SpriteBehavior;
import com.telinc1.faerie.sprite.display.DisplayData;
import com.telinc1.faerie.sprite.display.LabelDisplayData;
import com.telinc1.faerie.sprite.display.SpriteTile;
import com.telinc1.faerie.sprite.display.TileDisplayData;

import java.io.IOException;
import java.io.Writer;

/**
 * The {@code CFGEmitter} emits a GIEPY-compatible JSON file.
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
    public void emit(Writer writer) throws IOException{
        Sprite sprite = this.getSprite();
        SpriteBehavior behavior = sprite.getBehavior();
        DisplayData displayData = sprite.getDisplayData();

        JsonObject json = new JsonObject();
        json.addProperty("Type", sprite.getType().asInteger());
        json.addProperty("Type", sprite.getType().asInteger());
        json.addProperty("SubType", sprite.getSubType().asInteger());
        json.addProperty("ActLike", sprite.getActsLike());

        JsonObject $1656 = new JsonObject();
        $1656.addProperty("Object Clipping", behavior.objectClipping);
        $1656.addProperty("Can be jumped on", behavior.canBeJumpedOn);
        $1656.addProperty("Dies when jumped on", behavior.diesWhenJumpedOn);
        $1656.addProperty("Hop in /kick shell", behavior.hopInShells);
        $1656.addProperty("Disappears in cloud of smoke", behavior.disappearInSmoke);
        json.add("$1656", $1656);

        JsonObject $1662 = new JsonObject();
        $1662.addProperty("Sprite Clipping", behavior.spriteClipping);
        $1662.addProperty("Use shell as death frame", behavior.useShellAsDeathFrame);
        $1662.addProperty("Fall straight down when killed", behavior.fallsWhenKilled);
        json.add("$1662", $1662);

        JsonObject $166E = new JsonObject();
        $166E.addProperty("Use second graphics page", behavior.useSecondGraphicsPage);
        $166E.addProperty("Palette", behavior.palette);
        $166E.addProperty("Disable fireball killing", behavior.disableFireballKilling);
        $166E.addProperty("Disable cape killing", behavior.disableCapeKilling);
        $166E.addProperty("Disable water splash", behavior.disableWaterSplash);
        $166E.addProperty("Don't interact with Layer 2", behavior.disableSecondaryInteraction);
        json.add("$166E", $166E);

        JsonObject $167A = new JsonObject();
        $167A.addProperty("Don't disable cliping when starkilled", behavior.processIfDead);
        $167A.addProperty("Invincible to star/cape/fire/bounce blk", behavior.invincibleToPlayer);
        $167A.addProperty("Process when off screen", behavior.processWhileOffscreen);
        $167A.addProperty("Don't change into shell when stunned", behavior.skipShellIfStunned);
        $167A.addProperty("Can't be kicked like shell", behavior.disableKicking);
        $167A.addProperty("Process interaction with Mario every frame", behavior.processInteractionEveryFrame);
        $167A.addProperty("Gives power-up when eaten by Yoshi", behavior.isPowerup);
        $167A.addProperty("Don't use default interaction with Mario", behavior.disableDefaultInteraction);
        json.add("$167A", $167A);

        JsonObject $1686 = new JsonObject();
        $1686.addProperty("Inedible", behavior.inedible);
        $1686.addProperty("Stay in Yoshi's mouth", behavior.stayInMouth);
        $1686.addProperty("Weird ground behaviour", behavior.weirdGroundBehavior);
        $1686.addProperty("Don't interact with other sprites", behavior.disableSpriteInteraction);
        $1686.addProperty("Don't change direction if touched", behavior.preserveDirection);
        $1686.addProperty("Don't turn into coin when goal passed", behavior.disappearOnGoal);
        $1686.addProperty("Spawn a new sprite", behavior.spawnsSpriteWhenStunned);
        $1686.addProperty("Don't interact with objects", behavior.disableObjectInteraction);
        json.add("$1686", $1686);

        JsonObject $190F = new JsonObject();
        $190F.addProperty("Make platform passable from below", behavior.platformPassableFromBelow);
        $190F.addProperty("Don't erase when goal passed", behavior.ignoreGoal);
        $190F.addProperty("Can't be killed by sliding", behavior.disableSlideKilling);
        $190F.addProperty("Take 5 fireballs to kill", behavior.takesFiveFireballs);
        $190F.addProperty("Can't be jumped on with upwards Y speed", behavior.canBeJumpedOnFromBelow);
        $190F.addProperty("Death frame two tiles high", behavior.tallDeathFrame);
        $190F.addProperty("Don't turn into a coin with silver POW", behavior.ignoreSilverPSwitch);
        $190F.addProperty("Don't get stuck in walls (carryable sprites)", behavior.escapeWalls);
        json.add("$190F", $190F);

        json.addProperty("Extra Property Byte 1", sprite.getFirstPropertyByte());
        json.addProperty("Extra Property Byte 2", sprite.getSecondPropertyByte());
        json.addProperty("Unique Info", sprite.getUniqueByte());

        json.addProperty("AsmFile", sprite.usesFirstASM() ? sprite.getFirstASMFile() : "");
        json.addProperty("AsmFile2", sprite.usesSecondASM() ? sprite.getSecondASMFile() : "");

        json.addProperty("Extra Bytes Length", sprite.getExtraBytes());

        if(displayData != null){
            json.addProperty("Name", displayData.getName());

            JsonArray description = new JsonArray();
            description.add(displayData.getDescription());
            json.add("Description", description);

            json.addProperty("X", displayData.getPosition().x);
            json.addProperty("Y", displayData.getPosition().y);

            if(displayData instanceof TileDisplayData){
                JsonArray tiles = new JsonArray();
                TileDisplayData tileDisplayData = (TileDisplayData)displayData;

                for(SpriteTile spriteTile : tileDisplayData.getTiles()){
                    JsonObject tile = new JsonObject();
                    tile.addProperty("X", spriteTile.getPosition().x);
                    tile.addProperty("Y", spriteTile.getPosition().y);
                    tile.addProperty("Tile", spriteTile.getTile());

                    tiles.add(tile);
                }

                json.add("Tiles", tiles);
            }else if(displayData instanceof LabelDisplayData){
                JsonArray label = new JsonArray();
                LabelDisplayData labelDisplayData = (LabelDisplayData)displayData;

                label.add(labelDisplayData.getText());
                json.add("Label", label);
            }
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonWriter jsonWriter = gson.newJsonWriter(writer);
        jsonWriter.setIndent("    ");
        gson.toJson(json, jsonWriter);

        writer.flush();
    }
}
