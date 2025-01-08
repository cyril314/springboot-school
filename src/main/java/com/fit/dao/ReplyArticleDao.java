package com.fit.dao;

import com.fit.base.BaseDao;
import com.fit.entity.ReplyArticle;
import org.apache.ibatis.annotations.Mapper;

/**
 * @AUTO 回复文章接口
 * @Author AIM
 * @DATE 2025-01-07 18:18:55
 */
@Mapper
public interface ReplyArticleDao extends BaseDao<ReplyArticle> {
}