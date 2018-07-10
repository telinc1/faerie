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
import com.telinc1.faerie.Preferences;
import com.telinc1.faerie.gui.chooser.filter.IApplicationFilter;
import com.telinc1.faerie.util.TypeUtils;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * This {@link JFileChooser} implements functionality common to all file
 * choosers in the application.
 *
 * @author Telinc1
 * @since 1.0.0
 */
abstract class ApplicationChooser extends JFileChooser {
    /**
     * The {@code Application} of this chooser.
     */
    private final Application application;

    /**
     * Creates a new {@code ApplicationChooser}.
     */
    protected ApplicationChooser(Application application){
        this.application = application;

        String directory = this.getApplication().getPreferences().get(Preferences.LAST_DIRECTORY);

        if(directory != null){
            this.setCurrentDirectory(this.getFileSystemView().createFileObject(directory));
        }
    }

    /**
     * Returns the {@link Application} of this chooser.
     */
    public Application getApplication(){
        return this.application;
    }

    @Override
    public void setCurrentDirectory(File directory){
        super.setCurrentDirectory(directory);

        if(directory == null || this.getApplication() == null){
            return;
        }

        this.getApplication().getPreferences().set(
            Preferences.LAST_DIRECTORY,
            directory.getAbsolutePath()
        );
    }

    /**
     * Returns the selected {@link File} with the correct extension.
     *
     * @see JFileChooser#getSelectedFile()
     */
    public File getActualFile(){
        File selected = this.getSelectedFile();

        if(selected.isDirectory()){
            return selected;
        }

        String extension = TypeUtils.getExtension(selected);

        if(!extension.isEmpty()){
            return selected;
        }

        FileFilter filter = this.getFileFilter();

        if(!(filter instanceof IApplicationFilter)){
            return selected;
        }

        return this.getFileSystemView().createFileObject(selected.getAbsolutePath() + "." + ((IApplicationFilter)filter).getExtension());
    }

    @Override
    public void setSelectedFile(File file){
        if(file == null){
            this.getApplication().getPreferences().set(
                Preferences.LAST_DIRECTORY,
                this.getCurrentDirectory().getAbsolutePath()
            );
        }else{
            this.getApplication().getPreferences().set(
                Preferences.LAST_DIRECTORY,
                file.getAbsolutePath()
            );
        }

        super.setSelectedFile(file);
    }
}
