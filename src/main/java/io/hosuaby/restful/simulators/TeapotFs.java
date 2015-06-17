package io.hosuaby.restful.simulators;

import java.util.HashMap;
import java.util.Map;

/**
 * Teapot FS, primitive file system for teapot simulator.
 * <br/>
 * Provides commands:
 * <ul>
 *   <li>ls    - lists all existing files;</li>
 *   <li>cat   - prints content of the file or puts a new content into the
 *               file;</li>
 *   <li>touch - creates a new file;</li>
 *   <li>mv    - renames existing file;</li>
 *   <li>rm    - removes existing file.</li>
 * </ul>
 * <br/>
 * No of those commands throws exception.
 */
// TODO: make this file system thread-safe
public class TeapotFs {

    /**
     * Files, map between filename and content
     */
    private Map<String, String[]> files;

    /**
     * Default constructor.
     */
    public TeapotFs() {
        super();
        this.files = new HashMap<>();
    }

    /**
     * @return names of existing files.
     */
    public String[] ls() {
        return files.keySet().toArray(new String[]{});
    }

    /**
     * Returns content of specified file.
     *
     * @param filename    filename
     * @return file content
     */
    public String[] cat(String filename) {
        return files.get(filename);
    }

    /**
     * Rewrites content of specified file.
     *
     * @param filename    filename
     * @param content     file content
     */
    public void cat(String filename, String[] content) {
        files.put(filename, content);
    }

    /**
     * Creates empty file with specified name.
     *
     * @param filename    filename
     */
    public void touch(String filename) {
        files.put(filename, new String[]{});
    }

    /**
     * Renames the file.
     *
     * @param oldFilename    old filename
     * @param newFilename    new filename
     */
    public void mv(String oldFilename, String newFilename) {
        files.put(newFilename, files.get(oldFilename));
        files.remove(oldFilename);
    }

    /**
     * Removes specified file.
     *
     * @param filename    filename
     */
    public void rm(String filename) {
        files.remove(filename);
    }

}
