package com.fit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fit.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @AUTO 
 * @Author AIM
 * @DATE 2025-01-07 18:18:55
 */
@Data
@Builder
@NoArgsConstructor //无参数的构造方法
@AllArgsConstructor //包含所有变量构造方法
public class Exam extends BaseEntity {
    /** 考试编号 (主健ID) (无默认值) */
    private Long id;

    /** 考试班级 (无默认值) */
    private Long classId;

    /** 科目 (无默认值) */
    private String course;

    /** 考试时间  (默认值为: CURRENT_TIMESTAMP) */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date examTime;

    /** 满分 (无默认值) */
    private BigDecimal fullMarks;

    /** 平均分 (无默认值) */
    private BigDecimal average;

    /** 最高分 (无默认值) */
    private BigDecimal topMark;

    /** 最低分 (无默认值) */
    private BigDecimal lowestMark;

    /** 考试说明 (无默认值) */
    private String remark;
}