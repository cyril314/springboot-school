package com.fit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fit.base.BaseEntity;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @AUTO 
 * @Author AIM
 * @DATE 2025-01-08 12:30:24
 */
@Data
@Builder
@NoArgsConstructor //无参数的构造方法
@AllArgsConstructor //包含所有变量构造方法
public class StudentMessage extends BaseEntity {
    /** 消息编号 (主健ID) (无默认值) */
    private Long id;

    /** 学生编号 (无默认值) */
    private Long studentId;

    /** 创建时间  (默认值为: CURRENT_TIMESTAMP) */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date creatDate;

    /** 消息内容 (无默认值) */
    private String content;
}