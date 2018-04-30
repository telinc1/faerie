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

import com.telinc1.faerie.display.Palette;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * {@code JPaletteView} provides displays a rectangular part of palette.
 */
public class JPaletteView extends JComponent {
    /**
     * The size of a single cell.
     */
    private Dimension cellSize;

    /**
     * The palette to display from.
     */
    private Palette palette;

    /**
     * The first index of the palette which the {@code PaletteView} displays.
     */
    private int firstIndex;

    /**
     * The size of the region which is displayed by the {@code PaletteView}.
     * <p>
     * Each unit of the dimension refers to a single color. Invalid colors are
     * displayed as black.
     */
    private Dimension regionSize;

    /**
     * Constructs a palette view.
     * <p>
     * The cells will be 16x16 and only the first color of the palette will be
     * shown. This can be changed later.
     */
    public JPaletteView(){
        this(new Dimension(16, 16), 0, new Dimension(1, 1));
    }

    /**
     * Constructs a palette view with a specific cell width and height.
     *
     * @param cellSize the size of a single cell
     * @param firstIndex the first index of the palette to display
     * @param regionSize the amount of the palette to display
     * @throws IllegalArgumentException if the index is negative or either size is non-positive
     */
    public JPaletteView(Dimension cellSize, int firstIndex, Dimension regionSize){
        if(cellSize.width <= 0 || cellSize.height <= 0){
            throw new IllegalArgumentException("The cells must be at least 1x1 pixels in size!");
        }

        if(firstIndex < 0 || firstIndex > 255){
            throw new IllegalArgumentException("The first index must be between 0 and 255!");
        }

        if(regionSize.width <= 0 || regionSize.height <= 0){
            throw new IllegalArgumentException("The displayed region must be at least 1x1!");
        }

        this.cellSize = cellSize;
        this.firstIndex = firstIndex;
        this.regionSize = regionSize;
        this.updateSize();
    }

    /**
     * Updates the size components of the {@code JPaletteView} based on its
     * cell structure and size.
     *
     * @return the component, for chaining
     */
    public JPaletteView updateSize(){
        this.setMinimumSize(new Dimension(
            this.getCellSize().width * this.getRegionSize().width,
            this.getCellSize().height * this.getRegionSize().height
        ));
        this.setMaximumSize(null);
        this.setPreferredSize(null);

        this.repaint();
        return this;
    }

    /**
     * Returns the size of a single cell.
     * <p>
     * The returned object is live. If you modify it directly, make sure to
     * call {@link JPaletteView#updateSize()} afterwards.
     *
     * @return the size of a single cell
     */
    public Dimension getCellSize(){
        return this.cellSize;
    }

    /**
     * Returns the current region size of the {@code PaletteView}.
     *
     * @return the region size
     */
    public Dimension getRegionSize(){
        return this.regionSize;
    }

    /**
     * Sets a new width for a single cell.
     *
     * @param width the new width of a cell
     * @return the component, for chaining
     * @throws IllegalArgumentException if the width is non-positive
     */
    public JPaletteView setCellWidth(int width){
        if(width <= 0){
            throw new IllegalArgumentException("The cells must be at least 1x1 pixels in size!");
        }

        this.getCellSize().width = width;
        return this.updateSize();
    }

    /**
     * Sets a new height for a single cell.
     *
     * @param height the new height of a cell
     * @return the component, for chaining
     * @throws IllegalArgumentException if the height is non-positive
     */
    public JPaletteView setCellHeight(int height){
        if(height <= 0){
            throw new IllegalArgumentException("The cells must be at least 1x1 pixels in size!");
        }

        this.getCellSize().height = height;
        return this.updateSize();
    }

    /**
     * Sets a new size for a single cell.
     *
     * @param width the new width of a cell
     * @param height the new height of a cell
     * @return the component, for chaining
     * @throws IllegalArgumentException if the size is non-positive
     */
    public JPaletteView setCellSize(int width, int height){
        return this.setCellWidth(width).setCellHeight(height);
    }

    /**
     * Updates the region displayed by the {@code PaletteView}.
     * <p>
     * A single unit on the rectangle refers to a single color from the
     * palette. Invalid colors are displayed as black.
     *
     * @param firstIndex the new first index to display
     * @param width the new area width to display
     * @param height the new area height to display
     * @return the component, for chaining
     * @throws IllegalAccessError if the index is out of bounds or the size is non-positive
     */
    public JPaletteView setRegion(int firstIndex, int width, int height){
        return this.setFirstIndex(firstIndex).setRegionSize(width, height);
    }

    /**
     * Sets a new palette region to display.
     *
     * @param width the new width
     * @param height the new height
     * @return the component, for chaining
     */
    public JPaletteView setRegionSize(int width, int height){
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("The displayed region must be at least 1x1!");
        }

        this.getRegionSize().setSize(width, height);
        return this.updateSize();
    }

    @Override
    protected void paintComponent(Graphics destination){
        destination.setColor(Color.BLACK);
        destination.fillRect(0, 0, this.getWidth(), this.getHeight());

        Palette palette = this.getPalette();

        if(palette == null){
            return;
        }

        int cellWidth = this.getCellSize().width;
        int cellHeight = this.getCellSize().height;
        int firstIndex = this.getFirstIndex();
        int regionWidth = this.getRegionSize().width;
        int regionHeight = this.getRegionSize().height;

        for(int x = 0; x < regionWidth; x++){
            for(int y = 0; y < regionHeight; y++){
                try {
                    Color color = palette.getColor(firstIndex + x + y * 16);
                    destination.setColor(color);
                    destination.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                }catch(ArrayIndexOutOfBoundsException exception){
                    // do nothing, leave slot black
                }
            }
        }
    }

    /**
     * Returns the palette displayed by the {@code PaletteView}.
     *
     * @return a nullable {@link Palette} which provides the colors
     */
    public Palette getPalette(){
        return this.palette;
    }

    /**
     * Sets a new palette to display colors from.
     * <p>
     * If the palette is null, the whole component will be black.
     *
     * @param palette the new palette to display, or {@code null} to display nothing
     * @return the component, for chaining
     */
    public JPaletteView setPalette(Palette palette){
        this.palette = palette;
        this.repaint();

        return this;
    }

    /**
     * Returns the first index displayed by the palette view.
     *
     * @return the first index displayed
     */
    public int getFirstIndex(){
        return this.firstIndex;
    }

    /**
     * Sets a new first index to display.
     *
     * @param firstIndex the new first index
     * @return the component, for chaining
     * @throws IllegalArgumentException if the index is out of bounds
     */
    public JPaletteView setFirstIndex(int firstIndex){
        if(firstIndex < 0 || firstIndex > 255){
            throw new IllegalArgumentException("The first index must be between 0 and 255!");
        }

        this.firstIndex = firstIndex;
        this.repaint();
        return this;
    }
}
