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

package com.telinc1.faerie.util.locale;

import com.telinc1.faerie.gui.DialogNotifier;
import com.telinc1.faerie.util.EnumSeverity;

/**
 * The {@code ILocalizable} interface can be implemented by a class to allow it
 * to be directly displayed through the application's {@link DialogNotifier}.
 */
public interface ILocalizable {
    /**
     * Returns the resource for this class's message.
     *
     * @return the non-null resource for this class's message
     */
    String getResource();

    /**
     * Returns the class's severity.
     * <p>
     * The severity determines what type of message will be displayed for this
     * class and what its key will be.
     *
     * @return the severity of the class's message
     */
    EnumSeverity getSeverity();

    /**
     * Returns the subkey for this class's message.
     * <p>
     * The {@link #getSeverity() severity} of the class determines what the
     * message's icon and parent key will be.
     *
     * @return the non-null subkey for this class's message
     */
    String getSubkey();

    /**
     * Returns the arguments passed to this class's message.
     *
     * @return the array of arbitrary arguments to pass to the class's message
     */
    Object[] getArguments();
}
