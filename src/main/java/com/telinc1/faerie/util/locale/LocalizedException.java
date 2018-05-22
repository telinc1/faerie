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

import com.telinc1.faerie.util.notification.EnumSeverity;

/**
 * The {@code LocalizedException} is an unchecked wrapper around any other
 * exception which allows it to be thrown at runtime and localized when it's
 * displayed to the user.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class LocalizedException extends RuntimeException implements ILocalizable {
    /**
     * The resource from which the exception message is read.
     */
    private String resource;

    /**
     * The severity of this exception.
     */
    private EnumSeverity severity;

    /**
     * The key from which the exception message is read.
     */
    private String subkey;

    /**
     * The arguments which will be passed down to the message.
     */
    private Object[] arguments;

    /**
     * Constructs a {@code LocalizedException} which wraps an {@link Exception}
     * with a specific message.
     * <p>
     * The exception is assumed to be fatal and unrecoverable.
     *
     * @param cause the parent exception
     * @param resource the string resource to look in
     * @param subkey the key in the resource to grab
     * @param arguments the arguments to format the message with
     */
    public LocalizedException(Throwable cause, String resource, String subkey, Object... arguments){
        this(cause, EnumSeverity.FATAL, resource, subkey, arguments);
    }

    /**
     * Constructs a {@code LocalizedException} which wraps an {@link Exception}
     * with a specific message.
     *
     * @param cause the parent exception
     * @param resource the string resource to look in
     * @param severity the severity of the exception
     * @param subkey the key in the resource to grab
     * @param arguments the arguments to format the message with
     */
    public LocalizedException(Throwable cause, EnumSeverity severity, String resource, String subkey, Object... arguments){
        super(cause.getMessage(), cause);
        this.severity = severity;
        this.resource = resource;
        this.subkey = subkey;

        if(arguments.length <= 0){
            this.arguments = new Object[]{cause};
        }else{
            Object[] merged = new Object[arguments.length + 1];
            merged[0] = cause;

            System.arraycopy(arguments, 0, merged, 1, arguments.length);

            this.arguments = merged;
        }
    }

    @Override
    public String getResource(){
        return this.resource;
    }

    @Override
    public EnumSeverity getSeverity(){
        return this.severity;
    }

    /**
     * Sets a new severity for the exception.
     *
     * @param severity the new severity for the exception
     * @return the exception, for chaining
     */
    public LocalizedException setSeverity(EnumSeverity severity){
        this.severity = severity;
        return this;
    }

    @Override
    public String getSubkey(){
        return this.subkey;
    }

    @Override
    public Object[] getArguments(){
        return this.arguments;
    }
}
