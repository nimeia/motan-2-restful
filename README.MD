# 技术转换说明

[toc]



## 样例阅读说明

### 项目说明

| 项目   | 说明                                                         |
| ------ | ------------------------------------------------------------ |
| api    | 主要演示motan 转 feign后的 接口层的变化                      |
| client | 主要演示了怎样使用feign 调用后端接口                         |
| server | 主要演示motan 转feign 的交换器层的变化 与 jpa 转mybatis 的变化 |

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

### 样例说明

样例程序入口

> ```java
> test.server.web.OrmController
> ```

```java
/**
* jap 使用例子
*
* @return
*/
@RequestMapping("jpa")
public Object jpa() {
    //jpa 保存级联例子
    businessUserService.japSave();
    //jpa 查询例子
    businessUserService.jpaQuery();

    return Maps.newHashMap();
}

@RequestMapping("mybatis")
public Object mybatis() {
    // mybatis 保存例子，例子中使用了mybatis plus ，未使用过的同事请先阅读对应的文档
    List<User> userPos = businessUserService.mybatisSave();
    // mybatis 查询例子
    businessUserService.mybatisQuery();

    return userPos;
}

/**
     * jpa 转 mybatis 显示
     * @return
     */
@RequestMapping("j2m")
public Object jpaToMybatis(){

    UserEntity userEntity = new UserEntity();
    userEntity.setName("jpa to mybatis demo");
    businessUserService.jpaToMybatisDemo(userEntity);

    return null;
}
```

> 如果对jpa 或者mybatis 不是很熟悉的用户，请先阅读 jpa 与 mybatis 的两个方法。
>
> jpaToMybatis 方法演示了的是一个转换的例子
>
> ***开发人员改造时请先阅读完对应的代码***

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

​	关于分页处理，mybatis 需要自己手写或者封装插件，对应比较著名的组件有

>  [mybatis plus](https://github.com/baomidou/mybatis-plus)
>
> https://github.com/pagehelper/Mybatis-PageHelper
>
> https://github.com/abel533/Mapper

下面以 `mybatis plus ` 插件整合为例描述整个迁移过程

### 整合mybatis plus 

-  增加对应的依赖

  ```xml
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-data-jpa</artifactId>
          </dependency>
  
  <!--        mybatis plus 依赖， generator 用于从数据库反向生成代码-->
          <dependency>
              <groupId>com.baomidou</groupId>
              <artifactId>mybatis-plus-boot-starter</artifactId>
              <version>3.2.0</version>
          </dependency>
          <dependency>
              <groupId>com.baomidou</groupId>
              <artifactId>mybatis-plus-generator</artifactId>
              <version>3.4.1</version>
              <scope>test</scope>
          </dependency>
  
  <!--        用于开发时输出对应的sql 日志-->
          <dependency>
              <groupId>com.googlecode.log4jdbc</groupId>
              <artifactId>log4jdbc</artifactId>
              <version>1.2</version>
          </dependency>
  <!--        方便演示使用内嵌的数据库，web 界面请见 application.yml-->
          <dependency>
              <groupId>com.h2database</groupId>
              <artifactId>h2</artifactId>
          </dependency>
  ```

  其中需要注意的是，当去除`spring-boot-starter-data-jpa` 后可能会报没有数据源相关依赖，需要改成引用`spring-boot-starter-data-jdbc`

  

  ### MybatisPlug  插件配置

  下面主要启动了mybatisplug 的分页插件，
  
  ```java
  @Configuration
  @MapperScan("test.server.mybatis.mapper")
  public class MybatisConfig {
  
      /**
       * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
       */
      @Bean
      public MybatisPlusInterceptor mybatisPlusInterceptor() {
          MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
          interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
          return interceptor;
      }
  
      @Bean
      public ConfigurationCustomizer configurationCustomizer() {
          return configuration -> configuration.setUseDeprecatedExecutor(false);
      }
  
  }
  ```
  
  

  ### 相关配置项
  
  例子中使用log4jdbc输出sql ,配置如下
  
  ```yml
  spring:
    jpa:
      hibernate:
        ddl-auto: update
    datasource:
  #    url: jdbc:h2:~/test
      url: jdbc:log4jdbc:h2:~/test
      username: sa
      password: sa
      driver-class-name: net.sf.log4jdbc.DriverSpy
    #    driver-class-name: org.h2.Driver
    h2:
      console:
        enabled: true
        settings:
          web-allow-others: true
          trace: true
        path: /h2
  logging:
    level:
      jdbc: error
      jdbc.sqltiming: info
  
  ```
  
  

相关配置主要有下面三部分

```yaml
#    url: jdbc:h2:~/test
    url: jdbc:log4jdbc:h2:~/test
```

```yaml
    driver-class-name: net.sf.log4jdbc.DriverSpy
  #    driver-class-name: org.h2.Driver
```

```yaml
    jdbc: error
    jdbc.sqltiming: info
```

### 代码自动生成

配置反射代码生成，详细主见 `MybatisGenerator.java` 

```java
package test.server;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MybatisGenerator {

        /*代码生成器
        AutoGenerator 是 MyBatis-Plus 的代码生成器，通过 AutoGenerator 可以快速生成 Entity、Mapper、Mapper XML、Service、Controller 等各个模块的代码，极大的提升了开发效率。

        特别说明:

        自定义模板有哪些可用参数？Github Gitee AbstractTemplateEngine 类中方法 getObjectMap 返回 objectMap 的所有值都可用。

        演示效果图：

        relationship*/

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = "D:\\dev\\motan-demo\\server";
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("hgq@sinosoft");
        gc.setOpen(false);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://localhost:3306/ant?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setUrl("jdbc:h2:~/test");
        dsc.setDbType(DbType.H2);
        // dsc.setSchemaName("public");
        dsc.setDriverName("org.h2.Driver");
        dsc.setUsername("sa");
        dsc.setPassword("sa");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();

//        pc.setModuleName(scanner("模块名"));
        pc.setModuleName("mybatis");

        pc.setParent("test.server");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(false);
//        strategy.setEntityLombokModel(true);

        strategy.setRestControllerStyle(true);
        // 公共父类

//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");

//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setInclude("USER,ORDERT".split(","));


        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}

```

其中代码中需要注意带注释的地方，使用请先看一遍代码。另外代码生成器需要选择对应的模板引擎，例子中使用

```xml
<dependency>	
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
```



代码生成器会自动生成如下格式代码。

```shell
D:.
├─controller
│      OrdertController.java
│      UserController.java
│
├─entity
│      Ordert.java
│      User.java
│
├─mapper
│      OrdertMapper.java
│      UserMapper.java
│
└─service
    │  IOrdertService.java
    │  IUserService.java
    │
    └─impl
            OrdertServiceImpl.java
            UserServiceImpl.java
------------------------------
D:.
│  application.yml
│
└─mapper
    └─mybatis
            OrdertMapper.xml
            UserMapper.xml

```

其中controller 是controller ，如果没用可以删除。***生成代码时建议生成到一个独立的 package 下，再改自己的需要进行重构命名***

在改造时 你可以使用mapper 直接代替 jpa 中的 repos 。service 是自动生成的一些实用方法的封装，在项目当前情况你可以当前一个dao 层代替直接访问 mapper 。 为了与现有的service 层区分开，建议在代码生成时配置生成后缀为其它，例如dao

```java
        gc.setServiceName("I%sDao");
        gc.setServiceImplName("%sDaoImpl");
```

### 一对多关联配置

mybatis 的级联查询主要使用了 `association` 和 `collection` 。配置如下 

`usermapp.mxl`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="test.server.mybatis.mapper.UserMapper">

    <resultMap id="userResultMap" type="test.server.mybatis.entity.User">
        <id column="id" property="id"></id>
        <result property="name" column="name"/>
        <collection property="ordertList" column="id" select="selectOrderByUserId"/>
    </resultMap>

    <select id="selectOrderByUserId" parameterType="int" resultMap="test.server.mybatis.mapper.OrdertMapper.orderResultMap">
        select id,name,user_id from ordert where user_id = #{id}
    </select>

    <select id="selectByUserId" parameterType="long" resultMap="userResultMap">
        select id,name from user where id= #{id}
    </select>

</mapper>
```

`OrdertMapper.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="test.server.mybatis.mapper.OrdertMapper">
    <resultMap id="orderResultMap" type="test.server.mybatis.entity.Ordert">
        <id column="id" property="id"></id>
        <result property="name" column="name"/>
        <result property="userId" column="user_id"/>
        <association property="user"
                     column="user_id" select="test.server.mybatis.mapper.UserMapper.selectByUserId"></association>
    </resultMap>
</mapper>
```

这两个文件的变更都为新增。需要生成自动改mybatis 的规范配置，这不做说明。

在映射类里需要用tablename 注意指定对应的resultmap,否则不生效。

```java
package test.server.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 * 
 * </p>
 *
 * @author hgq@sinosoft
 * @since 2020-11-11
 */
@TableName(value = "ORDERT",resultMap = "orderResultMap")
public class Ordert implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //    private Long userId;

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

//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }

    @Override
    public String toString() {
        return "Ordert{" +
                "}";
    }

}

```

其中一对多的关联需要自动加。对应的注解中增加resultmap `@TableName(value = "ORDERT",resultMap = "orderResultMap")`

### 级联保存

请在自动生成的代码 service层中自行处理。mybatis 并没有官方的解决方案

### 注意事项

**不能直接把po ,entity 层的对象输出成json**



