package test.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("demo")
@RequestMapping("_api")
public interface DemoRestApi {

    @RequestMapping("/")
    String hello(@RequestParam("world") String world);

    @RequestMapping("/user")
    User updateUser(@RequestBody User user);

    /**
     * 替换例子，把motan 的多参数封装成一个参数传输
     * @param rpcRequestVo
     * @return
     */
    @RequestMapping("update")
    List<User> update(@RequestBody RpcRequestVo<User> rpcRequestVo);
}
