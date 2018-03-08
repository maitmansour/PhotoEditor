/*
 * The MIT License
 *
 * Copyright 2018 Mohamed AIT MANSOUR <contact@numidea.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package photoeditor;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Find Extension in folder
 * @author Mohamed AIT MANSOUR <contact@numidea.com>
 */
public class FindCertainExtension {

    public boolean checkFileExistence(String folder, String ext) {
        return getFilesList(folder, ext).length != 0;
    }
    /**
     * Get all files with extension in folder
     * @param folder
     * @param ext
     * @return 
     */
    public String[] getFilesList(String folder, String ext) {
        GenericExtFilter filter = new GenericExtFilter(ext);
        File dir = new File(folder);
        String[] list = dir.list(filter);
        return list;
    }

   /**
    * Generic extension filter
    */
    public class GenericExtFilter implements FilenameFilter {

        private final String extension;
        public GenericExtFilter(String ext) {
            this.extension = ext;
        }

        @Override
        public boolean accept(File dir, String name) {
            return (name.endsWith(extension));
        }
    }
}