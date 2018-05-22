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

package com.telinc1.faerie.util.notification;

import com.telinc1.faerie.Application;
import com.telinc1.faerie.Resources;
import com.telinc1.faerie.util.locale.ILocalizable;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The {@code Notifier} handles the creation and management of message dialogs.
 */
public class Notifier {
    /**
     * The main window used by the {@code Notifier}.
     */
    private final Application application;

    /**
     * Creates a {@code Notifier} for the an application.
     *
     * @param application the {@code Application} to notify for
     */
    public Notifier(Application application){
        this.application = application;
    }

    /**
     * Shows a notification dialog to the user.
     * <p>
     * If the severity of the message is {@link EnumSeverity#FATAL}, the
     * application will quit with exit code 1.
     *
     * @param source the source to display from
     */
    public void notify(ILocalizable source){
        this.notify(this.getApplication().getWindow(), source);
    }

    /**
     * Shows a notification dialog to the user.
     * <p>
     * If the severity of the message is {@link EnumSeverity#FATAL}, the
     * application will quit with exit code 1.
     *
     * @param parent the frame which the dialog will belong to
     * @param source the source to display from
     */
    @SuppressWarnings("MagicConstant")
    public void notify(JFrame parent, ILocalizable source){
        String title = Resources.getString(
            source.getResource(),
            source.getSeverity().getKey() + "." + source.getSubkey() + ".title",
            source.getArguments()
        );

        String content = Resources.getString(
            source.getResource(),
            source.getSeverity().getKey() + "." + source.getSubkey() + ".content",
            source.getArguments()
        );

        JOptionPane.showMessageDialog(parent, content, title, source.getSeverity().getMessageType());

        if(source.getSeverity() == EnumSeverity.FATAL){
            System.exit(1);
        }
    }

    /**
     * Returns the application used by the {@code Notifier}.
     *
     * @return the {@link Application} which the {@code Notifier} serves
     */
    public Application getApplication(){
        return this.application;
    }

    /**
     * Shows an information dialog.
     *
     * @param resource the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void info(String resource, String subkey, Object... arguments){
        this.info(this.getApplication().getWindow(), resource, subkey, arguments);
    }

    /**
     * Shows an information dialog.
     *
     * @param parent the {@code JFrame} which will own the dialog
     * @param resource the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void info(JFrame parent, String resource, String subkey, Object... arguments){
        this.notify(parent, this.createMessage(EnumSeverity.INFO, resource, subkey, arguments));
    }

    /**
     * Creates an anonymous localizable message.
     *
     * @param severity the severity of the message
     * @param resource the string resource of the message
     * @param subkey the subkey of the message
     * @param arguments the arguments for the message
     * @return the anonymous {@code ILocalizable} message
     */
    private ILocalizable createMessage(EnumSeverity severity, String resource, String subkey, Object... arguments){
        return new ILocalizable() {
            @Override
            public String getResource(){
                return resource;
            }

            @Override
            public EnumSeverity getSeverity(){
                return severity;
            }

            @Override
            public String getSubkey(){
                return subkey;
            }

            @Override
            public Object[] getArguments(){
                return arguments;
            }
        };
    }

    /**
     * Shows a warning information dialog.
     *
     * @param resource the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void warn(String resource, String subkey, Object... arguments){
        this.warn(this.getApplication().getWindow(), resource, subkey, arguments);
    }

    /**
     * Shows a warning information dialog.
     *
     * @param parent the {@code JFrame} which will own the dialog
     * @param resource the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void warn(JFrame parent, String resource, String subkey, Object... arguments){
        this.notify(parent, this.createMessage(EnumSeverity.WARNING, resource, subkey, arguments));
    }

    /**
     * Shows an error information dialog.
     *
     * @param resource the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void error(String resource, String subkey, Object... arguments){
        this.error(this.getApplication().getWindow(), resource, subkey, arguments);
    }

    /**
     * Shows an error information dialog.
     *
     * @param parent the {@code JFrame} which will own the dialog
     * @param resource the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void error(JFrame parent, String resource, String subkey, Object... arguments){
        this.notify(parent, this.createMessage(EnumSeverity.ERROR, resource, subkey, arguments));
    }

    /**
     * Shows a fatal information dialog.
     *
     * @param resource the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void fatal(String resource, String subkey, Object... arguments){
        this.fatal(this.getApplication().getWindow(), resource, subkey, arguments);
    }

    /**
     * Shows a fatal information dialog.
     *
     * @param parent the {@code JFrame} which will own the dialog
     * @param resource the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void fatal(JFrame parent, String resource, String subkey, Object... arguments){
        this.notify(parent, this.createMessage(EnumSeverity.FATAL, resource, subkey, arguments));
    }
}
