package edu.whu.springbootdemo.service.impl;

import edu.whu.springbootdemo.entity.Commodity;
import edu.whu.springbootdemo.service.CommodityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CommodityImpl implements CommodityService {

    Map<Long, Commodity> concurrentHashMap = new ConcurrentHashMap<>();

    @Override
    public boolean save(Commodity commodity) {
        if (commodity == null || commodity.getId() == null || concurrentHashMap.containsKey(commodity.getId())) {
            return false;
        }
        concurrentHashMap.put(commodity.getId(), commodity);
        return true;
    }

    @Override
    public Commodity getById(Long id) {
        if (id == null) {
            return null;
        }
        return concurrentHashMap.get(id);
    }

    @Override
    public List<Commodity> list() {
        return new ArrayList<>(concurrentHashMap.values());
    }

    @Override
    public boolean update(Commodity commodity) {
        if (commodity == null || !concurrentHashMap.containsKey(commodity.getId())) {
            return false;
        }
        concurrentHashMap.put(commodity.getId(), commodity);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        if (id == null || !concurrentHashMap.containsKey(id)) {
            return false;
        }
        concurrentHashMap.remove(id);
        return true;
    }
}
