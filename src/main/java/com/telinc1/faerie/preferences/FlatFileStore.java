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

package com.telinc1.faerie.preferences;

import com.telinc1.faerie.Application;
import com.telinc1.faerie.util.locale.Warning;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * The {@code FlatFileStore} class implements a persistent INI-like flat file
 * storage for application preferences, stored in the same directory as the
 * application itself.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class FlatFileStore extends PreferenceStore {
    /**
     * The name of the configuration file, relative to the directory in which
     * Faerie is launched.
     */
    private static final String FILE_NAME = "faerie.ini";

    /**
     * All of the preferences stored in this object.
     */
    private final Map<String, String> preferences;

    /**
     * The configuration file for the object.
     */
    private File file;

    /**
     * Construct a new {@code FlatFileStore} manager for an {@link Application}.
     */
    public FlatFileStore(Application application){
        super(application);
        this.preferences = new HashMap<>();
    }

    @Override
    public boolean has(String key){
        return this.preferences.containsKey(key);
    }

    @Override
    public String get(String key){
        return this.preferences.get(key);
    }

    @Override
    public FlatFileStore set(String key, String value){
        this.preferences.put(key, value);
        return this;
    }

    @Override
    public Warning load(){
        File file = this.getFile();

        if(file == null){
            return new Warning("core", "preferences.file");
        }

        if(!file.exists()){
            return null;
        }

        Path path = Paths.get(file.toURI());

        try(Stream<String> stream = Files.lines(path)) {
            stream.forEach(line -> {
                if(line.isEmpty()){
                    return;
                }

                String[] preference = line.split("=", 2);

                if(preference.length != 2){
                    return;
                }

                this.preferences.put(preference[0], preference[1]);
            });
        }catch(IOException exception){
            this.getApplication().getExceptionHandler().report(exception);
            return new Warning("core", "preferences.load.io");
        }catch(SecurityException exception){
            this.getApplication().getExceptionHandler().report(exception);
            return new Warning("core", "preferences.load.security");
        }

        return null;
    }

    /**
     * Returns the {@link File} which stores preferences, or {@code null} if
     * it isn't accessible.
     */
    private File getFile(){
        if(this.file == null){
            try {
                String path = Application.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                File file = new File(path);

                if(!file.isDirectory()){
                    path = file.getParent();
                }

                this.file = new File(path + "/" + FlatFileStore.FILE_NAME);
            }catch(NullPointerException | URISyntaxException | SecurityException exception){
                return null;
            }
        }

        return this.file;
    }

    @Override
    public Warning store(){
        File file = this.getFile();

        if(file == null){
            return new Warning("core", "preferences.file");
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for(Map.Entry<String, String> entry : this.preferences.entrySet()){
                writer.write(String.format("%s=%s%n", entry.getKey(), entry.getValue()));
            }

            writer.flush();
        }catch(IOException exception){
            this.getApplication().getExceptionHandler().report(exception);
            return new Warning("core", "preferences.store.io");
        }catch(SecurityException exception){
            this.getApplication().getExceptionHandler().report(exception);
            return new Warning("core", "preferences.store.security");
        }

        return null;
    }
}
