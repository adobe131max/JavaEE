package edu.whu.springbootdemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.whu.springbootdemo.entity.Supplier;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SupplierMapper extends BaseMapper<Supplier> {
}
