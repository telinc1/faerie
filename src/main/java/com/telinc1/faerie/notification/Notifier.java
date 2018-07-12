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

package com.telinc1.faerie.notification;

import com.telinc1.faerie.Application;
import com.telinc1.faerie.util.locale.ILocalizable;

/**
 * The {@code Notifier} class provides an implementation for handling various
 * notifications from the application, such as information messages, warnings,
 * and errors.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public abstract class Notifier {
    /**
     * The application which uses this {@code Notifier}.
     */
    private final Application application;

    /**
     * Creates a {@code Notifier} for the an {@code Application}.
     */
    public Notifier(Application application){
        this.application = application;
    }

    /**
     * Shows a notification to the user. If the severity of the message is
     * {@link EnumSeverity#FATAL}, the application should exit with status 1.
     *
     * @param source the source to handle from
     */
    public void notify(ILocalizable source){
        this.notify(null, source);
    }

    /**
     * Shows a notification to the user. If the severity of the message is
     * {@link EnumSeverity#FATAL}, the application will exit with status 1.
     * <p>
     * If the implementation can properly use the provided {@code parent}, the
     * message will be its child in any appropriate manner.
     *
     * @param parent the parent of the notification, can be {@code null}
     * @param source the source to handle from
     */
    public final void notify(Object parent, ILocalizable source){
        this.showNotification(parent, source);

        if(source.getSeverity() == EnumSeverity.FATAL){
            this.getApplication().exit(1);
        }
    }

    /**
     * Shows a notification to the user. If the implementation can properly use
     * the provided {@code parent}, the message should be its child in any
     * appropriate manner.
     *
     * @param parent the parent of the notification, can be {@code null}
     * @param source the source to handle from
     */
    protected abstract void showNotification(Object parent, ILocalizable source);

    /**
     * Returns the {@code Application} used by the {@code Notifier}.
     */
    public Application getApplication(){
        return this.application;
    }

    /**
     * Shows a localized information message to the user.
     *
     * @param resource the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void info(String resource, String subkey, Object... arguments){
        this.info(null, resource, subkey, arguments);
    }

    /**
     * Shows a localized information message to the user.
     *
     * @param parent the parent of the notification, can be {@code null}
     * @param resource the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void info(Object parent, String resource, String subkey, Object... arguments){
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
     * Shows a localized warning to the user.
     *
     * @param resource the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void warn(String resource, String subkey, Object... arguments){
        this.warn(null, resource, subkey, arguments);
    }

    /**
     * Shows a localized warning to the user.
     *
     * @param parent the parent of the notification, can be {@code null}
     * @param resource the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void warn(Object parent, String resource, String subkey, Object... arguments){
        this.notify(parent, this.createMessage(EnumSeverity.WARNING, resource, subkey, arguments));
    }

    /**
     * Shows a localized error message to the user.
     *
     * @param resource the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void error(String resource, String subkey, Object... arguments){
        this.error(null, resource, subkey, arguments);
    }

    /**
     * Shows a localized error message to the user.
     *
     * @param parent the parent of the notification, can be {@code null}
     * @param resource the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void error(Object parent, String resource, String subkey, Object... arguments){
        this.notify(parent, this.createMessage(EnumSeverity.ERROR, resource, subkey, arguments));
    }

    /**
     * Shows a localized fatal error message to the user.
     *
     * @param resource the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void fatal(String resource, String subkey, Object... arguments){
        this.fatal(null, resource, subkey, arguments);
    }

    /**
     * Shows a localized fatal error message to the user.
     *
     * @param parent the parent of the notification, can be {@code null}
     * @param resource the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void fatal(Object parent, String resource, String subkey, Object... arguments){
        this.notify(parent, this.createMessage(EnumSeverity.FATAL, resource, subkey, arguments));
    }
}
