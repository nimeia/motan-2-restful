package test.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("ordert")
public class OrderPo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;
}
