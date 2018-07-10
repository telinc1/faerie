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

package com.telinc1.faerie.sprite.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.telinc1.faerie.sprite.EnumSpriteSubType;
import com.telinc1.faerie.sprite.EnumSpriteType;
import com.telinc1.faerie.sprite.Sprite;
import com.telinc1.faerie.sprite.SpriteBehavior;
import com.telinc1.faerie.sprite.display.DisplayData;
import com.telinc1.faerie.sprite.display.LabelDisplayData;
import com.telinc1.faerie.sprite.display.SpriteTile;
import com.telinc1.faerie.sprite.display.TileDisplayData;
import com.telinc1.faerie.util.locale.Warning;

import java.io.Reader;

/**
 * Parses a JSON configuration file.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class JSONParser extends Parser {
    /**
     * Constructs a JSON parser.
     *
     * @param input the input to the parser
     */
    public JSONParser(Reader input){
        super(input);
    }

    @Override
    public Sprite parse() throws ParseException{
        Sprite sprite = new Sprite();
        SpriteBehavior behavior = sprite.getBehavior();

        JsonParser parser = new JsonParser();

        try {
            JsonObject json = parser.parse(this.getInput()).getAsJsonObject();

            sprite.setType(EnumSpriteType.fromInteger(this.getInt(json, "Type")));
            sprite.setSubtype(EnumSpriteSubType.fromInteger(this.getInt(json, "SubType")));
            sprite.setActsLike(this.getInt(json, "ActLike"));

            if(!json.has("SubType")){
                this.getWarnings().add(new Warning("parse", "legacy"));
            }

            this.parse1656(json, behavior);
            this.parse1662(json, behavior);
            this.parse166E(json, behavior);
            this.parse167A(json, behavior);
            this.parse1686(json, behavior);
            this.parse190F(json, behavior);

            sprite.setFirstPropertyByte(this.getInt(json, "Extra Property Byte 1"));
            sprite.setSecondPropertyByte(this.getInt(json, "Extra Property Byte 2"));
            sprite.setUniqueByte(this.getInt(json, "Unique Info"));

            sprite.setFirstASMFile(this.getString(json, "AsmFile"));
            sprite.setSecondASMFile(this.getString(json, "AsmFile2"));

            sprite.setExtraBytes(this.getInt(json, "Extra Bytes Length"));

            DisplayData displayData = null;

            if(json.has("Label")){
                displayData = new LabelDisplayData();
                ((LabelDisplayData)displayData).setText(this.getStringArray(json, "Label"));
            }else if(json.has("Tiles")){
                JsonElement tiles = json.get("Tiles");

                if(tiles.isJsonArray() && tiles.getAsJsonArray().size() > 0){
                    displayData = new TileDisplayData();

                    for(JsonElement tile : tiles.getAsJsonArray()){
                        SpriteTile spriteTile = new SpriteTile(this.getInt(tile, "X"), this.getInt(tile, "Y"), this.getInt(tile, "Tile"));
                        ((TileDisplayData)displayData).getTiles().add(spriteTile);
                    }
                }
            }else{
                displayData = new TileDisplayData();
            }

            if(displayData != null){
                displayData.getPosition().setLocation(this.getInt(json, "X"), this.getInt(json, "Y"));
                displayData.setName(this.getString(json, "Name"));
                displayData.setDescription(this.getStringArray(json, "Description"));

                sprite.setDisplayData(displayData);
            }

            if(!sprite.verify()){
                throw new ParseException("Incomplete sprite data.", "incomplete", null);
            }
        }catch(JsonParseException exception){
            throw new ParseException("Can't parse JSON.", "json.parse", exception);
        }catch(IllegalStateException exception){
            throw new ParseException("Malformed configuration.", "json.malformed", exception);
        }

        return sprite;
    }

    /**
     * Retrieves an integer from a {@link JsonObject}.
     *
     * @param object the {@code JsonObject} to retrieve the integer from
     * @param property the property name to retrieve
     * @return the integer value of the property, or {@code 0} if it doesn't
     * exist or isn't an integer
     */
    private int getInt(JsonElement object, String property){
        if(!object.isJsonObject()){
            return 0;
        }

        JsonElement element = object.getAsJsonObject().get(property);

        if(element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isNumber()){
            return 0;
        }

        return element.getAsJsonPrimitive().getAsInt();
    }

    /**
     * Parses the behavior settings for $1656 from a {@link JsonObject}.
     *
     * @param json the {@code JsonObject} from which to parse the settings
     * @param behavior the {@code SpriteBehavior} into which to parse
     */
    private void parse1656(JsonObject json, SpriteBehavior behavior){
        JsonObject $1656 = this.getObject(json, "$1656");

        if($1656 == null){
            return;
        }

        behavior.objectClipping = (byte)(this.getInt($1656, "Object Clipping") & 0x1F);
        behavior.canBeJumpedOn = this.getBoolean($1656, "Can be jumped on");
        behavior.diesWhenJumpedOn = this.getBoolean($1656, "Dies when jumped on");
        behavior.hopInShells = this.getBoolean($1656, "Hop in /kick shell");
        behavior.disappearInSmoke = this.getBoolean($1656, "Disappears in cloud of smoke");
    }

    /**
     * Parses the behavior settings for $1662 from a {@link JsonObject}.
     *
     * @param json the {@code JsonObject} from which to parse the settings
     * @param behavior the {@code SpriteBehavior} into which to parse
     */
    private void parse1662(JsonObject json, SpriteBehavior behavior){
        JsonObject $1662 = this.getObject(json, "$1662");

        if($1662 == null){
            return;
        }

        behavior.spriteClipping = (byte)(this.getInt($1662, "Sprite Clipping") & 0x3F);
        behavior.useShellAsDeathFrame = this.getBoolean($1662, "Use shell as death frame");
        behavior.fallsWhenKilled = this.getBoolean($1662, "Fall straight down when killed");
    }

    /**
     * Parses the behavior settings for $166E from a {@link JsonObject}.
     *
     * @param json the {@code JsonObject} from which to parse the settings
     * @param behavior the {@code SpriteBehavior} into which to parse
     */
    private void parse166E(JsonObject json, SpriteBehavior behavior){
        JsonObject $166E = this.getObject(json, "$166E");

        if($166E == null){
            return;
        }

        behavior.useSecondGraphicsPage = this.getBoolean($166E, "Use second graphics page");
        behavior.palette = (byte)(this.getInt($166E, "Palette") & 0x7);
        behavior.disableFireballKilling = this.getBoolean($166E, "Disable fireball killing");
        behavior.disableCapeKilling = this.getBoolean($166E, "Disable cape killing");
        behavior.disableWaterSplash = this.getBoolean($166E, "Disable water splash");
        behavior.disableSecondaryInteraction = this.getBoolean($166E, "Don't interact with Layer 2");
    }

    /**
     * Parses the behavior settings for $167A from a {@link JsonObject}.
     *
     * @param json the {@code JsonObject} from which to parse the settings
     * @param behavior the {@code SpriteBehavior} into which to parse
     */
    private void parse167A(JsonObject json, SpriteBehavior behavior){
        JsonObject $167A = this.getObject(json, "$167A");

        if($167A == null){
            return;
        }

        behavior.processIfDead = this.getBoolean($167A, "Don't disable cliping when starkilled");
        behavior.invincibleToPlayer = this.getBoolean($167A, "Invincible to star/cape/fire/bounce blk");
        behavior.processWhileOffscreen = this.getBoolean($167A, "Process when off screen");
        behavior.skipShellIfStunned = this.getBoolean($167A, "Don't change into shell when stunned");
        behavior.disableKicking = this.getBoolean($167A, "Can't be kicked like shell");
        behavior.processInteractionEveryFrame = this.getBoolean($167A, "Process interaction with Mario every frame");
        behavior.isPowerup = this.getBoolean($167A, "Gives power-up when eaten by Yoshi");
        behavior.disableDefaultInteraction = this.getBoolean($167A, "Don't use default interaction with Mario");
    }

    /**
     * Parses the behavior settings for $1686 from a {@link JsonObject}.
     *
     * @param json the {@code JsonObject} from which to parse the settings
     * @param behavior the {@code SpriteBehavior} into which to parse
     */
    private void parse1686(JsonObject json, SpriteBehavior behavior){
        JsonObject $1686 = this.getObject(json, "$1686");

        if($1686 == null){
            return;
        }

        behavior.inedible = this.getBoolean($1686, "Inedible");
        behavior.stayInMouth = this.getBoolean($1686, "Stay in Yoshi's mouth");
        behavior.weirdGroundBehavior = this.getBoolean($1686, "Weird ground behaviour");
        behavior.disableSpriteInteraction = this.getBoolean($1686, "Don't interact with other sprites");
        behavior.preserveDirection = this.getBoolean($1686, "Don't change direction if touched");
        behavior.disappearOnGoal = this.getBoolean($1686, "Don't turn into coin when goal passed");
        behavior.spawnsSpriteWhenStunned = this.getBoolean($1686, "Spawn a new sprite");
        behavior.disableObjectInteraction = this.getBoolean($1686, "Don't interact with objects");
    }

    /**
     * Parses the behavior settings for $190F from a {@link JsonObject}.
     *
     * @param json the {@code JsonObject} from which to parse the settings
     * @param behavior the {@code SpriteBehavior} into which to parse
     */
    private void parse190F(JsonObject json, SpriteBehavior behavior){
        JsonObject $190F = this.getObject(json, "$190F");

        if($190F == null){
            return;
        }

        behavior.platformPassableFromBelow = this.getBoolean($190F, "Make platform passable from below");
        behavior.ignoreGoal = this.getBoolean($190F, "Don't erase when goal passed");
        behavior.disableSlideKilling = this.getBoolean($190F, "Can't be killed by sliding");
        behavior.takesFiveFireballs = this.getBoolean($190F, "Take 5 fireballs to kill");
        behavior.canBeJumpedOnFromBelow = this.getBoolean($190F, "Can't be jumped on with upwards Y speed");
        behavior.tallDeathFrame = this.getBoolean($190F, "Death frame two tiles high");
        behavior.ignoreSilverPSwitch = this.getBoolean($190F, "Don't turn into a coin with silver POW");
        behavior.escapeWalls = this.getBoolean($190F, "Don't get stuck in walls (carryable sprites)");
    }

    /**
     * Retrieves a string from a {@link JsonObject}.
     *
     * @param object the {@code JsonObject} to retrieve the string from
     * @param property the property name to retrieve
     * @return the string value of the property, or {@code ""} if it doesn't
     * exist or isn't a string
     */
    private String getString(JsonElement object, String property){
        if(!object.isJsonObject()){
            return "";
        }

        JsonElement element = object.getAsJsonObject().get(property);

        if(element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()){
            return "";
        }

        return element.getAsJsonPrimitive().getAsString();
    }

    /**
     * Retrieves an array of strings as a single multi-line string from
     * a {@link JsonObject}.
     *
     * @param object the {@code JsonObject} to retrieve the string array from
     * @param property the property name to retrieve
     * @return the string value of the property, or {@code ""} if it doesn't
     * exist or isn't a string array
     */
    private String getStringArray(JsonElement object, String property){
        if(!object.isJsonObject()){
            return "";
        }

        JsonElement lines = object.getAsJsonObject().get(property);

        if(lines == null || !lines.isJsonArray()){
            return "";
        }

        StringBuilder text = new StringBuilder();

        for(JsonElement line : lines.getAsJsonArray()){
            if(!line.isJsonPrimitive() || !line.getAsJsonPrimitive().isString()){
                continue;
            }

            text.append(line.getAsJsonPrimitive().getAsString());
            text.append('\n');
        }

        return text.toString().trim();
    }

    /**
     * Retrieves an object from its parent {@link JsonObject}.
     *
     * @param object the {@code JsonObject} to retrieve the child from
     * @param property the property name to retrieve
     * @return the object value of the property, or {@code null} if it doesn't
     * exist or isn't an object
     */
    private JsonObject getObject(JsonElement object, String property){
        if(!object.isJsonObject()){
            return null;
        }

        JsonElement element = object.getAsJsonObject().get(property);

        if(element == null || !element.isJsonObject()){
            return null;
        }

        return element.getAsJsonObject();
    }

    /**
     * Retrieves a boolean from a {@link JsonObject}.
     *
     * @param object the {@code JsonObject} to retrieve the boolean from
     * @param property the property name to retrieve
     * @return the boolean value of the property, or {@code false} if it
     * doesn't exist or isn't a boolean
     */
    private boolean getBoolean(JsonElement object, String property){
        if(!object.isJsonObject()){
            return false;
        }

        JsonElement element = object.getAsJsonObject().get(property);

        if(element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isBoolean()){
            return false;
        }

        return element.getAsJsonPrimitive().getAsBoolean();
    }
}
