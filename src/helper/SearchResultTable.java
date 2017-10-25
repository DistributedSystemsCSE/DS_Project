package helper;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;


/**
 *
 * @author Hareen Udayanath
 */
public class SearchResultTable extends Observable{
    private final Hashtable<String, HashSet<SearchResult>> searchTable;

    public SearchResultTable() {
        this.searchTable = new Hashtable<>();
    }
    
    public void addToTable(String keyWord,SearchResult result){
        if(!searchTable.containsKey(keyWord)){
            searchTable.put(keyWord, new HashSet<>());
        }
        searchTable.get(keyWord).add(result);
        setChanged();
        notifyObservers();
    }
    
    public HashSet<SearchResult> getResults(String keyWord){
        return searchTable.get(keyWord);
    }

           
}
