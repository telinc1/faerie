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

import com.telinc1.faerie.Resources;

/**
 * A {@code ParseException} is thrown when a configuration file for a sprite is
 * malformed or has invalid data.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class ParseException extends Exception {
    /**
     * The subkey from the {@code parse} bundle to use for the message.
     */
    private final String subkey;

    /**
     * The arguments to format the exception's message with.
     */
    private final Object[] arguments;

    /**
     * Constructs a {@code ParseException} with an optional cause.
     * <p>
     * The messages will be taken from the {@code parse} resource bundle.
     *
     * @param message the internal message of the exception
     * @param subkey the key for the exception message from {@code parse}
     * @param cause the cause of the exception
     * @param arguments the arguments for the message
     */
    public ParseException(String message, String subkey, Throwable cause, Object... arguments){
        super(message, cause);
        this.subkey = subkey;
        this.arguments = arguments;
    }

    @Override
    public String getLocalizedMessage(){
        return Resources.getString("parse", this.subkey, this.arguments);
    }
}
