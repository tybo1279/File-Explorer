
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * the class that implements methods to explore files and return results
 * from them
 * @author tykrill
 *
 */
public class FileExplorer {

    /**
     * Sorts through a list given by currentFolder to find all files in 
     * the directory and the directories themselves.
     * @param currentFolder the folder entered
     * @return a list of the names of all files and directories in
     * the the given folder.
     * @throws NotDirectoryException if the provided currentFolder 
     * does not exist or if it is not a directory
     */
    public static ArrayList<String> listContents(File currentFolder)
      throws NotDirectoryException {

        ArrayList<String> contentsList = new ArrayList();
          if (currentFolder.exists() == false || 
            currentFolder.isDirectory() == false) {
            throw new NotDirectoryException("No such file exists");
          }
        String[] listArray = currentFolder.list();
        //implement simple matching algorithm
          for (int i = 0; i < listArray.length; ++i) {
            contentsList.add(listArray[i]);
          }
        return contentsList;
    }
    
    /**
     * Sorts through a list given by currentFolder that finds all files within
     * a current directory, and its sub folders
     * @param currentFolder the folder entered
     * @return a list of the names of the files
     * @throws NotDirectoryException if current Folder isn't a directory
     * or there were no results.
     */
    public static ArrayList<String> deepListContents(File currentFolder)
        throws NotDirectoryException {

        if (currentFolder.isDirectory() == false |
          currentFolder.exists() == false) {
          throw new NotDirectoryException("Folder not a directory");
        }
        ArrayList<String> contentsList = new ArrayList();
        File[] folderContents = currentFolder.listFiles();
        int length = folderContents.length;
        for (int j = 0; j < length; ++j) { //implement recursive algorithm
          if (folderContents[j].isFile()) {
            contentsList.add(folderContents[j].getName());
          }
          if (folderContents[j].isDirectory()) {
            contentsList.addAll(deepListContents(folderContents[j]));
          }
        }
        return contentsList;
    }

    /**
     * Method that is called when 3 is entered into the console, 
     * checks for files that don't exist, then calls the helper method
     * @param currentFolder the folder entered
     * @param fileName the file name entered
     * @return the address of the file searched for
     */
    public static String searchByName(File currentFolder, String fileName) {

        String newString = "";
        newString = sBNHelper(currentFolder, fileName);//method call to helper
        if (newString.equals(fileName)){
            throw new NoSuchElementException("File does not exist");
        }
        return newString;
    }
    
    /**
     * searchByName Helper method that searches through a list given by 
     * current Folder and sub-folders for the file designated by the 
     * given filename
     * @param currentFolder the folder entered
     * @param fileName the name of the file that will be searched for
     * @return the address of the file searched for
     */
    private static String sBNHelper (File currentFolder, String fileName) {
        
        File[] searchList = currentFolder.listFiles();
        String finalPath = fileName;
          if (currentFolder.exists() == false) {
            throw new NoSuchElementException("Folder not a directory");
          }
        //implement recursive algorithm
        for (int i = 0; i < searchList.length; ++i) {
          if (searchList[i].isFile()) {
            if (searchList[i].toString().contains(fileName)) {
                  finalPath = currentFolder.toString().concat("\\" + fileName);
            }
          }
          if (searchList[i].isDirectory()) {
            finalPath = sBNHelper(searchList[i], finalPath);
          }
        }
        return finalPath;
    }

    /**
     * Sorts through the current Folder and sub-folders for a file that has a 
     * name matching that of the entered key
     * @param currentFolder the folder entered
     * @param key the String that will be searched for a match
     * @return list of all the matching files containing the key
     */
    public static ArrayList<String> searchByKey(File currentFolder, 
        String key) {

        File[] searchList = currentFolder.listFiles();
        ArrayList<String> matches = new ArrayList();
        //implement recursive algorithm
        for (int i = 0; i < searchList.length; ++i) {
          if (searchList[i].isFile()) {
            if (searchList[i].toString().contains(key)) {
              matches.add(searchList[i].getName());
            }
          }
          if (searchList[i].isDirectory()) {
            matches.addAll(searchByKey(searchList[i], key));
          }
        }
        return matches;
    }

    /**
     * Sorts through the current Folder and sub-folders for a file whose
     * size is in between an entered minimum and maximum
     * @param currentFolder the folder entered
     * @param sizeMin the minimum size a file can be to be added
     * @param sizeMax the maximum size a file can be to be added
     * @return a list of all the files whose size is in between sizeMin
     * and sizeMax
     */
    public static ArrayList<String> searchBySize(File currentFolder,
        long sizeMin, long sizeMax) {

        File[] searchList = currentFolder.listFiles();
        ArrayList<String> isBetween = new ArrayList();
        //implement recursive algorithm
        for (int i = 0; i < searchList.length; ++i) {
          if (searchList[i].isFile()) {
            if (searchList[i].length() >= sizeMin &&
              searchList[i].length() <= sizeMax) {
              isBetween.add(searchList[i].getName());
            }
          }
          if (searchList[i].isDirectory()) {
            isBetween.addAll(searchBySize(searchList[i], sizeMin, sizeMax));
          }
        }
        return isBetween;
    }
}

