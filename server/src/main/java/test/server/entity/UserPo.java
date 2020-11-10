package test.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@TableName("user")
public class UserPo {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField(value = "name")
    private String name;


//    List<OrderPo> orderPoList = new ArrayList();
//    @Select(value = {"select * from ordert where user_id = ?","id"})
//    public List<OrderPo> getOrderPoList() {
//        return orderPoList;
//    }
//
//    public void setOrderPoList(List<OrderPo> orderPoList) {
//        this.orderPoList = orderPoList;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
