package helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

/**
 *
 * @author Buddhi
 */
public class FileHandler {
    
    private final List<String> fileNames =  getRandomFileNames();
    
    public FileHandler(){
        
    }
    
    public String[] getAllFileNames() {
        try {
            Scanner sc = new Scanner(new File("FileNames.txt"));
            List<String> lines = new ArrayList<>();
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
            return lines.toArray(new String[0]);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<String> getCurrentFileNames() {
        return fileNames; 
    }
    
    public List<String> getRandomFileNames() {
        try {
            Scanner sc = new Scanner(new File("FileNames.txt"));
            List<String> lines = new ArrayList<>();
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
            String[] temFiles = lines.toArray(new String[0]);

            Random rand = new Random();
            int n = rand.nextInt(temFiles.length - 1) + 1;
            final int[] picked = new int[n];
            List<String> selectFiles = new ArrayList();
            int i = 0, index = 0;
            while ((i < n) || (index == 0)) {
                int num = rand.nextInt(n);
                if (!IntStream.of(picked).anyMatch(x -> x == num)) {
                    picked[index++] = num;
                    selectFiles.add(temFiles[num]);
                }
                i++;
            }
            return selectFiles;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<String> getSimilarFileNames(String name) {
        Set<String> selectedFiles = new HashSet<>();
        String[] checkWords = name.toLowerCase().split(" ");
        fileNames.stream().forEach((filename) -> {
            List<String> fileNameWords = Arrays
                    .asList(filename.toLowerCase().split(" "));
            for(String checkWord:checkWords){
                if(fileNameWords.contains(checkWord)){
                    selectedFiles.add(filename);
                    break;
                }
            }
        });
        return new ArrayList(selectedFiles);
    }
    
    public List<String> getFileNames(){
        return fileNames;
    }

    public static void main(String[] args) {

        FileHandler fh = new FileHandler();
        List<String> fileNames = fh.getRandomFileNames();
        System.out.println("Number of Files: " + fileNames.size());
        fileNames.stream().forEach((name) -> {
            System.out.println(name);
        });

        String fnm = "The";
        System.out.println(fnm + ": " + fh.getSimilarFileNames(fnm));
    }

}
