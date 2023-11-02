package whu.edu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommoditySupplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "commodity_id", type = IdType.AUTO)
    private Long commodityId;

    private Long supplierId;
}
