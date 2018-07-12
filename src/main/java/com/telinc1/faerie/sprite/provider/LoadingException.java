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

package com.telinc1.faerie.sprite.provider;

import com.telinc1.faerie.notification.EnumSeverity;
import com.telinc1.faerie.util.locale.ILocalizable;

/**
 * A {@code LoadingException} is thrown by a {@link Provider}'s constructor if
 * it can't load or parse the given input {@code File}.
 */
public class LoadingException extends Exception implements ILocalizable {
    /**
     * The exception's subkey from the "file" resource bundle.
     */
    private final String subkey;

    /**
     * The arguments to pass to the exception's message.
     */
    private final Object[] arguments;

    /**
     * Create a {@code LoadingException} with no defined message.
     */
    public LoadingException(){
        super();
        this.subkey = null;
        this.arguments = new Object[0];
    }

    /**
     * Create a {@code LoadingException} with no defined message and known
     * cause.
     *
     * @param cause the cause of the exception
     */
    public LoadingException(Throwable cause){
        super(cause);
        this.subkey = null;
        this.arguments = new Object[0];
    }

    /**
     * Create a {@code LoadingException} with a defined message and localizable
     * subkey.
     *
     * @param message the error message of the exception
     * @param subkey the subkey used when showing the exception to the user
     * @param arguments the arguments to pass to the exception's message
     */
    public LoadingException(String message, String subkey, Object... arguments){
        super(message);
        this.subkey = subkey;
        this.arguments = arguments;
    }

    /**
     * Create a {@code LoadingException} with a defined message, localizable
     * subkey, and cause.
     *
     * @param message the error message of the exception
     * @param subkey the subkey used when showing the exception to the user
     * @param cause the cause of the exception
     * @param arguments the arguments to pass to the exception's message
     */
    public LoadingException(String message, String subkey, Throwable cause, Object... arguments){
        super(message, cause);
        this.subkey = subkey;
        this.arguments = arguments;
    }

    @Override
    public String getResource(){
        return "file";
    }

    @Override
    public EnumSeverity getSeverity(){
        return EnumSeverity.ERROR;
    }

    @Override
    public String getSubkey(){
        return this.subkey == null ? "load" : "load." + this.subkey;
    }

    @Override
    public Object[] getArguments(){
        return this.arguments;
    }
}
