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

package com.telinc1.faerie.util.notification;

import javax.swing.JOptionPane;

/**
 * {@code EnumSeverity} contains the possible severities of a localized
 * message.
 * <p>
 * The severity of a message determines its icon and the key of its title and
 * message.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public enum EnumSeverity {
    /**
     * Defines an information message.
     * <p>
     * Information messages are used to report general success or other pieces
     * of information which the user should know.
     */
    INFO("info", JOptionPane.INFORMATION_MESSAGE),

    /**
     * Defines a warning.
     * <p>
     * Warnings are unexpected events which don't normally affect the
     * application but should still be known.
     */
    WARNING("warning", JOptionPane.WARNING_MESSAGE),

    /**
     * Defines an error.
     * <p>
     * Errors are unexpected events which affect the user's workflow or the
     * application's stability, but are nonetheless recoverable.
     */
    ERROR("error", JOptionPane.ERROR_MESSAGE),

    /**
     * Defines a fatal error.
     * <p>
     * Fatal errors affect the application's stability to an unrecoverable
     * degree. The application will automatically quit after one is triggered.
     */
    FATAL("fatal", JOptionPane.ERROR_MESSAGE);

    /**
     * The key which all of this severity's messages use.
     */
    private final String key;

    /**
     * The message type of this severity's messages.
     *
     * @see javax.swing.JOptionPane
     */
    private final int messageType;

    /**
     * Constructs an enumeration element.
     *
     * @param key the key for this severity's messages
     * @param messageType the type of this severity's messages
     */
    EnumSeverity(String key, int messageType){
        this.key = key;
        this.messageType = messageType;
    }

    /**
     * Returns the key of this severity's messages.
     *
     * @return the key which all of this severity's messages start with
     */
    public String getKey(){
        return this.key;
    }

    /**
     * Returns the message type of this severity's messages.
     *
     * @return the type of this severity's message, one of
     * {@link javax.swing.JOptionPane} constants
     */
    public int getMessageType(){
        return this.messageType;
    }
}
