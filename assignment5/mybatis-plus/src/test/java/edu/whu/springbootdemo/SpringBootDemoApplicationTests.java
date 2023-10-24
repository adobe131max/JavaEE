package edu.whu.springbootdemo;

import edu.whu.springbootdemo.entity.Commodity;
import edu.whu.springbootdemo.service.CommodityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class SpringBootDemoApplicationTests {

    @Autowired
    CommodityService commodityService;

    @Test
    void testSave() {
        Commodity commodity = new Commodity();
        commodity.setId(114514L);
        commodity.setName("AMG ONE");
        commodity.setPrice(2800000);
        commodityService.save(commodity);
        Commodity commodityInDB = commodityService.getById(commodity.getId());
        assertNotNull(commodityInDB);
        assertEquals(commodity.getName(), commodityInDB.getName());
    }

    @Test
    void testList() {
        List<Commodity> list = commodityService.list();
        assertEquals(2, list.size());
    }
}
