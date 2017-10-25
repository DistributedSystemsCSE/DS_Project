package helper;

import ds_project.Neighbour;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Hareen Udayanath
 */
public class SearchResult {
    private Neighbour neighbour;
    private String[] files;
    
    public SearchResult(){
        
    }
    
    public SearchResult(Neighbour neighbour,String[] files){
        this.neighbour = neighbour;
        this.files = files;
    }

    /**
     * @return the neighbour
     */
    public Neighbour getNeighbour() {
        return neighbour;
    }

    /**
     * @param neighbour the neighbour to set
     */
    public void setNeighbour(Neighbour neighbour) {
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
    
    
    
    
}
