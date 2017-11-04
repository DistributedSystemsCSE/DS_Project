package helper;

import ds_project.Host;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Hareen Udayanath
 */
public class SearchResult {
    private Host neighbour;
    private String[] files;
    
    public SearchResult(){
        
    }
    
    public SearchResult(Host neighbour,String[] files){
        this.neighbour = neighbour;
        this.files = files;
    }

    /**
     * @return the neighbour
     */
    public Host getNeighbour() {
        return neighbour;
    }

    /**
     * @param neighbour the neighbour to set
     */
    public void setNeighbour(Host neighbour) {
        this.neighbour = neighbour;
    }

    /**
     * @return the files
     */
    public String[] getFiles() {
        return files;
    }

    /**
     * @param files the files to set
     */
    public void setFiles(String[] files) {
        this.files = files;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.neighbour);
        hash = 59 * hash + Arrays.deepHashCode(this.files);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SearchResult other = (SearchResult) obj;
        if (!Objects.equals(this.neighbour, other.neighbour)) {
            return false;
        }
        if (!Arrays.deepEquals(this.files, other.files)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String files_ = "";
        for(String file:files){
            files_+=file+",";
        }
        if(files_.length()>0)
            files_ = files_.substring(0, files_.length()-1);
        return neighbour + "\n\t files=" + files_;
    }
    
      
    
}
