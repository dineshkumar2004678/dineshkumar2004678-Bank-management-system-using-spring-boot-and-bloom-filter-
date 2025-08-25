package com.example.database.bloom;

import org.springframework.stereotype.Component;
import java.util.BitSet;

@Component
public class AccountBloomFilter {

    private BitSet bitset;
    private int size = 1000;          
    private int[] seeds = {7, 11, 13}; 

    public AccountBloomFilter() {
        bitset = new BitSet(size);
    }  

    // Track names now
    public void addName(String accountHolder) {    
        for (int seed : seeds) {
            int hash = hash(accountHolder, seed);
            bitset.set(Math.abs(hash % size), true);
        }
    }

    private int hash(String item, int seed) {
        int result = 0;
        for (char c : item.toCharArray()) {
            result = seed * result + c;
        }
        return result;
    }
    public boolean mightContainName(String accountHolder) {
        String item = accountHolder;
        for (int seed : seeds) {
            int hash = hash(item, seed);
            if (!bitset.get(Math.abs(hash % size))) {
                return false;
            }
        }
        return true;
    }
}
