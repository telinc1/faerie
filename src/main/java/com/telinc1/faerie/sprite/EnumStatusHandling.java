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
 * Defines the way a custom sprite treats its status.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public enum EnumStatusHandling {
    /**
     * Denotes a sprite which handles its stunned statuses.
     * <p>
     * Statuses 0x1 (initializing) and 0x8 (alive) always run custom code with
     * no default handling.
     * <p>
     * A custom sprite with this handling will run its ASM code after the
     * default handling of statuses 0x3 (stomped), 0x9 (carryable),
     * 0xA (kicked), and 0xB (carried).
     * <p>
     * All other statuses will run the default handling without running the
     * custom ASM code.
     */
    HANDLE_STUNNED(0),

    /**
     * Denotes a sprite which handles all of its statuses.
     * <p>
     * Statuses 0x1 (initializing) and 0x8 (alive) always run custom code with
     * no default handling.
     * <p>
     * A custom sprite with this handling will run its ASM code after the
     * default handling of its status, regardless of what its status is.
     */
    HANDLE_ALL(1),

    /**
     * Denotes a custom sprite which overrides all of its statuses.
     * <p>
     * A custom sprite with this handling will always run its custom ASM code
     * regardless of its status. Default handling will never run.
     */
    OVERRIDE_ALL(2);

    /**
     * The bits which identify this handling.
     */
    private final int bits;

    /**
     * Construct an element of the enum.
     *
     * @param bits the bits which identify the handling
     */
    EnumStatusHandling(int bits){
        this.bits = bits;
    }

    /**
     * Returns the status handling which corresponds to the given bits.
     *
     * @param bits the bits to identify, only the lower two are used
     * @return the enum element which corresponds to the bits
     */
    public static EnumStatusHandling fromBits(int bits){
        switch(bits & 0b11){
            case 0:
                return EnumStatusHandling.HANDLE_STUNNED;
            case 1:
                return EnumStatusHandling.HANDLE_ALL;
            default:
                return EnumStatusHandling.OVERRIDE_ALL;
        }
    }

    /**
     * Returns the bits which identify the handling.
     *
     * @return an integer in the range [0; 3] which identifies this handling
     */
    public int getBits(){
        return this.bits;
    }
}
