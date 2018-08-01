package com.palotech.pelflex.workout.burner;

import java.util.HashMap;
import java.util.Map;

public class Burner {

    private String name;
    private Map<String, Integer> intMap;
    private Map<String, Double> doubleMap;
    private Map<String, Boolean> booleanMap;

    public Burner(String name, Map<String, Double> doubleMap) {
        this(name, new HashMap<>(), doubleMap, new HashMap<>());
    }

    public Burner(String name, Map<String, Integer> intMap, Map<String, Double> doubleMap, Map<String, Boolean> booleanMap) {
        this.name = name;
        this.intMap = intMap;
        this.doubleMap = doubleMap;
        this.booleanMap = booleanMap;
    }

    private boolean containsIntegerKey(String key) {
        return containsKey(intMap, key);
    }

    private boolean containsDoubleKey(String key) {
        return containsKey(doubleMap, key);
    }

    private boolean containsBooleanKey(String key) {
        return containsKey(booleanMap, key);
    }

    public boolean containsKey(Map<?, ?> map, String key) {
        return map.containsKey(key);
    }

    public Integer getIntegerValue(String key) {
        return intMap.get(key);
    }

    public Double getDoubleValue(String key) {
        return doubleMap.get(key);
    }

    public Boolean getBooleanValue(String key) {
        return booleanMap.get(key);
    }

    public String getName() {
        return name;
    }
}
