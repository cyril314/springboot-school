package com.fit.entity;

import com.fit.base.BaseEntity;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @AUTO 
 * @Author AIM
 * @DATE 2025-01-08 12:30:24
 */
@Data
@Builder
@NoArgsConstructor //无参数的构造方法
@AllArgsConstructor //包含所有变量构造方法
public class Classes extends BaseEntity {
    /** 班级编号，302表示三年级二班 (主健ID) (无默认值) */
    private Long id;

    /** 班级名称，三年级二班 (无默认值) */
    private String name;

    /** 班主任姓名 (无默认值) */
    private String headTeacher;

    /** 学生数量 (无默认值) */
    private Integer studentCount;
}