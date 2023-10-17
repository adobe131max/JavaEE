package edu.whu.springbootdemo;

import edu.whu.springbootdemo.entity.Commodity;
import edu.whu.springbootdemo.service.CommodityService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SpringBootDemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommodityService commodityService;

    @Test
    public void testSaveCommodity() throws Exception {
        this.mockMvc.perform(get("/commodity/get/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());

        Commodity commodity = new Commodity();
        commodity.setId(1L);
        commodity.setName("MAYBACH S680");
        commodity.setDescription("梅赛德斯-迈巴赫 S 680 4MATIC 匠心高定首发双色版");
        commodity.setPrice(BigDecimal.valueOf(3758000));

        commodityService.save(commodity);

        this.mockMvc.perform(get("/commodity/delete/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Li"));
        commodityService.delete(1L);
    }
}
