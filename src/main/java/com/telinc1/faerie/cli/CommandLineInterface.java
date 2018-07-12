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

package com.telinc1.faerie.cli;

import com.telinc1.faerie.Application;
import com.telinc1.faerie.UserInterface;
import com.telinc1.faerie.notification.Notifier;

import java.io.File;

/**
 * This class implements a command line user interface which only outputs text
 * to the standard output stream, operates on a single sprite based on the
 * provided command line arguments, and quits afterwards.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class CommandLineInterface extends UserInterface {
    public CommandLineInterface(Application application){
        super(application);
    }

    @Override
    public Notifier createNotifier(){
        return null;
    }

    @Override
    public void init(){

    }

    @Override
    public void start(){

    }

    @Override
    public void openFile(File file){

    }

    @Override
    public void saveProvider(File file){

    }

    @Override
    public boolean unloadProvider(){
        return false;
    }

    @Override
    public boolean stop(){
        return false;
    }
}
