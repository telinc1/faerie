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
import com.telinc1.faerie.display.UpdateListener;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * {@code JPaletteView} is a Swing component which displays a rectangular
 * region of a 256-color SNES palette. It will automatically listen for updates
 * to the palettes and repaint itself if they happen.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class JPaletteView extends JComponent {
    /**
     * The size of a single cell.
     */
    private Dimension cellSize;

    /**
     * The palette to handle from.
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
     * An {@code UpdateListener} which will repaint this {@code JPaletteView}.
     */
    private final UpdateListener updateListener;

    /**
     * Constructs a palette view with 16x16 cells and a 1x1 display area at the
     * very top left of the palette. This can be changed later.
     */
    public JPaletteView(){
        this(new Dimension(16, 16), 0, new Dimension(1, 1));
    }

    /**
     * Constructs a palette view with a specific cell width and height.
     *
     * @param cellSize the size of a single cell
     * @param firstIndex the first index of the palette to handle
     * @param regionSize the amount of the palette to handle
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

        this.updateListener = event -> this.repaint();

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
     * Returns the palette displayed by the {@code PaletteView}. It can be
     * {@code null}.
     */
    public Palette getPalette(){
        return this.palette;
    }

    /**
     * Sets a new palette to display colors from. If the palette is
     * {@code null}, the whole component will be rendered as black.
     * The {@link #updateListener} will be automatically added to
     * non-{@code null} palettes.
     *
     * @param palette the new palette to display, or {@code null}
     * @return the component, for chaining
     */
    public JPaletteView setPalette(Palette palette){
        if(this.getPalette() != null){
            this.getPalette().removeUpdateListener(this.updateListener);
        }

        this.palette = palette;

        if(this.getPalette() != null){
            this.getPalette().addUpdateListener(this.updateListener);
        }

        this.repaint();
        return this;
    }

    /**
     * Returns the size of a single cell. The returned object is mutable and
     * live. If you modify it directly, make sure to call {@link #updateSize()}
     * afterwards.
     *
     * @return the size of a single cell
     */
    public Dimension getCellSize(){
        return this.cellSize;
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
     * Returns the first index displayed by the palette view. This is the index
     * displayed at the very top left of the rectangular region.
     */
    public int getFirstIndex(){
        return this.firstIndex;
    }

    /**
     * Sets a new first index to handle.
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

    /**
     * Returns the current region size of the {@code PaletteView}.
     *
     * @return the region size
     */
    public Dimension getRegionSize(){
        return this.regionSize;
    }

    /**
     * Sets a new palette region to handle.
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

    /**
     * Updates the region displayed by the {@code PaletteView}. A single unit
     * on the rectangle refers to a single color from the palette. Invalid
     * colors are displayed as black.
     *
     * @param firstIndex the new first index to handle
     * @param width the new area width to handle
     * @param height the new area height to handle
     * @return the component, for chaining
     * @throws IllegalAccessError if the index is out of bounds or the size is non-positive
     */
    public JPaletteView setRegion(int firstIndex, int width, int height){
        return this.setFirstIndex(firstIndex).setRegionSize(width, height);
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
}
