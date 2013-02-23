/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.web_assets;

import java.util.LinkedList;
import org.jsoup.nodes.Document;

/**
 *
 * @author Jason
 */
public class Html_asset extends Web_asset{
    
    protected Document contents;
    protected LinkedList<String> misspellings;
    protected LinkedList<String> at_mentions;
    protected LinkedList<String> hash_tags;
    protected LinkedList<String> emails;
    
    public Html_asset(Document inc_contents){
        contents = inc_contents;
        misspellings = new LinkedList();
        at_mentions = new LinkedList();
        hash_tags = new LinkedList();
        emails = new LinkedList();
    }
    
    public Document get_contents(){
        return contents;
    }
    
    public void add_to_at_mensions(String at_mention){
        if(at_mention != null){
            at_mentions.add(at_mention);
        }
    }
    
    public void add_to_hash_tags(String hashtag){
        if(hashtag != null){
            hash_tags.add(hashtag);
        }
    }
    
    public void add_to_emails(String email){
        if(email != null){
            emails.add(email);
        }
    }
    
    public void add_to_misspellings(String misspelling){
        if(misspelling != null){
            misspellings.add(misspelling);
        }
    }
    
    public void add_to_misspellings(LinkedList<String> incoming_misspellings){
        if(incoming_misspellings != null){
            misspellings.addAll(incoming_misspellings);
        }
    }
    
    public LinkedList<String> get_misspellings(){
        return misspellings;
    }
    
}
