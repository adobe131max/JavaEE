package edu.whu.springbootdemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.whu.springbootdemo.common.R;
import edu.whu.springbootdemo.dto.CommodityDto;
import edu.whu.springbootdemo.entity.Commodity;
import edu.whu.springbootdemo.entity.CommoditySupplier;
import edu.whu.springbootdemo.entity.Supplier;
import edu.whu.springbootdemo.service.CommodityService;
import edu.whu.springbootdemo.service.CommoditySupplierService;
import edu.whu.springbootdemo.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    CommodityService commodityService;

    @Autowired
    SupplierService supplierService;

    @Autowired
    CommoditySupplierService commoditySupplierService;

    @PutMapping("/save")
    public R<String> save(Commodity commodity) {
        commodityService.save(commodity);

        return R.success("add success");
    }

    @GetMapping("/list")
    public R<List<Commodity>> list(Commodity commodity) {
        LambdaQueryWrapper<Commodity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(commodity.getId() != null, Commodity::getId, commodity.getId());
        List<Commodity> list = commodityService.list(lambdaQueryWrapper);

        return R.success(list);
    }

    @GetMapping("/with")
    public R<List<CommodityDto>> withSupplier(Commodity commodity) {
        LambdaQueryWrapper<Commodity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(commodity.getId() != null, Commodity::getId, commodity.getId());
        List<CommodityDto> commodityDtoList = commodityService
                .list(lambdaQueryWrapper)
                .stream()
                .map(item -> {
                    CommodityDto commodityDto = new CommodityDto();
                    BeanUtils.copyProperties(item, commodityDto);
                    Long id = item.getId();
                    LambdaQueryWrapper<CommoditySupplier> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(CommoditySupplier::getCommodityId, id);
                    List<Supplier> suppliers = commoditySupplierService
                            .list(queryWrapper)
                            .stream()
                            .map(i -> supplierService.getById(i.getSupplierId()))
                            .collect(Collectors.toList());
                    commodityDto.setSuppliers(suppliers);
                    return commodityDto;
                })
                .collect(Collectors.toList());

        return R.success(commodityDtoList);
    }

    @PutMapping("/update")
    public R<String> update(@RequestBody Commodity commodity) {
        commodityService.updateById(commodity);

        return R.success("update success");
    }

    @DeleteMapping("/delete")
    public R<String> delete(@RequestParam List<Long> ids) {
        commodityService.removeByIds(ids);

        return R.success("delete success");
    }
}
