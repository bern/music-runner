package com.sp.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.*;
import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> 6e01702834fa1fb8c1e9f502109ec4de7e8dae43
import java.util.StringTokenizer;

/**
 * Created by Troy on 12/1/2015.
 */
public class SongCacheUtil {

    private static FileHandle handle = getFileHandle();
    private static final String PATH =  "cache/levelcache.txt";
    private static final String TEMP_PATH = "cache/tmp.txt";
    private static final String ABS_PATH = System.getProperty("user.dir");

    private static FileHandle getFileHandle() {
        return Gdx.files.internal(PATH);
    }

    private static StringTokenizer getStringTokenizer() {
        if (handle != null)
            return new StringTokenizer(handle.readString());
        return null;
    }

    public static boolean hasLevel(String levelName) {
        //THIS METHOD CHECKS TO SEE IF A PARTICULAR SONG NAME IS CACHED.
        //IF IT IS, IT THEN ENSURES A WAVE FILE EXISTS WITH THE SAME NAME
        StringTokenizer tokens = getStringTokenizer();
        while(tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            if(token.equals(levelName)) {
                String tmpPath;
                if (!levelName.contains(".wav"))
                    tmpPath = levelName + ".wav";
                else
                    tmpPath = levelName;
                File file = new File(tmpPath);
                if (file.exists())
                    return true;
            }
        }
        return false;
    }
    
    public static boolean hasFrameBuiltLevel(String levelName) {
        File levelFile = new File("levels/" + levelName + ".txt");
        if (levelFile.exists()) {
            return true;
        }
        return false;
    }
    
    public static boolean addLevel(String levelName) {
        //ADDS A LEVEL TO THE CACHE LIST IF IT IS NOT ALREADY PRESENT
        //AND A PROPER WAV FILE EXISTS FOR THE ADDITION
        String tmp = levelName;
        tmp += tmp.contains(".wav") ? "":  ".wav";
        File tmpFile = new File(tmp);
        if (!tmpFile.exists())
            return false;
        if (!hasLevel(levelName)) {
            try {
                Writer output = new BufferedWriter(new FileWriter(PATH, true));
                output.append(levelName + "\n");
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean removeLevel(String levelName) {
        if (hasLevel(levelName)) {
            File inputFile = new File(PATH);
            File tempFile = new File(TEMP_PATH);
            File levelFile = new File("levels/" + levelName + ".txt");
            if (levelFile.exists()) {
                levelFile.delete();
            }

            BufferedReader reader = null;
            BufferedWriter writer = null;
            try {
                reader = new BufferedReader(new FileReader(inputFile));
                writer = new BufferedWriter(new FileWriter(tempFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            String lineToRemove = levelName;
            String currentLine;
            try {
                while ((currentLine = reader.readLine()) != null) {
                    // trim newline when comparing with lineToRemove
                    String trimmedLine = currentLine.trim();
                    if (trimmedLine.equals(lineToRemove)) continue;
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
                writer.close();
                reader.close();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
            inputFile.delete();
            return tempFile.renameTo(inputFile);
        }
        else {
            return false;
        }
    }

    public static String getLevelPath(String levelName) {
        //IF THE LEVEL IS CURRENTLY CACHED, AND THERE IS AN APPROPRIATE
        //LEVEL GENERATED, RETURN THE PATH OF THE LEVEL. ELSE RETURN NULL
        if (hasLevel(levelName)) {
            String tmp = levelName;
            if (levelName.contains(".wav"))
                tmp.replace(".wav", "");
            String path = "levels/" + tmp + ".txt";
            File tmpFile = new File(path);
            return tmpFile.exists() ? path : null;
        }
        else
            return null;
    }
    
    public static ArrayList<String> getCacheList() {
    	ArrayList<String> result = new ArrayList<String>();
    	
    	StringTokenizer tokens = getStringTokenizer();
        while(tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            result.add(token);
        }
    	
    	return result;
    }

    public static void refreshCache() {
        //PARSES THE CACHE FILE AND VALIDATES EXISTENCE OF MUSIC FILE AND LEVEL FILE.
        //IF EITHER CONDITION FAILS, REMOVE THE CACHE ENTRY.
        StringTokenizer tokens = getStringTokenizer();
        while(tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            File wav = new File(token + ".wav");
            File level = new File("levels/" + token + ".txt");
            if (!wav.exists() || !level.exists()) {
                removeLevel(token);
            }
        }
    }

    public static List<String> getCachedSongList() {
        refreshCache();
        ArrayList<String> list = new ArrayList<String>();
        StringTokenizer tokens = getStringTokenizer();
        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            list.add(token);
        }
        return list;
    }
}
