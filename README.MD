# 技术转换说明

[toc]

## motan 转 feign

一般 motan 定义的接口会需要先定义一个接口类 `interface`

例如

```java
package test.api;

public interface DemoApi {

    String hello(String world);

    User updateUser(User user);
}

```

服务提供方只需要实现该接口，同时使用注解 `@MotanService()` 明确该服务为`motan` 的服务,

例如

```java

import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import test.api.DemoApi;
import test.api.User;

@MotanService(export = "demoMotan:8002")
public class DemoApiImpl implements DemoApi {
    @Override
    public String hello(String world) {
        return "hello:"+ world;
    }

    @Override
    public User updateUser(User user) {
        user.setRemark("信息已经修改");
        return user;
    }
}
```

当客户端调用时只需使用注解标明某对象为motan 调用接口即可

例如

```java
@MotanReferer
DemoApi demoApi;
```

当需要改造成 `feign` 调用`restfulapi` 接口时

接口定义类需要增加对应的注解,例如：

注意其中的 `@FeignClient("demo")` ,其中demo 为服务名，另外还有一些spring mvc 的注解，这些注解与spring mvc 定义的restful 接口一致。

```java
@FeignClient("demo")
@RequestMapping("_api")
public interface DemoRestApi {

    @RequestMapping("/")
    String hello(@RequestParam("world") String world);

    @RequestMapping("/user")
    User updateUser(@RequestBody User user);
}
```

下面为spring mvc 实现类

```java
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.api.DemoApi;
import test.api.User;
@RestController
@RequestMapping("_api")
public class DemoApiRestfulImpl implements DemoApi {
    @Override
    @RequestMapping
    public String hello(@RequestParam("world") String world) {
        return "restful hello !" +world;
    }

    @Override
    @RequestMapping("user")
    public User updateUser(@RequestBody User user) {
        user.setRemark("restful 信息已经修改");
        return user;
    }
}
```



请注意，当一个服务有直接提供给浏览器端和后端调用的接口时，后端接口请用统一的url前缀区分。例如`@RequestMapping("_api")` .

当客户端调用时，直接使用 `@Autowired` 标记即可

```java
    @Autowired
    DemoRestApi demoRestApi;
```

当提示找不到对应的服务时，请检查包扫描路径，使用 `@EnableFeignClients(basePackages = "test.api")` 

当测试不使用注册中心时，可以使用下面的方式配置对应的服务。其中 `demo` 与 `@FeignClient` 注解中的名称对应

```java
demo:
  ribbon:
    listOfServers: http://localhost:8081
```



## jpa 转 mybatic

| mybatis | jpa        | 说明                                                   |
| ------- | ---------- | ------------------------------------------------------ |
| Vo      | Entity     | 两者都需要一个pojo 类完成与数据库的映射。              |
| Mapper  | Repository | 可以理解成查询每个实体对应的工具类，一般为接口非实现类 |
|         |            |                                                        |

转换时注意点。

mybatis 一般没有一对多等关联操作。jpa 转mybatis 后，需要自动手工完成对应子类的查询

例如

```java
//jpa code ...
List<Order> orders = user.getOrders();

//mybatis code ...
Long userId = user.getId();
List<Order> orders = orderMappers.findByUserId(userId);

```

​	

关于分页处理，mybatis 需要自己手写或者封装插件，对应比较著名的组件有

>  [mybatis plus](https://github.com/baomidou/mybatis-plus)
>
> https://github.com/pagehelper/Mybatis-PageHelper
>
> https://github.com/abel533/Mapper

