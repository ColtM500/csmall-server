package cn.tedu.csmall.product.web;

/*
    枚举就是私有权限
 */
public enum ServiceCode {

    OK(20000),
    ERR_BAD_REQUEST(40000),
    ERR_CONFLICT(40900),
    ERR_UNKOWN(9999);

    private Integer value;

    public Integer getValue(){
        return value;
    }

    //通过构造方法的参数为属性赋值
    ServiceCode(Integer value){
        this.value = value;
    }
}
