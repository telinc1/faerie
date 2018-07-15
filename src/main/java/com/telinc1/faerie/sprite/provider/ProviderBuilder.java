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

package com.telinc1.faerie.sprite.provider;

import com.telinc1.faerie.Arguments;
import com.telinc1.faerie.util.EnumSeverity;
import com.telinc1.faerie.util.TypeUtils;
import com.telinc1.faerie.util.locale.ILocalizable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ProviderBuilder} class is used to create and pre-configure
 * {@link Provider}s for an arbitrary sprite, usually from command-line
 * {@link Arguments}.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class ProviderBuilder {
    /**
     * The {@code Provider} which is being configured by the
     * {@code ProviderBuilder}.
     */
    private Provider provider;

    /**
     * A {@code List} of any exceptions thrown while configuring the
     * {@link Provider}.
     */
    private final List<ILocalizable> errors;

    /**
     * Creates a new generic {@code ProviderBuilder}.
     */
    public ProviderBuilder(){
        this.errors = new ArrayList<>();
    }

    /**
     * Creates and pre-configures a {@link Provider} based on the given
     * command-line {@code Arguments}. All exceptions thrown during the
     * creation and configuration of the {@link Provider} are stored into the
     * {@code ProviderBuilder}.
     *
     * @see #getProvider()
     * @see #getErrors()
     */
    public void fromArguments(Arguments arguments){
        Provider provider;
        String path = String.join(" ", arguments.getTrailing());

        if(!path.isEmpty()){
            File file = new File(path);
            provider = this.loadFile(file);
        }else{
            provider = new BlankProvider();
        }

        try {
            provider.loadSprite(arguments.getSprite());
        }catch(ProvisionException exception){
            try {
                provider.loadSprite(0);
            }catch(ProvisionException inner){
                // java is on some really fuckened drugs if we ever get to here
                // good lord let's hope we never do
                this.getErrors().add(inner);

                provider = new BlankProvider();
            }

            this.getErrors().add(exception);
        }

        this.provider = provider;
    }

    /**
     * Returns the resultant {@code Provider} in its current state.
     */
    public Provider getProvider(){
        return this.provider;
    }

    /**
     * Returns all of the errors created while configuring the
     * {@link Provider}.
     */
    public List<ILocalizable> getErrors(){
        return this.errors;
    }

    /**
     * Creates an anonymous {@link ILocalizable localizable} error and adds it
     * to the {@code ProviderBuilder}'s list of errors.
     *
     * @param key the key of the error from the {@code file} bundle
     */
    private void pushError(String key){
        ILocalizable localizable = new ILocalizable() {
            @Override
            public String getResource(){
                return "file";
            }

            @Override
            public EnumSeverity getSeverity(){
                return EnumSeverity.ERROR;
            }

            @Override
            public String getSubkey(){
                return key;
            }

            @Override
            public Object[] getArguments(){
                return new Object[0];
            }
        };

        this.getErrors().add(localizable);
    }

    /**
     * Finds an appropriate {@code Provider} for the given {@code File} and
     * loads the {@code File} into it.
     */
    private Provider loadFile(File file){
        if(!file.exists() || !file.canRead()){
            this.pushError("load.file");
            return new BlankProvider();
        }

        if(TypeUtils.isConfiguration(file)){
            return new ConfigurationProvider(file);
        }else if(TypeUtils.isROM(file)){
            try {
                return new ROMProvider(file);
            }catch(LoadingException exception){
                this.getErrors().add(exception);
            }
        }else{
            this.pushError("load.type");
        }

        return new BlankProvider();
    }
}
