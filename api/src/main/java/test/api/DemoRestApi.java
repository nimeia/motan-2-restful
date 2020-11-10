package test.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("demo")
@RequestMapping("_api")
public interface DemoRestApi {

    @RequestMapping("/")
    String hello(@RequestParam("world") String world);

    @RequestMapping("/user")
    User updateUser(@RequestBody User user);

}
