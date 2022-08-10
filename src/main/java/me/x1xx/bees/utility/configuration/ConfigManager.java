/*
 * MIT License
 *
 * Copyright (c) 2021 Azortis
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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.x1xx.bees.utility.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("all")
public class ConfigManager {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final Class<?> main;

    /**
     * @param plugin The plugin to use when loading in configuration files.
     */
    public ConfigManager(Class<?> main) {
        this.main = main;
    }

    /**
     * Loads and creates a configuration file.
     *
     * @param name     The name/path of the configuration file.
     * @param defaults The default values of the configuration file
     * @param <T>      The type/class of the configuration file.
     * @return {@link Config<T>} the config object
     */
    public <T> Config<T> loadConfig(String name, T defaults) {
        return new Config<T>(name, gson, main, defaults);
    }

    /**
     * @return The Gson object used to load in configuration data.
     */
    public Gson getGson() {
        return gson;
    }
}


