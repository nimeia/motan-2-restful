package test.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RpcRequestVo<T> implements Serializable {

    /**
     * 主要用于包裹对应的vo
     */
    T args;

    /**
     * 开始页
     */
    Integer pageNumber;

    /**
     * 结束页
     */
    Integer pageSize;

    List<RpcRequestOrderVo> orders = new ArrayList<>();

    public void addOrder(RpcRequestOrderVo rpcRequestOrderVo){
        orders.add(rpcRequestOrderVo);
    }

    public T getArgs() {
        return args;
    }

    public void setArgs(T args) {
        this.args = args;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<RpcRequestOrderVo> getOrders() {
        return orders;
    }

    public void setOrders(List<RpcRequestOrderVo> orders) {
        this.orders = orders;
    }
}
