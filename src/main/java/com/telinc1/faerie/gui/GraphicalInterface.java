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

import com.telinc1.faerie.Application;
import com.telinc1.faerie.Resources;
import com.telinc1.faerie.UserInterface;
import com.telinc1.faerie.display.Palette;
import com.telinc1.faerie.gui.main.MainWindow;
import com.telinc1.faerie.sprite.provider.ConfigurationProvider;
import com.telinc1.faerie.sprite.provider.LoadingException;
import com.telinc1.faerie.sprite.provider.Provider;
import com.telinc1.faerie.sprite.provider.ProvisionException;
import com.telinc1.faerie.sprite.provider.ROMProvider;
import com.telinc1.faerie.sprite.provider.SavingException;
import com.telinc1.faerie.util.TypeUtils;
import com.telinc1.faerie.util.locale.LocalizedException;
import com.telinc1.faerie.util.locale.Warning;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.File;
import java.io.IOException;

/**
 * This class manages the {@link MainWindow}, a Swing-based graphical user
 * interface to the application.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class GraphicalInterface extends UserInterface {
    /**
     * The {@code MainWindow} owned and used by the interface.
     */
    private MainWindow window;

    /**
     * The currently loaded palette.
     */
    private Palette palette;

    /**
     * Creates a new {@code GraphicalInterface} for the given application.
     */
    public GraphicalInterface(Application application){
        super(application);
    }

    @Override
    public void init(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(ReflectiveOperationException | UnsupportedLookAndFeelException exception){
            this.getApplication().getExceptionHandler().report(exception);
            this.getApplication().getNotifier().warn("core", "launch.lookAndFeel", exception);
        }

        this.palette = new Palette();
        this.loadDefaultPalette();

        this.window = new MainWindow(this);
    }

    @Override
    public void start(){
        this.getWindow().setVisible(true);
    }

    /**
     * Returns the {@code MainWindow} owned and used by this interface.
     */
    public MainWindow getWindow(){
        return this.window;
    }

    /**
     * Loads the default colors into the interfaces's internal palette.
     */
    public void loadDefaultPalette(){
        try {
            this.getPalette().loadMW3File(Resources.getResource("data/generic.mw3"));
        }catch(IOException exception){
            throw new LocalizedException(exception, "core", "launch.palette");
        }
    }

    /**
     * Returns the currently loaded {@code Palette}.
     */
    public Palette getPalette(){
        return this.palette;
    }

    @Override
    public void openFile(File file){
        if(!this.unloadProvider()){
            return;
        }

        if(TypeUtils.isConfiguration(file)){
            ConfigurationProvider provider = new ConfigurationProvider(file);
            this.setProvider(provider);
        }else if(TypeUtils.isROM(file)){
            try {
                ROMProvider provider = new ROMProvider(file);
                this.setProvider(provider);
            }catch(LoadingException exception){
                this.getApplication().getExceptionHandler().handle(this.getWindow(), exception);
            }
        }else{
            this.getApplication().getNotifier().error(this.getWindow(), "file", "load.type");
        }
    }

    @Override
    public void saveProvider(File file){
        Provider provider = this.getProvider();

        if(provider == null){
            this.getApplication().getNotifier().error(this.getWindow(), "chooser", "save.blank");
            return;
        }

        Provider replacement = null;

        try {
            if(file == null){
                File input = provider.getInput();

                if(input != null){
                    replacement = provider.save(input);
                }else{
                    File save = this.getWindow().showSaveDialog();

                    if(save != null){
                        replacement = provider.save(save);
                    }else{
                        return;
                    }
                }
            }else{
                replacement = provider.save(file);
            }
        }catch(SavingException exception){
            this.getApplication().getExceptionHandler().report(exception);

            String message = exception.getMessage();

            if(exception.getCause() != null){
                message += " (" + exception.getCause().getClass().getName() + ")";
            }

            this.getApplication().getNotifier().error(this.getWindow(), "chooser", "save", "message", message);
        }

        if(replacement != null && this.unloadProvider()){
            this.setProvider(replacement);
        }
    }

    @Override
    public boolean unloadProvider(){
        if(this.prepareToLoad()){
            this.setProvider(null);
            return true;
        }

        return false;
    }

    @Override
    public UserInterface setProvider(Provider provider){
        super.setProvider(provider);

        try {
            this.getProvider().loadSprite(0);
        }catch(ProvisionException exception){
            this.getApplication().getExceptionHandler().handle(this.getWindow(), exception);
            return this.setProvider(null);
        }

        this.getWindow().updateInput();

        for(Warning warning : provider.getWarnings()){
            this.getApplication().getNotifier().notify(this.getWindow(), warning);
        }

        return this;
    }

    @Override
    public boolean stop(){
        return false;
    }

    /**
     * If a provider is currently loaded and is modified, this method will ask
     * the user to save their changes to it before loading another provider.
     * The user is also given the option to cancel the operation.
     *
     * @return if the new provider should be loaded
     */
    public boolean prepareToLoad(){
        Provider provider = this.getProvider();

        if(provider == null || !provider.isModified()){
            return true;
        }

        String[] options = Resources.getStrings("main", "dialog.option.yes", "dialog.option.no", "dialog.option.cancel");

        int option = JOptionPane.showOptionDialog(
            this.getWindow(),
            Resources.getString("main", "dialog.unsaved.content"),
            Resources.getString("main", "dialog.unsaved.title"),
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[2]
        );

        if(option == -1 || option == 2){
            return false;
        }

        if(option == 0){
            if(provider.getInput() != null){
                this.getWindow().getConfigurationChooser().setSelectedFile(provider.getInput());
            }

            File file = this.getWindow().showSaveDialog();

            if(file != null){
                this.saveProvider(file);
            }
        }

        return true;
    }
}
