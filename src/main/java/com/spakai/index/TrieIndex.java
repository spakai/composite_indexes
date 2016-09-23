package com.spakai.index;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
    char c;
    String word;
    HashMap<Character, TrieNode>  children = new HashMap<>(10);
    boolean isLeaf;
    
    public TrieNode() {
        
    }
    
    public TrieNode(char c) {
        this.c = c;
    }
    
}

public class TrieIndex {
    private TrieNode root;
    
    public TrieIndex() {
        root = new TrieNode();
    }
    
    public void insert(String word) {
         Map<Character, TrieNode> children = root.children;
         
         for(int i=0; i < word.length(); i++) {
             char c = word.charAt(i);
             
             TrieNode t;
             if (children.containsKey(c)) {
                 t = children.get(c);
             } else {
                 t = new TrieNode(c);
                 children.put(c, t);
             }
             
             children = t.children;
             
             if ( i == word.length() - 1) {
                 t.word = word;
                 t.isLeaf = true;
             }
             
         }
    }
    
    public String bestSearch(String word) {
        
        Map<Character, TrieNode> children = root.children;
        TrieNode t = null;
        String longestPrefix = null;
        
        for( int i =0 ; i < word.length(); i++) {
            char c = word.charAt(i);
            if (children.containsKey(c)) {
                t = children.get(c);
                
                if (t.isLeaf) {
                    longestPrefix = t.word;
                }
                children = t.children;
            } else {
                return longestPrefix;
            }
        }
        
        return null;
        
    }
}
