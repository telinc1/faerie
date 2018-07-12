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

package com.telinc1.faerie.util;

import java.io.File;

/**
 * The {@code TypeUtils} static class contains various methods for dealing with
 * file extensions.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public final class TypeUtils {
    /**
     * The file extension of a CFG configuration file.
     */
    public static final String TYPE_CFG = "cfg";

    /**
     * The file extension of a JSON configuration file.
     */
    public static final String TYPE_JSON = "json";

    /**
     * The file extension of a headered SNES ROM image.
     */
    public static final String TYPE_SMC_ROM = "smc";

    /**
     * The file extension of an unheadered SNES ROM image.
     */
    public static final String TYPE_SFC_ROM = "sfc";

    /**
     * The file extension of an RGB-based palette file.
     */
    public static final String TYPE_RGB_PALETTE = "pal";

    /**
     * The file extension of a Tile Layer Pro palette file.
     */
    public static final String TYPE_TPL_PALETTE = "tpl";

    /**
     * The file extension of an SNES-based palette file.
     */
    public static final String TYPE_SNES_PALETTE = "mw3";

    /**
     * Checks if the given file is a sprite configuration file.
     *
     * @param file the file to check
     * @return whether the file's extension is one of (CFG, JSON)
     */
    public static boolean isConfiguration(File file){
        String extension = TypeUtils.getExtension(file);

        return TypeUtils.TYPE_CFG.equals(extension)
            || TypeUtils.TYPE_JSON.equals(extension);
    }

    /**
     * Returns the extension of a file.
     *
     * @param file the file whose name should be processed
     * @return the file extension
     */
    public static String getExtension(File file){
        String name = file.getName();
        int dotIndex = name.lastIndexOf('.');

        return (dotIndex > 0 && dotIndex < name.length() - 1) ? name.substring(dotIndex + 1).toLowerCase() : "";
    }

    /**
     * Checks if the given file is an SNES ROM image.
     *
     * @param file the file to check
     * @return whether the file's extension is one of (SMC, SFC)
     */
    public static boolean isROM(File file){
        String extension = TypeUtils.getExtension(file);

        return TypeUtils.TYPE_SMC_ROM.equals(extension)
            || TypeUtils.TYPE_SFC_ROM.equals(extension);
    }

    /**
     * Checks if the given file is a palette file.
     *
     * @param file the file to check
     * @return whether the file's extension is one of (PAL, TPL, MW3)
     */
    public static boolean isPalette(File file){
        String extension = TypeUtils.getExtension(file);

        return TypeUtils.TYPE_RGB_PALETTE.equals(extension)
            || TypeUtils.TYPE_TPL_PALETTE.equals(extension)
            || TypeUtils.TYPE_SNES_PALETTE.equals(extension);
    }
}
