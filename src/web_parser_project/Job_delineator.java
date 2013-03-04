/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project;

import database.Database_dump;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import page_parsing.Page_parser;
import web_parser_project.libraries.Html_helper;
import web_parser_project.site_getter.Site_getter;
import web_parser_project.web_assets.Html_asset;
import web_parser_project.web_assets.Web_url;

/**
 *
 * @author Jason
 */
public class Job_delineator {
    
    private Site_getter site_reader;
    private String starting_url;
    private String domain;
    
    public Job_delineator(String s_url, String dom){
        
        starting_url = s_url;
        domain = dom;
        
        site_reader = new Site_getter(s_url, dom);
    }
    
    public void run(){
        
        Web_url current_site;
        
        int limit = 1000;
        
        int counter = 0;
        while(site_reader.has_next() && counter < limit){
            current_site = site_reader.get_next();
            
            /*
            if(current_site != null){
                doc_parser.parse(current_site);
                parsed_pages++;
            }
            
            read_urls++;
            */
            counter++;
        }
        
        
        System.out.println("\n\n-----------------Parsed Pages----------------\n");
        
        Page_parser page_parser = new Page_parser();
        
        HashMap<String, Web_url> traveled_sites = site_reader.get_traveled_urls();
        Iterator it  = traveled_sites.keySet().iterator();
        Web_url current_it;
        while(it.hasNext()){
            String key = (String)it.next();
            current_it = (Web_url)traveled_sites.get(key);
            
            /*
            if(current_it.is_404()){
                print_fof(current_it.get_url(), current_it.get_references());
            }
            else if(current_it.is_200_ok()){
                System.out.println("OK: "+current_it.get_url());
            }*/
            
            Iterator referred_to = current_it.get_references().iterator();
            
            /*System.out.println("\tReferred By:");
            while(referred_to.hasNext()){
                System.out.println("\t\t"+referred_to.next());
            }*/
            
            //System.out.println("\t"+current_it.getClass().getName());
            
            
            
                
            // must be in the same domain to get checked in-depth
            if(Html_helper.is_same_domain(starting_url, current_it.get_url())){            
                if(current_it.get_web_asset() instanceof Html_asset && current_it.get_web_asset() != null){
                    //System.out.println("printing web asset");
                    page_parser.parse(current_it);
                    Html_asset asset = (Html_asset)current_it.get_web_asset();
                    LinkedList<String> misspellings = asset.get_misspellings();
                    Iterator misp_it = misspellings.iterator();
                    System.out.println(current_it.get_url());
                    while(misp_it.hasNext()){
                        String misp = (String)misp_it.next();
                        System.out.println("\t\t\t"+misp);
                    }

                }
            }
            
        }
        
        
            
        Database_dump d = new Database_dump();
        d.dump_to_database(starting_url, domain, traveled_sites);
        
        
        System.out.println("done");
    }
    
    public void print_fof(String fof_url, LinkedList<String> requested_by){
        
        String r_by = "";
        Iterator it = requested_by.iterator();
        while(it.hasNext()){
            r_by += it.next();
        }
        
        System.out.println("404: "+fof_url+" Used by "+r_by);
    }
}
