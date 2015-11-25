package com.sp.game.tools;

/**
 * Created by Troy on 11/25/2015.
 *
 * This abstract class provides a skeleton and interface for generating a map based on
 * file data. The format of the file to be read is delegated to subclasses.
 *
 */
public abstract class LevelBuilder {

    private String readPath;
    private String writePath;

    public LevelBuilder(String filepath) {
        this.readPath = filepath;
    }

    public final String getReadPath() {
        return readPath;
    }

    public final String getWritePath() {
        return writePath;
    }

    protected final void setWritePath(String writePath) {
        this.writePath = writePath;
    }

    protected abstract void generateLevel();
}
