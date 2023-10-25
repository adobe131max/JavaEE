package edu.whu.springbootdemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.whu.springbootdemo.entity.CommoditySupplier;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommoditySupplierMapper extends BaseMapper<CommoditySupplier> {
}
