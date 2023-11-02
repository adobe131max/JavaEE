package whu.edu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import whu.edu.entity.Commodity;
import whu.edu.mapper.CommodityMapper;
import whu.edu.service.CommodityService;

@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {
}