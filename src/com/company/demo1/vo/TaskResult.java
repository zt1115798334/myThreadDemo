package com.company.demo1.vo;

public class TaskResult<R> {
    //方法本身运行是否正确的结果类型
    private final TaskResultType resultType;
    //方法的业务结果数据；
    private final R returnValue;
    //这里放方法失败的原因
    private final String reason;

    public TaskResult(TaskResultType resultType, R returnValue, String reason) {
        this.resultType = resultType;
        this.returnValue = returnValue;
        this.reason = reason;
    }

    public TaskResult(R returnValue) {
        this.resultType = TaskResultType.SUCCESS;
        this.returnValue = returnValue;
        this.reason = "success";
    }

    public TaskResultType getResultType() {
        return resultType;
    }

    public R getReturnValue() {
        return returnValue;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "resultType=" + resultType +
                ", returnValue=" + returnValue +
                ", reason='" + reason + '\'' +
                '}';
    }
}
