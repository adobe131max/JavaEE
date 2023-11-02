package whu.edu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    String id;

    private String password; //密码在数据库中需要加密保存
}