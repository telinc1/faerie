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

/**
 * Contains the behavior of a sprite, often called the Tweaker settings.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class SpriteBehavior {
    /**
     * Bits 0-3 of $1656, the sprite's block (object) interaction hitbox.
     */
    public byte objectClipping;

    /**
     * The 4th bit of $1656, whether the player can jump on the sprite without
     * being hurt (if using default player interaction).
     */
    public boolean canBeJumpedOn;

    /**
     * The 5th bit of $1656, whether the sprite will just die or turn carryable
     * after the player jumps on it.
     */
    public boolean diesWhenJumpedOn;

    /**
     * The 6th bit of $1656, whether the sprites interacts with shells like a
     * Beach Koopa.
     */
    public boolean hopInShells;

    /**
     * The 7th bit of $1656, whether the sprite will disappear in a puff of
     * smoke when the player kills it.
     */
    public boolean disappearInSmoke;

    /**
     * Bits 0-5 of $1662, the sprite's interaction (hitbox) with other sprites.
     */
    public byte spriteClipping;

    /**
     * Bit 6 of $1662, whether the sprite uses a shell as its death frame.
     */
    public boolean useShellAsDeathFrame;

    /**
     * Bit 7 of $1662, whether the sprite will fall down the screen when the
     * player kills it by jumping on it.
     */
    public boolean fallsStraightDownWhenKilled;

    /**
     * Bit 0 of $166E, the bit stored to bit 0 of the YXPPCCCT properties
     * (highest bit of the sprite tile).
     */
    public boolean useSecondGraphicsPage;

    /**
     * Bits 1-3 of $166E, the palette stored to the YXPPCCCT properties.
     */
    public byte palette;

    /**
     * Bit 4 of $166E, whether the sprite can be killed by fireballs. If false,
     * it just makes them disappear.
     */
    public boolean disableFireballKilling;

    /**
     * Bit 5 of $166E, whether the sprite can interact with the player's cape.
     */
    public boolean disableCapeKilling;

    /**
     * Bit 6 of $166E, whether the sprite creates a smoke sprite when entering
     * or exiting water.
     */
    public boolean disableWaterSplash;

    /**
     * Bit 7 of $166E, whether the sprite can interact with a solid secondary
     * layer (either interactable layer 2 or a layer 3 tide).
     */
    public boolean disableSecondaryInteraction;

    /**
     * Bit 0 of $167A, whether the sprite will still run its main code while
     * falling off-screen after being killed.
     */
    public boolean processIfDead;

    /**
     * Bit 1 of $167A, whether the sprite will at all interact with
     * star power, capes, fireballs, and bounce sprites.
     */
    public boolean invincibleToPlayer;

    /**
     * Bit 2 of $167A, whether the sprite will despawn when going off-screen.
     */
    public boolean processWhileOffscreen;

    /**
     * Bit 3 of $167A, whether the sprite will automatically display shell
     * graphics when it is stunned.
     */
    public boolean skipShellIfStunned;

    /**
     * Bit 4 of $167A, whether the sprite can be kicked like a shell while it
     * is carryable.
     */
    public boolean disableKicking;

    /**
     * Bit 5 of $167A, whether the sprite will process its interaction with the
     * player every frame instead of every second frame.
     */
    public boolean processInteractionEveryFrame;

    /**
     * Bit 6 of $167A, whether the sprite will give the player a powerup when
     * eaten by Yoshi.
     */
    public boolean isPowerup;

    /**
     * Bit 7 of $167A, whether the sprite will the use the game's default player
     * interaction routines.
     */
    public boolean disableDefaultInteraction;

    /**
     * Bit 0 of $1686, whether the sprite can at all be eaten by Yoshi under
     * normal conditions.
     */
    public boolean inedible;

    /**
     * Bit 1 of $1686, whether the sprite will stay in Yoshi's mouth when
     * eaten.
     */
    public boolean stayInMouth;

    /**
     * Bit 2 of $1686, an unidentified ground behavior bit.
     */
    public boolean weirdGroundBehavior;

    /**
     * Bit 3 of $1686, whether the sprite will interact with other sprites.
     */
    public boolean disableSpriteInteraction;

    /**
     * Bit 4 of $1686, whether the sprite will flip its direction when the
     * player touches it (with default interaction).
     */
    public boolean preserveDirection;

    /**
     * Bit 5 of $1686, whether the sprite will disappear instead of turning
     * into a coin when the player passes the goal.
     */
    public boolean disappearWhenGoalPassed;

    /**
     * Bit 6 of $1686, whether the sprite will spawn another sprite after its
     * stun timer runs out in its carryable state.
     */
    public boolean spawnsNewSprite;

    /**
     * Bit 7 of $1686, whether the sprite will interact with objects (blocks).
     */
    public boolean disableObjectInteraction;

    /**
     * Bit 0 of $190F, whether a platform sprite can be passed from below.
     */
    public boolean platformPassableFromBelow;

    /**
     * Bit 1 of $190F, whether the sprite will just ignore passing the goal
     * without turning into a coin or disappearing.
     */
    public boolean ignoreGoal;

    /**
     * Bit 2 of $190F, whether the sprite can be killed by sliding into it
     * (using the default interaction).
     */
    public boolean disableSlideKill;

    /**
     * Bit 3 of $190F, whether the sprite will need 1 or 5 fireballs in order
     * to be killed.
     */
    public boolean needsFiveFireballs;

    /**
     * Bit 4 of $190F, whether the player can jump on this sprite with upward
     * (negative) vertical speed without getting hurt.
     */
    public boolean canBeJumpedOnFromBelow;

    /**
     * Bit 5 of $190F, whether the death frame of the sprite is 1 or 2 tiles
     * high.
     */
    public boolean tallDeathFrame;

    /**
     * Bit 6 of $190F, whether the sprite will turn into a coin when a silver
     * P-Switch is pressed.
     */
    public boolean immuneToSilverPOW;

    /**
     * Bits 7 of $190F, whether the sprite will push itself out of a wall when
     * it is carryable.
     */
    public boolean escapeWalls;

    /**
     * Packs all six Tweaker bytes into an array of integers.
     *
     * @return the packed array in the order $1656, $1662, $166E, $167A, $1686, $190F
     */
    public int[] pack(){
        return new int[]{
            (this.objectClipping & 0xF)
                | (this.canBeJumpedOn ? 0x10 : 0x0)
                | (this.diesWhenJumpedOn ? 0x20 : 0x0)
                | (this.hopInShells ? 0x40 : 0x0)
                | (this.disappearInSmoke ? 0x80 : 0x0),
            (this.spriteClipping & 0x3F)
                | (this.useShellAsDeathFrame ? 0x40 : 0x0)
                | (this.fallsStraightDownWhenKilled ? 0x80 : 0x0),
            (this.useSecondGraphicsPage ? 0x1 : 0x0)
                | (this.palette & 0x7 << 0x1)
                | (this.disableFireballKilling ? 0x10 : 0x0)
                | (this.disableCapeKilling ? 0x20 : 0x0)
                | (this.disableWaterSplash ? 0x40 : 0x0)
                | (this.disableSecondaryInteraction ? 0x90 : 0x0),
            (this.processIfDead ? 0x1 : 0x0)
                | (this.invincibleToPlayer ? 0x2 : 0x0)
                | (this.processWhileOffscreen ? 0x4 : 0x0)
                | (this.skipShellIfStunned ? 0x8 : 0x0)
                | (this.disableKicking ? 0x10 : 0x0)
                | (this.processInteractionEveryFrame ? 0x20 : 0x0)
                | (this.isPowerup ? 0x40 : 0x0)
                | (this.disableDefaultInteraction ? 0x80 : 0x0),
            (this.disableObjectInteraction ? 0x1 : 0x0)
                | (this.spawnsNewSprite ? 0x2 : 0x0)
                | (this.disappearWhenGoalPassed ? 0x4 : 0x0)
                | (this.preserveDirection ? 0x8 : 0x0)
                | (this.disableSpriteInteraction ? 0x10 : 0x0)
                | (this.weirdGroundBehavior ? 0x20 : 0x0)
                | (this.stayInMouth ? 0x40 : 0x0)
                | (this.inedible ? 0x80 : 0x0),
            (this.escapeWalls ? 0x1 : 0x0)
                | (this.immuneToSilverPOW ? 0x2 : 0x0)
                | (this.tallDeathFrame ? 0x4 : 0x0)
                | (this.canBeJumpedOnFromBelow ? 0x8 : 0x0)
                | (this.needsFiveFireballs ? 0x10 : 0x0)
                | (this.disableSlideKill ? 0x20 : 0x0)
                | (this.ignoreGoal ? 0x40 : 0x0)
                | (this.platformPassableFromBelow ? 0x80 : 0x0)
        };
    }
}
