/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.configuration;

/**
 *
 * @author ComradeBaz
 */
public enum HTTPCacheHeader {

    CACHE_CONTROL("Cache-Control"),

    EXPIRES("Expires"),

    PRAGMA("Pragma");

    private String name;

    HTTPCacheHeader(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }

}