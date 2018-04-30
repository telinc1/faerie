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

package com.telinc1.faerie.gui;

import com.telinc1.faerie.util.Bounds;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import java.text.ParseException;

/**
 * Formats hexadecimal numbers into strings.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class HexadecimalFormatter extends JFormattedTextField.AbstractFormatter {
    private Bounds bounds;

    /**
     * Creates an unbounded hexadecimal formatter.
     */
    public HexadecimalFormatter(){
        this(new Bounds(Integer.MIN_VALUE, Integer.MAX_VALUE));
    }

    /**
     * Creates a hexadecimal formatter with the given bounds.
     *
     * @param bounds the bounds of the integer
     */
    public HexadecimalFormatter(Bounds bounds){
        this.bounds = bounds;
    }

    /**
     * Creates a hexadecimal formatter with the given bounds.
     *
     * @param min the lower bound
     * @param max the upper bound
     */
    public HexadecimalFormatter(int min, int max){
        this(new Bounds(min, max));
    }

    /**
     * Applies the formatter to a text field.
     *
     * @param field the field to apply to
     * @param min the lower bound of the value
     * @param max the upper bound of the value
     */
    public static void apply(JFormattedTextField field, int min, int max){
        field.setFormatterFactory(new DefaultFormatterFactory(new HexadecimalFormatter(min, max)));
    }

    /**
     * Makes the formatter unbounded.
     *
     * @return the formatter, for chaining
     */
    public HexadecimalFormatter setUnbounded(){
        return this.setBounds(new Bounds(Integer.MIN_VALUE, Integer.MAX_VALUE));
    }

    /**
     * Sets new bounds for the formatter.
     *
     * @param min the new lower bound
     * @param max the new upper bound
     * @return the formatter, for chaining
     */
    public HexadecimalFormatter setBounds(int min, int max){
        return this.setBounds(new Bounds(min, max));
    }

    /**
     * Sets new bounds for the formatter.
     *
     * @param bounds the new bounds to set
     * @return the formatter, for chaining
     */
    public HexadecimalFormatter setBounds(Bounds bounds){
        this.bounds = bounds;
        return this;
    }

    @Override
    public Object stringToValue(String text) throws ParseException{
        try {
            return this.getBounds().clamp(Integer.valueOf(text, 16));
        }catch(NumberFormatException exception){
            throw new ParseException("Invalid hexadecimal number.", 0);
        }
    }

    /**
     * Returns the formatter's bounds.
     *
     * @return the {@link Bounds} of the formatter
     */
    public Bounds getBounds(){
        return this.bounds;
    }

    @Override
    public String valueToString(Object value) throws ParseException{
        if(value instanceof Number){
            value = ((Number)value).intValue();
        }

        if(value instanceof Integer){
            return Integer.toHexString((Integer)value).toUpperCase();
        }

        throw new ParseException("The value must be a number.", 0);
    }
}
