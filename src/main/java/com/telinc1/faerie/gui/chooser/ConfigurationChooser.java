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
import com.telinc1.faerie.gui.chooser.filter.CFGFilter;
import com.telinc1.faerie.gui.chooser.filter.ConfigurationFilter;
import com.telinc1.faerie.gui.chooser.filter.EditableFilter;
import com.telinc1.faerie.gui.chooser.filter.JSONFilter;
import com.telinc1.faerie.gui.chooser.filter.ROMFilter;

import java.awt.Component;

/**
 * This is an {@link ApplicationChooser} which allows the user to select any
 * supported configuration format (CFG and JSON for now) as well as,
 * optionally, ROMs.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class ConfigurationChooser extends ApplicationChooser {
    /**
     * The {@link java.io.FileFilter} for editable files.
     */
    private final EditableFilter editableFilter;

    /**
     * The {@link java.io.FileFilter} for configuration files.
     */
    private final ConfigurationFilter configurationFilter;

    /**
     * The {@link java.io.FileFilter} for CFG configuration files.
     */
    private final CFGFilter cfgFilter;

    /**
     * The {@link java.io.FileFilter} for JSON configuration files.
     */
    private final JSONFilter jsonFilter;

    /**
     * The {@link java.io.FileFilter} for SNES ROM image files.
     */
    private final ROMFilter romFilter;

    /**
     * Creates a new {@code ConfigurationChooser}.
     */
    public ConfigurationChooser(Application application){
        super(application);

        this.editableFilter = new EditableFilter();
        this.configurationFilter = new ConfigurationFilter();
        this.cfgFilter = new CFGFilter();
        this.jsonFilter = new JSONFilter();
        this.romFilter = new ROMFilter();
    }

    /**
     * Shows a configuration file chooser for opening a file.
     * <p>
     * This entails setting the choosable filters to allow ROM images and
     * setting an appropriate title for the chooser.
     *
     * @return the return state of the file chooser
     * @see #showOpenDialog(Component)
     */
    public int showOpen(Component parent){
        this.setDialogTitle(Resources.getString("chooser", "chooser.configuration.open"));

        this.resetChoosableFileFilters();
        this.addChoosableFileFilter(this.getEditableFilter());
        this.addChoosableFileFilter(this.getConfigurationFilter());
        this.addChoosableFileFilter(this.getCFGFilter());
        this.addChoosableFileFilter(this.getJSONFilter());
        this.addChoosableFileFilter(this.getROMFilter());
        this.setAcceptAllFileFilterUsed(false);

        return this.showOpenDialog(parent);
    }

    /**
     * Shows a configuration file chooser for saving a file.
     * <p>
     * This entails setting the choosable filters to disallow ROM images and
     * setting an appropriate title for the chooser.
     *
     * @return the return state of the file chooser
     * @see #showSaveDialog(Component)
     */
    public int showSave(Component parent){
        this.setDialogTitle(Resources.getString("chooser", "chooser.configuration.save"));

        this.resetChoosableFileFilters();
        this.addChoosableFileFilter(this.getCFGFilter());
        this.addChoosableFileFilter(this.getJSONFilter());
        this.setAcceptAllFileFilterUsed(false);

        return this.showSaveDialog(parent);
    }

    /**
     * Returns the filter for editable files.
     *
     * @return the {@link java.io.FileFilter} for editable files
     */
    public EditableFilter getEditableFilter(){
        return this.editableFilter;
    }

    /**
     * Returns the filter for configuration files.
     *
     * @return the {@link java.io.FileFilter} for configuration files
     */
    public ConfigurationFilter getConfigurationFilter(){
        return this.configurationFilter;
    }

    /**
     * Returns the filter for CFG configuration files.
     *
     * @return the {@link java.io.FileFilter} for CFG configuration files
     */
    public CFGFilter getCFGFilter(){
        return this.cfgFilter;
    }

    /**
     * Returns the filter for JSON configuration files.
     *
     * @return the {@link java.io.FileFilter} for JSON configuration files
     */
    public JSONFilter getJSONFilter(){
        return this.jsonFilter;
    }

    /**
     * Returns the filter for SNES ROM image files.
     *
     * @return the {@link java.io.FileFilter} for SNES ROM image files
     */
    public ROMFilter getROMFilter(){
        return this.romFilter;
    }
}
