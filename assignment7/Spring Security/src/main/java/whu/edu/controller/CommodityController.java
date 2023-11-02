package whu.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import whu.edu.common.R;
import whu.edu.entity.Commodity;
import whu.edu.entity.CommodityDto;
import whu.edu.entity.Supplier;
import whu.edu.entity.CommoditySupplier;
import whu.edu.service.CommodityService;
import whu.edu.service.CommoditySupplierService;
import whu.edu.service.SupplierService;

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

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('user/update')")
    public R<String> save(@RequestBody Commodity commodity) {
        commodityService.save(commodity);

        return R.success("add success");
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user/query')")
    @Cacheable(value = "commodity", key = "'list'")
    public R<List<Commodity>> list() {
        log.info("query commodity list");
        List<Commodity> list = commodityService.list();

        return R.success(list);
    }

    @GetMapping("/with")
    @PreAuthorize("hasAuthority('user/query')")
    public R<List<CommodityDto>> withSupplier(@RequestBody Commodity commodity) {
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
    @PreAuthorize("hasAuthority('user/update')")
    @CacheEvict(value = "commodity", allEntries = true)
    public R<String> update(@RequestBody Commodity commodity) {
        commodityService.updateById(commodity);

        return R.success("update success");
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('user/update')")
    @CacheEvict(value = "commodity", allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids) {
        commodityService.removeByIds(ids);

        return R.success("delete success");
    }
}
