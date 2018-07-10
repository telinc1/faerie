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

package com.telinc1.faerie.gui.chooser.filter;

import com.telinc1.faerie.Resources;
import com.telinc1.faerie.util.TypeUtils;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * The {@code ROMFilter} is a {@link FileFilter} for SNES ROM images.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class ROMFilter extends FileFilter implements IConfigurationFilter {
    @Override
    public boolean accept(File file){
        if(file.isDirectory()){
            return true;
        }

        return TypeUtils.isROM(file);
    }

    @Override
    public String getDescription(){
        return Resources.getString("chooser", "format.rom");
    }

    @Override
    public String getExtension(){
        return TypeUtils.TYPE_SMC_ROM;
    }
}
