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

import com.telinc1.faerie.Notifier;
import com.telinc1.faerie.Resources;
import com.telinc1.faerie.util.locale.ILocalizable;

import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * The {@code DialogNotifier} handles all notifications using visual message
 * dialogs, optionally pertaining a specific frame.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class DialogNotifier extends Notifier {
    /**
     * The {@code GraphicalInterface} which owns this {@code DialogNotifier}.
     */
    private final GraphicalInterface graphicalInterface;

    /**
     * Creates a {@code DialogNotifier} for a {@code GraphicalInterface}.
     */
    public DialogNotifier(GraphicalInterface graphicalInterface){
        super(graphicalInterface.getApplication());
        this.graphicalInterface = graphicalInterface;
    }

    @Override
    public void notify(ILocalizable source){
        super.notify(this.getInterface().getWindow(), source);
    }

    /**
     * Returns the {@code GraphicalInterface} which owns and uses this
     * {@code DialogNotifier}.
     */
    public GraphicalInterface getInterface(){
        return this.graphicalInterface;
    }

    @Override
    public void info(String resource, String subkey, Object... arguments){
        super.info(this.getInterface().getWindow(), resource, subkey, arguments);
    }

    @Override
    public void warn(String resource, String subkey, Object... arguments){
        super.warn(this.getInterface().getWindow(), resource, subkey, arguments);
    }

    @Override
    public void error(String resource, String subkey, Object... arguments){
        super.error(this.getInterface().getWindow(), resource, subkey, arguments);
    }

    @Override
    public void fatal(String resource, String subkey, Object... arguments){
        super.fatal(this.getInterface().getWindow(), resource, subkey, arguments);
    }

    @Override
    @SuppressWarnings("MagicConstant")
    protected void showNotification(Object parent, ILocalizable source){
        JOptionPane.showMessageDialog(
            parent instanceof Component ? (Component)parent : null,
            Resources.getString(
                source.getResource(),
                source.getSeverity().getKey() + "." + source.getSubkey() + ".content",
                source.getArguments()
            ),
            Resources.getString(
                source.getResource(),
                source.getSeverity().getKey() + "." + source.getSubkey() + ".title",
                source.getArguments()
            ),
            source.getSeverity().getMessageType()
        );
    }
}
