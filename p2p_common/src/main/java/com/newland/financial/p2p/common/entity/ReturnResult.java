package com.newland.financial.p2p.common.entity;

import lombok.Getter;

/**
 * @author cengdaijuan
 */
@Getter
public class ReturnResult {
    /**
     * 常量.
     */
    public static final String SUCCESS = "1";
    /**
     * 常量.
     */
    public static final String ERROR = "0";

    /**
     * 构造方法.
     *
     * @param rst Object
     */
    public ReturnResult(final Object rst) {
        this.result = rst;
    }

    /**
     * 构造方法.
     *
     * @param msd String
     * @param rst Object
     * @param skf StringBuffer
     */
    public ReturnResult(final String msd,
                        final Object rst, final StringBuffer skf) {
        this.msgCode = msd;
        this.result = rst;
        this.stackflow = skf;
    }

    /**
     * 返回码.
     */
    private String msgCode = "1";

    /**
     * 返回结果.
     */
    private Object result = null;
    /**
     * 堆栈信息.
     */
    private StringBuffer stackflow;
}
