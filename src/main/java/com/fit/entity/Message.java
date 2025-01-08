package com.fit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fit.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
public class Message extends BaseEntity {
    /**  (主健ID) (无默认值) */
    private Long id;

    /**  (无默认值) */
    private String toUserName;

    /**  (无默认值) */
    private String fromUserName;

    /**  (无默认值) */
    private String msgType;

    /**   (默认值为: CURRENT_TIMESTAMP) */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date creatDate;

    /**  (无默认值) */
    private String msgId;

    /**  (无默认值) */
    private String content;

    /**  (无默认值) */
    private String picUrl;

    /**  (无默认值) */
    private String title;

    /**  (无默认值) */
    private String description;

    /**  (无默认值) */
    private String url;

    /**  (无默认值) */
    private String locationX;

    /**  (无默认值) */
    private String locationY;

    /**  (无默认值) */
    private String scale;

    /**  (无默认值) */
    private String label;

    /**  (无默认值) */
    private String event;

    /**  (无默认值) */
    private String eventKey;
}