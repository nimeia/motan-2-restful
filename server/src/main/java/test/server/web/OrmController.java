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

    /**
     * jap 使用例子
     * @return
     */
    @RequestMapping("jpa")
    public Object jpa(){

        //jpa 保存级联例子
        //businessUserService.japSave();

        //jpa 查询例子
        businessUserService.jpaQuery();

        return Maps.newHashMap();
    }

    @RequestMapping("mybatis")
    public Object mybatis(){
        //mybatis 保存例子，例子中使用了mybatis plus ，未使用过的同事请先阅读对应的文档
        List<User> userPos = businessUserService.mybatisSave();


        businessUserService.mybatisQuery();


        return userPos;
    }
}
