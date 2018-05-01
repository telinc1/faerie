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
import java.util.function.Consumer;

/**
 * Formats hexadecimal numbers into strings.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class HexadecimalFormatter extends DecimalFormatter {
    /**
     * Creates an unbounded hexadecimal formatter.
     */
    public HexadecimalFormatter(){
        super();
    }

    /**
     * Creates a hexadecimal formatter with the given bounds.
     *
     * @param bounds the bounds of the integer
     */
    public HexadecimalFormatter(Bounds bounds){
        super(bounds);
    }

    /**
     * Creates a hexadecimal formatter with the given bounds.
     *
     * @param min the lower bound
     * @param max the upper bound
     */
    public HexadecimalFormatter(int min, int max){
        super(min, max);
    }

    /**
     * Applies the formatter to a text field.
     *
     * @param field the field to apply to
     * @param min the lower bound of the value
     * @param max the upper bound of the value
     * @param listener the consumer to call when the field's value changes
     * @return the field, for chaining
     */
    public static JFormattedTextField apply(JFormattedTextField field, int min, int max, Consumer<Integer> listener){
        field.setFormatterFactory(new DefaultFormatterFactory(new HexadecimalFormatter(min, max)));

        if(listener != null){
            DecimalFormatter.addListener(field, listener);
        }

        return field;
    }

    @Override
    public Object stringToValue(String text) throws ParseException{
        try {
            return this.getBounds().clamp(Integer.valueOf(text, 16));
        }catch(NumberFormatException exception){
            throw new ParseException("Invalid hexadecimal number.", 0);
        }
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
