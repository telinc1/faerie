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

package com.telinc1.faerie.math;

/**
 * Specifies a lower and an upper bound for an integer.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class Bounds {
    /**
     * The lower bound of the integer.
     */
    private int min;

    /**
     * The upper bound of the integer.
     */
    private int max;

    /**
     * Creates a new integer bound.
     *
     * @param min the lower bound
     * @param max the upper bound
     */
    public Bounds(int min, int max){
        this.min = min;
        this.max = max;
    }

    /**
     * Sets a new bound for the integer.
     *
     * @param min the new lower bound
     * @param max the new upper bound
     * @return the object, for chaining
     */
    public Bounds setBounds(int min, int max){
        return this.setMin(min).setMax(max);
    }

    /**
     * Clamps the given integer to these bounds.
     *
     * @param input the integer to clamp
     * @return the bounded integer
     */
    public int clamp(int input){
        return Math.min(Math.max(this.getMin(), input), this.getMax());
    }

    /**
     * Returns the lower bound of the integer.
     *
     * @return the lower bound
     */
    public int getMin(){
        return this.min;
    }

    /**
     * Sets the lower bound of the integer.
     *
     * @param min the new lower bound
     * @return the object, for chaining
     */
    public Bounds setMin(int min){
        this.min = min;
        return this;
    }

    /**
     * Returns the upper bound of the integer.
     *
     * @return the upper bound
     */
    public int getMax(){
        return this.max;
    }

    /**
     * Sets the upper bound of the integer.
     *
     * @param max the new upper bound
     * @return the object, for chaining
     */
    public Bounds setMax(int max){
        this.max = max;
        return this;
    }

    @Override
    public int hashCode(){
        return 31 * (this.getMin() + 31) + this.getMax();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Bounds){
            Bounds bounds = (Bounds)obj;
            return this.getMin() == bounds.getMin() && this.getMax() == bounds.getMax();
        }

        return false;
    }

    @Override
    public String toString(){
        return this.getClass().getName() + "[min=" + this.getMin() + ",max=" + this.getMax() + "]";
    }
}
