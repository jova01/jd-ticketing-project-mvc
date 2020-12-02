package com.cybertek.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapService<T, ID> {
    protected Map<ID, T> map = new HashMap<>();

    T save(ID id, T t){
        map.put(id, t);
        return t;
    }

    List<T> findAll(){
        return new ArrayList<>(map.values());
    }

    T findById(ID id){
        return map.get(id);
    }

    void deleteById(ID id){
        map.remove(id);
    }

    void delete(T t){
        map.entrySet().removeIf(entry -> entry.getValue().equals(t));
    }

    void  update(ID id, T t){
        map.put(id, t);
    }
}
