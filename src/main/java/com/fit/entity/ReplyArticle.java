package com.fit.entity;

import com.fit.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @AUTO 回复文章
 * @Author AIM
 * @DATE 2025-01-07 18:18:55
 */
@Data
@Builder
@NoArgsConstructor //无参数的构造方法
@AllArgsConstructor //包含所有变量构造方法
public class ReplyArticle extends BaseEntity {
    /**  (主健ID) (无默认值) */
    private Long id;

    /**  (无默认值) */
    private Long replyId;

    /**  (无默认值) */
    private String title;

    /**  (无默认值) */
    private String content;

    /**  (无默认值) */
    private String picUrl;

    /**  (无默认值) */
    private String url;
}