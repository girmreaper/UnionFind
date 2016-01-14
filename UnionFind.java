/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Bhaskar
 */
public class UnionFind <T>{
    class Container<T>{
        Container parent;
        T payload;
        int rank;
        @Override
        public boolean equals(Object o){
            if (o instanceof Container){
                Container c = (Container) o;
                return this.payload.equals(c.payload);
            } else {
                return false;
            }
        }
        @Override
        public int hashCode(){
            return payload.hashCode();
        }
        private Container(T input){
            payload = input;
        }
    }
    public void makeSet(T input){
        Container<T> cont = new Container(input);
        cont.parent = cont;
        cont.rank = 0;
        registry.put(input, cont);
        roots.add(cont);
    }
    public T find(T input){
        T retval = null;
        if (registry.containsKey(input)){
            Container<T> c1 = registry.get(input);
            Container<T> c2 = c1;
            while(c1.parent != c1){
                c1 = c1.parent;
            }
            // c1 is now parent
            // do this for path compression
            
            while(c2.parent != c1){
                Container<T> par = c2.parent;
                c2.parent = c1;
                c2 = par;
            }
            return c1.payload;
        } else {
            return retval;
        }
    }
    
    public void union(T a, T b){
        T aPar = find(a);
        T bPar = find(b);
        if (aPar == null || bPar == null) return;
        Container aPC = registry.get(aPar);
        Container bPC = registry.get(bPar);
        if (aPC != bPC){
            // parent of a and b are not the same
            if (aPC.rank < bPC.rank){
                aPC.parent = bPC;
                roots.remove(aPC);
            } else if (aPC.rank > bPC.rank){
                bPC.parent = aPC;
                roots.remove(bPC);
            } else {
                // aPC.rank == bPC.rank
                aPC.parent = bPC;
                bPC.rank++;
                roots.remove(aPC);
            }
        }
    }
    
    public int numSets(){
        return roots.size();
    }
    
    HashMap<T, Container<T>> registry = new HashMap();
    HashSet<Container> roots = new HashSet();
}
