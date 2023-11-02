package whu.edu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import whu.edu.common.R;
import whu.edu.entity.Supplier;
import whu.edu.service.SupplierService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user/query')")
    @Cacheable(value = "supplier", key = "'list'")
    public R<List<Supplier>> list() {
        log.info("query supplier list");
        List<Supplier> list = supplierService.list();

        return R.success(list);
    }

    @GetMapping("/id")
    @PreAuthorize("hasAuthority('user/query')")
    @Cacheable(value = "supplier", key = "#id")
    public R<Supplier> one(@RequestParam Long id) {
        Supplier supplier = supplierService.getById(id);

        return R.success(supplier);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('user/update')")
    @CacheEvict(value = "supplier", allEntries = true)
    public R<String> save(@RequestBody Supplier supplier) {
        if (supplier != null) {
            supplierService.save(supplier);
            return R.success("add supplier success");
        }
        return R.error("supplier can't be null");
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('user/update')")
    @CacheEvict(value = "supplier", allEntries = true)
    public R<String> update(@RequestBody Supplier supplier) {
        if (supplier != null && supplier.getId() != null) {
            supplierService.updateById(supplier);
            return R.success("update supplier success");
        }
        return R.error("update supplier can't be null");
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('user/update')")
    @CacheEvict(value = "supplier", allEntries = true)
    public R<String> delete(@RequestParam Long id) {
        supplierService.removeById(id);

        return R.success("delete supplier success");
    }
}
