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

package com.telinc1.faerie.util;

import com.telinc1.faerie.EnumSeverity;
import com.telinc1.faerie.ILocalizable;

/**
 * A {@code Warning} is a localizable message which can be created during the
 * application's lifetime. It is, in a way, a non-throwable, non-error
 * exception.
 */

public class Warning implements ILocalizable {
    /**
     * The resource from which the warning message is read.
     */
    private String resource;

    /**
     * The key from which the warning message is read.
     */
    private String subkey;

    /**
     * The arguments which will be passed down to the message.
     */
    private Object[] arguments;

    /**
     * Constructs a {@code Warning} with a specific message.
     *
     * @param resource the string resource to look in
     * @param subkey the key in the resource to grab
     * @param arguments the arguments to format the message with
     */
    public Warning(String resource, String subkey, Object... arguments){
        this.resource = resource;
        this.subkey = subkey;
        this.arguments = arguments;
    }

    @Override
    public String getResource(){
        return this.resource;
    }

    @Override
    public EnumSeverity getSeverity(){
        return EnumSeverity.WARNING;
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
