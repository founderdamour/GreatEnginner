package com.yzz.great.util.img;

/**
 * A functional interface (callback) that returns true or false for the given input path should be compressed.
 */

public interface CompressionPredicate {

    /**
     * Determine the given input path should be compressed and return a boolean.
     *
     * @param path input path
     * @return the boolean result
     */
    boolean apply(String path);
}
