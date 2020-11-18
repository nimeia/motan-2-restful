package test.server.web;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.server.mybatis.entity.User;
import test.server.service.BusinessUserService;

import java.util.List;

@RestController
@RequestMapping("")
public class OrmController {

    @Autowired
    BusinessUserService businessUserService;

    @RequestMapping("jpa")
    public Object jpa(){

        //jpa 保存级联例子
//        businessUserService.japSave();

        //jpa 查询例子
        businessUserService.jpaQuery();

        return Maps.newHashMap();
    }

    @RequestMapping("mybatis")
    public Object mybatis(){
        List<User> userPos = businessUserService.findAllUserPo();

        return userPos;
    }
}
