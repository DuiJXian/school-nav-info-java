package com.xz.schoolnavinfo.common.data;



public final class ResultBuilder {

  private Result result;

  private ResultBuilder() {
    result = new Result();
  }

  public static ResultBuilder aResult() {
    return new ResultBuilder();
  }

  public ResultBuilder code(String code) {
    result.setCode(code);
    return this;
  }

  public ResultBuilder msg(String msg) {
    // 使用Builder方式构建Result对象，翻译响应数据
    result.setMessage(msg);
    return this;
  }

  public <T> ResultBuilder data(T data) {
    result.setData(data);
    return this;
  }

  public Result build() {
    return result;
  }
}
