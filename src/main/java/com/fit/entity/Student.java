package com.fit.entity;

import com.fit.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @AUTO 
 * @Author AIM
 * @DATE 2025-01-07 18:18:55
 */
@Data
@Builder
@NoArgsConstructor //无参数的构造方法
@AllArgsConstructor //包含所有变量构造方法
public class Student extends BaseEntity {
    /** 学生学号，通常为班级编号加上序号，如3021 (主健ID) (无默认值) */
    private Long id;

    /** 所属班级编号 (无默认值) */
    private Long classId;

    /** 学生姓名 (无默认值) */
    private String name;

    /** 评论 (无默认值) */
    private String remark;
}