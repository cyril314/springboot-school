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
 * @AUTO 回复
 * @Author AIM
 * @DATE 2025-01-08 12:30:24
 */
@Data
@Builder
@NoArgsConstructor //无参数的构造方法
@AllArgsConstructor //包含所有变量构造方法
public class Reply extends BaseEntity {
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

    /** 回复消息内容 (无默认值) */
    private String content;

    /**  (无默认值) */
    private String musicUrl;

    /**  (无默认值) */
    private String hqMusicUrl;

    /**  (无默认值) */
    private Integer articleCount;
}