package com.fit.dao;

import com.fit.base.BaseDao;
import com.fit.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @AUTO 接口
 * @Author AIM
 * @DATE 2025-01-07 18:18:55
 */
@Mapper
public interface StudentDao extends BaseDao<Student> {
}