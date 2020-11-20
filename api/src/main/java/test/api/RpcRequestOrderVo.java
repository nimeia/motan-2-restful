package test.api;

import java.io.Serializable;

/**
 * 排序参数
 */
public class RpcRequestOrderVo implements Serializable {

    public static final String ORDER_TYPE_DESC = "desc";
    public static final String ORDER_TYPE_ASC = "asc";

    public RpcRequestOrderVo() {
    }

    public RpcRequestOrderVo(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * 排序key
     */
    private String name;

    /**
     * 排序类型 asc desc
     */
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
