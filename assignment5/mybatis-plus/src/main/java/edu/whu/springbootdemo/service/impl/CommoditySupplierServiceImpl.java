package edu.whu.springbootdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.whu.springbootdemo.dao.CommoditySupplierMapper;
import edu.whu.springbootdemo.entity.CommoditySupplier;
import edu.whu.springbootdemo.service.CommoditySupplierService;
import org.springframework.stereotype.Service;

@Service
public class CommoditySupplierServiceImpl extends ServiceImpl<CommoditySupplierMapper, CommoditySupplier> implements CommoditySupplierService {
}
