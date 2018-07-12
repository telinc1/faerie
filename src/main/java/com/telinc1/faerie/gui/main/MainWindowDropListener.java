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

package com.telinc1.faerie.gui.main;

import com.telinc1.faerie.notification.EnumSeverity;
import com.telinc1.faerie.util.locale.LocalizedException;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This class handles drag and drop events for the entire content panel of the
 * main Faerie window.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class MainWindowDropListener implements DropTargetListener {
    /**
     * The {@code MainWindow} which owns this drop listener.
     */
    private final MainWindow window;

    /**
     * Creates a new drop listener for the given {@code MainWindow}.
     */
    protected MainWindowDropListener(MainWindow window){
        this.window = window;
    }

    @Override
    public void dragEnter(DropTargetDragEvent event){}

    @Override
    public void dragOver(DropTargetDragEvent event){}

    @Override
    public void dropActionChanged(DropTargetDragEvent event){}

    @Override
    public void dragExit(DropTargetEvent event){}

    @Override
    @SuppressWarnings("unchecked")
    public void drop(DropTargetDropEvent event){
        event.acceptDrop(DnDConstants.ACTION_COPY);

        Transferable transferable = event.getTransferable();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();

        for(DataFlavor flavor : flavors){
            if(!flavor.isFlavorJavaFileListType()){
                continue;
            }

            try {
                List<File> files = (List<File>)transferable.getTransferData(flavor);

                if(!files.isEmpty()){
                    if(this.getWindow().getInterface().prepareToLoad()){
                        this.getWindow().getInterface().openFile(files.get(0));
                        event.dropComplete(true);
                    }

                    return;
                }
            }catch(IOException | UnsupportedFlavorException exception){
                this.getWindow().getInterface().getNotifier().notify(
                    this.getWindow(),
                    new LocalizedException(exception, EnumSeverity.ERROR, "main", "dragAndDrop")
                );
            }
        }
    }

    /**
     * Returns the {@code MainWindow} which owns this drop listener.
     */
    public MainWindow getWindow(){
        return this.window;
    }
}
