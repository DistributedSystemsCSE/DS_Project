package helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 *
 * @author Buddhi
 */
public class FileHandler {

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
        String[] fileNames = getAllFileNames();
        List<String> selectFile = new ArrayList<>();
        for (String fn : fileNames) {
            if (fn.toLowerCase().contains(name.toLowerCase())) {
                selectFile.add(fn);
            }
        }
        return selectFile;
    }

    public static void main(String[] args) {

        FileHandler fh = new FileHandler();
        List<String> fileNames = fh.getRandomFileNames();
        System.out.println("Number of Files: " + fileNames.size());
        fileNames.stream().forEach((name) -> {
            System.out.println(name);
        });

//        String fnm = "windows";
//        System.out.println(fnm + ": " + fh.getSimilarFileNames(fnm));
    }

}
