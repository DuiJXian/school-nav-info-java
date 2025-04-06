package com.xz.schoolnavinfo.common.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResult<T> {
    List<T> list;
    int pageNum;
    int pageSize;
    Long total;
}
