package test.api;

public interface DemoApi {

    String hello(String world);

    User updateUser(User user);

    /**
     * 只是用于演示多参数转换
     * @param user
     * @param order
     */
    void update(User user, Order order);
}
