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

package com.telinc1.faerie;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The {@code Resources} class handles locating and reading external data files
 * for the application, usually stored within the JAR. It also has methods for
 * handling strings within {@link ResourceBundle}s for easy localization.
 *
 * @author Telinc1
 * @since 1.0.0
 */
@SuppressWarnings("StringConcatenation")
public class Resources {
    /**
     * The top-level package of the application.
     */
    public static final String PACKAGE = "/com/telinc1/faerie";

    /**
     * A map of {@code ResourceBundle}s which have already been loaded.
     */
    private static Map<String, ResourceBundle> bundles = new HashMap<>();

    /**
     * Dummy private constructor to prevent construction of the class.
     */
    private Resources(){}

    /**
     * Retrieves a string from the given bundle. If the given key does not
     * exist in the bundle, the key itself is returned.
     *
     * @param source the name of the bundle to look in
     * @param key the key to look for
     * @param arguments the arguments to optionally format the string with
     * @return the formatted string from the bundle
     */
    public static String getString(String source, String key, Object... arguments){
        ResourceBundle bundle = Resources.getBundle(source);
        String value;

        try {
            value = bundle.getString(key);
        }catch(MissingResourceException | ClassCastException exception){
            value = key;
        }

        if(arguments.length == 0){
            return value;
        }

        return Resources.format(value, arguments);
    }

    /**
     * Formats the given string by substituting the items from the arguments.
     * <p>
     * Each argument consists of two consecutive values in the array: a
     * {@code String} representing a key and a value for that key; substrings
     * in the format {@code {key}} in the input are replaced with the value.
     * <p>
     * Alternatively, an unpaired {@link Integer} is implicitly given the key
     * {@code integer}, while an unpaired {@link Exception}'s entire trace log
     * is implicitly given the key {@code exception}.
     * <p>
     * Additionally, format styles and types can be applied to each parameter;
     * {@code {foo,date}} would be replaced with the key {@code foo} formatted
     * as a date.
     *
     * @param input the input {@code String} which should be formatted
     * @param arguments the key-value array of tokens to replace
     * @return the fully formatted input
     * @see MessageFormat
     */
    public static String format(String input, Object... arguments){
        int order = 0;
        List<Object> strings = new ArrayList<>();

        // yay spaghetti code and bad standards
        for(int i = 0; i < arguments.length; i++){
            String variable = null;
            Object element = arguments[i];

            if(element instanceof String){
                if(i >= arguments.length - 1){
                    break;
                }

                variable = (String)element;
                strings.add(arguments[++i]);
            }else if(element instanceof Integer){
                variable = "integer";
                strings.add(element.toString());
            }else if(element instanceof Exception){
                variable = "exception";
                strings.add(ExceptionHandler.getStackTrace((Throwable) element));
            }

            if(variable != null){
                input = input.replace("{" + variable + "}", "{" + order + "}");
                order += 1;
            }

            if(order >= 10){
                break;
            }
        }

        return MessageFormat.format(input.replace("'", "''"), strings.toArray());
    }

    /**
     * Retrieves an array of strings from a resource bundle.
     *
     * @param source the name of the bundle to look in
     * @param keys the array of keys to retrieve
     * @return an array of strings from the bundle
     */
    public static String[] getStrings(String source, String... keys){
        String[] strings = new String[keys.length];

        for(int i = 0; i < keys.length; i++){
            strings[i] = Resources.getString(source, keys[i]);
        }

        return strings;
    }

    /**
     * Retrieves or loads the given resource bundle.
     *
     * @param name the lowercase name of the bundle to look for
     * @return a {@link ResourceBundle} with the specified name
     * @throws java.util.MissingResourceException if no bundle with the given name exists
     */
    public static ResourceBundle getBundle(String name){
        if(Resources.bundles.containsKey(name)){
            return Resources.bundles.get(name);
        }

        String resource = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        ResourceBundle bundle = ResourceBundle.getBundle("com.telinc1.faerie.locale." + resource);
        Resources.bundles.put(name, bundle);

        return bundle;
    }

    /**
     * Finds a resource with the given name using the default class loader.
     *
     * @param path the path to the resource, excluding the top-level packages
     * @return an {@code InputStream} for the resource
     * @throws FileNotFoundException if the file doesn't exist
     */
    public static InputStream getResource(String path) throws FileNotFoundException{
        InputStream stream = Resources.class.getResourceAsStream(Resources.PACKAGE + "/" + path);

        if(stream == null){
            throw new FileNotFoundException(Resources.PACKAGE + "/" + path);
        }

        return stream;
    }
}
