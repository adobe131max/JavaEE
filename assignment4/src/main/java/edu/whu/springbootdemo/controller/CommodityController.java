package edu.whu.springbootdemo.controller;

import edu.whu.springbootdemo.common.R;
import edu.whu.springbootdemo.entity.Commodity;
import edu.whu.springbootdemo.service.CommodityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    CommodityService commodityService;

    @PostMapping("/add")
    public R<String> save(@RequestBody Commodity commodity) {
        if (commodityService.save(commodity)) {
            return R.success("add success");
        }
        return R.error("add fail");
    }

    @GetMapping("/get")
    public R<Commodity> getById(@RequestParam Long id) {
        Commodity commodity = commodityService.getById(id);
        if (commodity == null) {
            return R.error("get fail");
        }
        return R.success(commodity);
    }

    @GetMapping("/list")
    public R<List<Commodity>> list() {
        List<Commodity> list = commodityService.list();
        if (list == null || list.isEmpty()) {
            return R.error("list fail");
        }
        return R.success(list);
    }

    @PutMapping("/update")
    public R<String> update(@RequestBody Commodity commodity) {
        if (commodityService.update(commodity)) {
            return R.success("update success");
        }
        return R.error("update fail");
    }

    @DeleteMapping("/delete")
    public R<String> delete(@RequestParam Long id) {
        if (commodityService.delete(id)) {
            return R.success("delete success");
        }
        return R.error("update fail");
    }
}
