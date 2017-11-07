package helper;

import ds_project.Host;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Observable;


/**
 *
 * @author Hareen Udayanath
 */
public class SearchResultTable extends Observable{
    private final Hashtable<String, HashSet<SearchResult>> searchTable;
    private String search_keyword;

    public SearchResultTable() {
        this.searchTable = new Hashtable<>();
    }
    
    public void addToTable(String keyWord,SearchResult result){
        if(!searchTable.containsKey(keyWord)){
            searchTable.put(keyWord, new HashSet<>());
        }
        searchTable.get(keyWord).add(result);
        search_keyword = keyWord;
        setChanged();
        notifyObservers();
    }
    
    public HashSet<SearchResult> getResults(String keyWord){
        if(!searchTable.containsKey(keyWord)){
            return new HashSet<>();
        }
        return searchTable.get(keyWord);
    }

    public void removeLeavedPeerResults(Host host){
        for(String key:searchTable.keySet()){
           HashSet<SearchResult> set = searchTable.get(key);
           if(set.contains(new SearchResult(host,null))){
                set.remove(new SearchResult(host,null));
                searchTable.put(key, set);
            }
        }
    }
    
    public String getUpdatedKeyword(){
        return search_keyword;
    }
           
}
