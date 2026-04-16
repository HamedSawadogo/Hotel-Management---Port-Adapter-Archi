package org.example;

import java.util.*;


public class Main {

    public static List<String> products = new ArrayList<>();

    static {
        for (int i = 0; i < 100; i++) {
            products.add("Product" + i);
        }
    }

    public static class CacheService {
        private Map<String, String> cache = new HashMap<>();
        private static int TTL = 1000;

        public synchronized String get(String key) throws InterruptedException {
            if (!cache.containsKey(key)) {
                String value = products.toString();
                Thread.sleep(10000);
                cache.put(key, value);
            }
            return cache.get(key);
        }
    }


    static void main(String[] args) throws InterruptedException {
        CacheService cacheService = new CacheService();
        System.err.println(cacheService.get("1"));

        System.err.println("=====================>");
        System.err.println(cacheService.get("1"));
    }
}
