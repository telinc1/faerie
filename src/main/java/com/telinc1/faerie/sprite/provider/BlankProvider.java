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


import com.telinc1.faerie.sprite.Sprite;
import com.telinc1.faerie.util.locale.Warning;

import java.io.File;

/**
 * A {@code BlankProvider} takes no input and provides a brand new sprite. It
 * can only be saved to a new {@code ConfigurationProvider}.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class BlankProvider extends Provider {
    /**
     * The sprite which is being created by the provider.
     */
    private Sprite sprite;

    /**
     * Stores whether the sprite has been touched.
     */
    private boolean isModified;

    /**
     * Constructs a {@code BlankProvider} for a new sprite.
     */
    public BlankProvider(){
        super();
        this.sprite = new Sprite();
        this.isModified = false;
    }

    @Override
    public File getInput(){
        return null;
    }

    @Override
    public Provider save(File file) throws SavingException{
        ConfigurationProvider provider = new ConfigurationProvider(file);
        provider.setSprite(this.getCurrentSprite());
        provider.save(file);

        return provider;
    }

    @Override
    public String[] getAvailableSprites(){
        if(this.getCurrentSprite().getDisplayData() != null){
            return new String[]{this.getCurrentSprite().getDisplayData().getName()};
        }

        return new String[]{"Sprite"};
    }

    @Override
    public void loadSprite(int index) throws ProvisionException{
        if(index != 0){
            throw new ProvisionException("Index out of bounds: " + index + ".", "index");
        }
    }

    @Override
    public Warning[] getWarnings(){
        return new Warning[0];
    }

    @Override
    public int getLoadedIndex(){
        return 0;
    }

    @Override
    public Sprite getCurrentSprite(){
        return this.sprite;
    }

    @Override
    public Sprite startModification(){
        this.isModified = true;
        return this.getCurrentSprite();
    }

    @Override
    public boolean isModified(){
        return this.isModified;
    }
}
