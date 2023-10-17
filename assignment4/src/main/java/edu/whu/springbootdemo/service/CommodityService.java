package edu.whu.springbootdemo.service;

import edu.whu.springbootdemo.entity.Commodity;

import java.util.List;

public interface CommodityService {

    boolean save(Commodity commodity);

    Commodity getById(Long id);

    List<Commodity> list();

    boolean update(Commodity commodity);

    boolean delete(Long id);
}
