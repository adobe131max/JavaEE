package edu.whu.springbootdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.whu.springbootdemo.dao.SupplierMapper;
import edu.whu.springbootdemo.entity.Supplier;
import edu.whu.springbootdemo.service.SupplierService;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {
}
