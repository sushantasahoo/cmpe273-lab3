package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
    	
    	String serverA = "http://localhost:3000";
    	String serverB = "http://localhost:3001";
    	String serverC = "http://localhost:3002";
    	List<String> serverURLs = new ArrayList<>();
    	serverURLs.add(serverA);
    	serverURLs.add(serverB);
    	serverURLs.add(serverC);
    	
    	List<CacheServiceInterface> serverList = new ArrayList<>();
    	serverList.add(new DistributedCacheService(serverA));
    	serverList.add(new DistributedCacheService(serverB));
    	serverList.add(new DistributedCacheService(serverC));
    	
    	for(int i=1,j=97;i<=10;i++,j++){
    		int bucket = Hashing.consistentHash(Hashing.md5().hashInt(i), serverList.size());
            System.out.println("Key "+i+ " is routed to server : " + serverURLs.get(bucket));
            serverList.get(bucket).put(i, new Character((char)j).toString());
    	}
    	
    	System.out.println("\nRetriving all keys");
    	
    	for(int i=1;i<=10;i++){
    		int bucket = Hashing.consistentHash(Hashing.md5().hashInt(i), serverList.size());
            System.out.println("Key "+i+" : Value "+ serverList.get(bucket).get(i));
    	}
       
    }

}
