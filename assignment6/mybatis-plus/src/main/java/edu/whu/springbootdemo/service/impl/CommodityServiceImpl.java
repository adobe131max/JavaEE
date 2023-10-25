package edu.whu.springbootdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.whu.springbootdemo.dao.CommodityMapper;
import edu.whu.springbootdemo.entity.Commodity;
import edu.whu.springbootdemo.service.CommodityService;
import org.springframework.stereotype.Service;

@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {
}
