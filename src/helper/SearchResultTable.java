package helper;

import java.util.HashSet;
import java.util.Hashtable;

/**
 *
 * @author Hareen Udayanath
 */
public class SearchResultTable {
    private final Hashtable<String, HashSet<SearchResult>> searchTable;

    public SearchResultTable() {
        this.searchTable = new Hashtable<>();
    }
    
    public void addToTable(String keyWord,SearchResult result){
        if(!searchTable.containsKey(keyWord)){
            searchTable.put(keyWord, new HashSet<>());
        }
        searchTable.get(keyWord).add(result);
    }
    
    public HashSet<SearchResult> getResults(String keyWord){
        return searchTable.get(keyWord);
    }
       
}
