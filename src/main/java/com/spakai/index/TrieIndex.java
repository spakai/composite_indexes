package com.spakai.index;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
    char key;
    String value;
    HashMap<Character, TrieNode>  children = new HashMap<>(10);
    boolean isLeaf;
    
    public TrieNode() {
        
    }
    
    public TrieNode(char key) {
        this.key = key;
    }
    
}

public class TrieIndex {
    private TrieNode root;
    
    public TrieIndex() {
        root = new TrieNode();
    }
    
    public void insert(String key, String value) {
         Map<Character, TrieNode> children = root.children;
         
         for(int i=0; i < key.length(); i++) {
             char c = key.charAt(i);
             
             TrieNode t;
             if (children.containsKey(c)) {
                 t = children.get(c);
             } else {
                 t = new TrieNode(c);
                 children.put(c, t);
             }
             
             children = t.children;
             
             if ( i == key.length() - 1) {
                 t.value = value;
                 t.isLeaf = true;
             }
             
         }
    }
    
    public String bestSearch(String key) {
        
        Map<Character, TrieNode> children = root.children;
        TrieNode t = null;
        String longestPrefix = null;
        
        for( int i =0 ; i < key.length(); i++) {
            char c = key.charAt(i);
            if (children.containsKey(c)) {
                t = children.get(c);
                
                if (t.isLeaf) {
                    longestPrefix = t.value;
                }
                children = t.children;
            } else {
                return longestPrefix;
            }
        }
        
        return null;
        
    }
}
