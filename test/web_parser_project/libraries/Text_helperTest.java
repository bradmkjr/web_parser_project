/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.libraries;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jason
 */
public class Text_helperTest {
    
    public Text_helperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of strip_punctuation_from_end method, of class Text_helper.
     */
    @Test
    public void testStrip_punctuation_from_end() {
        System.out.println("strip_punctuation_from_end");
        String[] test_strings = {
            "", "hello", "cat!", "but,", "rick*", "phil,ly", "jim;"
        };
        String[] result_strings = {
            "", "hello", "cat", "but", "rick*", "phil,ly", "jim"
        };
       
        for(int i = 0; i < test_strings.length; i++){
            String result = Text_helper.strip_punctuation_from_end(test_strings[i]);
            assertEquals(result_strings[i], result);
        }
        
    }
    
    /**
     * Test of is_hash_tag( method, of class Text_helper.
     */
    @Test
    public void testIs_hash_tag() {
        System.out.println("Testing the is hash tag function");
        
        String[] test_strings = {
            "", "#yep", "nope"
        };
        boolean[] results = {
            false, true, false
        };
       
        for(int i = 0; i < test_strings.length; i++){
            boolean result = Text_helper.is_hash_tag(test_strings[i]);
            assertEquals(results[i], result);
        }
    }
    
    
    /**
     * Test of is_at_mention method, of class Text_helper.
     */
    @Test
    public void testIs_at_mention() {
        System.out.println("Testing the is at mention function");
        
        String[] test_strings = {
            "", "@yep", "nope"
        };
        boolean[] results = {
            false, true, false
        };
       
        for(int i = 0; i < test_strings.length; i++){
            boolean result = Text_helper.is_at_mention(test_strings[i]);
            assertEquals("The item at index "+i+"failed", results[i], result);
        }
    }

    /**
     * Test of split_text_to_individual_words method, of class Text_helper.
     */
    @Test
    public void testSplit_text_to_individual_words() {
        System.out.println("Testing the split text into individual words function");
        assertNull(Text_helper.split_text_to_individual_words(null));
        
        String input1 = "Hello, John; 'I";
        String[] result1 = {"Hello,", "John;", "'I"};
        String[] actual1 = Text_helper.split_text_to_individual_words(input1);
        assertArrayEquals(result1, actual1);
    }
    
    
    
}
