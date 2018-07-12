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
 * The {@code DecimalFormatter} is a formatter for {@link JFormattedTextField}s
 * which will format decimal integers into {@code String}s and vice-versa.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class DecimalFormatter extends JFormattedTextField.AbstractFormatter {
    /**
     * The bounds of the integer.
     */
    private Bounds bounds;

    /**
     * Creates an unbounded decimal formatter.
     */
    public DecimalFormatter(){
        this(new Bounds(Integer.MIN_VALUE, Integer.MAX_VALUE));
    }

    /**
     * Creates a decimal formatter with specific bounds.
     */
    public DecimalFormatter(Bounds bounds){
        this.bounds = bounds;
    }

    /**
     * Creates a decimal formatter with specific bounds.
     *
     * @param min the lower bound
     * @param max the upper bound
     */
    public DecimalFormatter(int min, int max){
        this(new Bounds(min, max));
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
        field.setFormatterFactory(new DefaultFormatterFactory(new DecimalFormatter(min, max)));

        if(listener != null){
            DecimalFormatter.addListener(field, listener);
        }

        return field;
    }

    /**
     * Adds a property listener to the given {@code JFormattedField}. The given
     * {@code Consumer} will receive the formatted integer value from the
     * field, not the {@link java.beans.PropertyChangeEvent}.
     *
     * @param field the field to add the listener to
     * @param listener the consumer to call with the integer value
     * @return the field, for chaining
     */
    public static JFormattedTextField addListener(JFormattedTextField field, Consumer<Integer> listener){
        field.addPropertyChangeListener("value", event -> {
            JFormattedTextField source = (JFormattedTextField)event.getSource();
            Number value = (Number)source.getValue();

            listener.accept(value.intValue());
        });

        return field;
    }

    /**
     * Returns the formatter's bounds.
     */
    public Bounds getBounds(){
        return this.bounds;
    }

    /**
     * Sets new bounds for the formatter.
     *
     * @param bounds the new bounds to set
     * @return the formatter, for chaining
     */
    public DecimalFormatter setBounds(Bounds bounds){
        this.bounds = bounds;
        return this;
    }

    /**
     * Sets new bounds for the formatter.
     *
     * @param min the new lower bound
     * @param max the new upper bound
     * @return the formatter, for chaining
     */
    public DecimalFormatter setBounds(int min, int max){
        return this.setBounds(new Bounds(min, max));
    }

    /**
     * Makes the formatter unbounded.
     *
     * @return the formatter, for chaining
     */
    public DecimalFormatter setUnbounded(){
        return this.setBounds(new Bounds(Integer.MIN_VALUE, Integer.MAX_VALUE));
    }

    @Override
    public Object stringToValue(String text) throws ParseException{
        try {
            return this.getBounds().clamp(Integer.valueOf(text, 10));
        }catch(NumberFormatException exception){
            throw new ParseException("Invalid decimal number.", 0);
        }
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if(value instanceof Number){
            value = ((Number)value).intValue();
        }

        if(value instanceof Integer){
            return Integer.toString((Integer)value, 10);
        }

        throw new ParseException("The value must be a number.", 0);
    }
}
