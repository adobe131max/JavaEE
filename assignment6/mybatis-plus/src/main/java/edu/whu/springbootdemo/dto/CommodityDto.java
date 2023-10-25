package edu.whu.springbootdemo.dto;

import edu.whu.springbootdemo.entity.Commodity;
import edu.whu.springbootdemo.entity.Supplier;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommodityDto extends Commodity {

    private List<Supplier> suppliers = new ArrayList<>();
}
