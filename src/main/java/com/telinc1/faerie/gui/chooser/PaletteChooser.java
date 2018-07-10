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

package com.telinc1.faerie.gui.chooser;

import com.telinc1.faerie.Application;
import com.telinc1.faerie.Resources;
import com.telinc1.faerie.gui.chooser.filter.MW3Filter;
import com.telinc1.faerie.gui.chooser.filter.PALFilter;
import com.telinc1.faerie.gui.chooser.filter.PaletteFilter;
import com.telinc1.faerie.gui.chooser.filter.TPLFilter;

import java.awt.Component;

/**
 * This is an {@link ApplicationChooser} which allows the user to select any
 * supported palette file.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class PaletteChooser extends ApplicationChooser {
    /**
     * The {@link java.io.FileFilter} for all palette files.
     */
    private final PaletteFilter paletteFilter;

    /**
     * The {@link java.io.FileFilter} for YY-CHR palette files.
     */
    private final PALFilter palFilter;

    /**
     * The {@link java.io.FileFilter} for Tile Layer Pro palette files.
     */
    private final TPLFilter tplFilter;

    /**
     * The {@link java.io.FileFilter} for Mario World palette files.
     */
    private final MW3Filter mw3Filter;

    /**
     * Creates a new {@code PaletteChooser}.
     */
    public PaletteChooser(Application application){
        super(application);

        this.paletteFilter = new PaletteFilter();
        this.palFilter = new PALFilter();
        this.tplFilter = new TPLFilter();
        this.mw3Filter = new MW3Filter();
    }

    /**
     * Shows a palette file chooser for opening a file.
     * <p>
     * This entails setting the choosable filters to allow all any palette
     * files, even the generalized filter.
     *
     * @return the return state of the file chooser
     * @see #showOpenDialog(Component)
     */
    public int showOpen(Component parent){
        this.setDialogTitle(Resources.getString("chooser", "chooser.palette.open"));

        this.resetChoosableFileFilters();
        this.addChoosableFileFilter(this.getPaletteFilter());
        this.addChoosableFileFilter(this.getPALFilter());
        this.addChoosableFileFilter(this.getTPLFilter());
        this.addChoosableFileFilter(this.getMW3Filter());
        this.setAcceptAllFileFilterUsed(false);

        return this.showOpenDialog(parent);
    }

    /**
     * Returns the {@link java.io.FileFilter} for all palette files.
     */
    public PaletteFilter getPaletteFilter(){
        return this.paletteFilter;
    }

    /**
     * Returns the {@link java.io.FileFilter} for PAL files.
     */
    public PALFilter getPALFilter(){
        return this.palFilter;
    }

    /**
     * Returns the {@link java.io.FileFilter} for TPL files.
     */
    public TPLFilter getTPLFilter(){
        return this.tplFilter;
    }

    /**
     * Returns the {@link java.io.FileFilter} for MW3 files.
     */
    public MW3Filter getMW3Filter(){
        return this.mw3Filter;
    }
}
