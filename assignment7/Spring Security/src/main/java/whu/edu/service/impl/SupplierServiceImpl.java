package whu.edu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import whu.edu.entity.Supplier;
import whu.edu.mapper.SupplierMapper;
import whu.edu.service.SupplierService;

@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {
}
