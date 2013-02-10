/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.site_getter;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import web_parser_project.libraries.Html_helper;
import web_parser_project.web_assets.Web_asset;

/**
 *
 * @author Jason
 */
public class Site_getter {
    
    private String starting_url;
    
    private HashMap<String, Web_asset> traveled_assets;
    private LinkedList<Parsing_url> non_traveled_urls;
    
    public Site_getter(String s_url){
        starting_url = s_url;
        
        non_traveled_urls = new LinkedList();
        traveled_assets = new HashMap();
        
        add_unexplored_url(starting_url, null);
    }
    
    public Document get_next(){
        Parsing_url current_url = non_traveled_urls.remove();
        
        System.out.println("Parsing: "+current_url.get_url());
        
        try{
            URL the_url = new URL(current_url.get_url());
            
            HttpURLConnection.setFollowRedirects(true);
            HttpURLConnection connection = (HttpURLConnection) the_url.openConnection();
            
            set_request_method(connection, current_url.get_url());
            
            InputStream in = connection.getInputStream();
            
            int http_status = connection.getResponseCode();
            
            add_to_explored_ok(current_url, http_status, connection.getContentType());
            
            if(Html_helper.is_200(http_status)){
            
                // only parse web pages
                if(Html_helper.is_content_type_html(connection.getContentType())){
                    Document html_page = Jsoup.parse(in, null, current_url.get_url());
                    store_links(html_page, current_url.get_url());
                    return html_page;
                }
                else{
                    System.out.println("Error, not a good content type");
                    System.out.println(connection.getContentType());
                }
                
            }
            
        }
        
        // TODO, log these exceptions
        catch(MalformedURLException e){
            
            //add_to_explored_error(current_url);
            
            
            // TODO, log this exception
            System.out.println("----------");
            System.out.println("Malformed URL");
            System.out.println(e);
            System.out.println(current_url.get_url());
            System.out.println("----------");
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
            System.out.println(current_url.get_url());
            
            add_to_explored_four_o_four(current_url);
        }
        catch(IOException e){
            System.out.println("IO Exception");
            System.out.println(e);
            
            //add_to_explored_error(current_url);
        }
        
        return null;
    }
    
    public boolean has_next(){
        if(non_traveled_urls.size() > 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    public HashMap<String, Web_asset> get_traveled_urls(){
        return traveled_assets;
    }
    
    private boolean has_already_been_seen(String the_url){
        if(traveled_assets.containsKey(the_url)){
            return true;
        }
        else{
            return false;
        }
    }
    
    private void store_links(Document the_document, String current_url){
        
        // the link page must be in the same domain if it is to be parsed also check if it hasn't been checked already
        if(Html_helper.is_same_domain(starting_url, current_url)){
            
            String link_url;
            Elements links = the_document.select("a[href]");
            for (Element link : links) {
                
                link_url = link.attr("abs:href");
                
                // strips out the # anchors
                link_url = Html_helper.strip_page_anchor(link_url);
                
                if(has_already_been_seen(link_url) == false){
                    add_unexplored_url(link_url, current_url);
                }
            }
        }
    }
    
    private void add_unexplored_url(String the_url, String parent_url){
        Parsing_url p_url = new Parsing_url(the_url);
        p_url.set_parent_url(parent_url);
        non_traveled_urls.add(p_url);
    }
    
    private void add_to_explored_ok(Parsing_url p_url, int status_code, String content_type){
        
        
        
        /*
        p_url.set_http_code(status_code);
        p_url.set_content_type(content_type);
        
        traveled_urls.put(p_url.get_url(), p_url);
        * */
    }
    
    private void add_to_explored_four_o_four(Parsing_url p_url){
        /*
        p_url.set_http_code(404);
        
        traveled_urls.put(p_url.get_url(), p_url);
    }
    
    private void add_to_explored_error(Parsing_url p_url){
        p_url.set_http_code(404);
        
        traveled_urls.put(p_url.get_url(), p_url);
        * */
    }
    
    
    private void set_request_method(HttpURLConnection connection, String the_url){
        // TODO, set head request if the url is an image, css file, jscript file, etc.
       // do this 
    }
    
    
}
