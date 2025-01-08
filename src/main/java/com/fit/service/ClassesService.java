package com.fit.service;

import com.fit.base.BaseService;
import com.fit.entity.Classes;
import com.fit.dao.ClassesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @AUTO 服务实现类
 * @Author AIM
 * @DATE 2025-01-07 18:18:55
 */
@Service
public class ClassesService extends BaseService<ClassesDao, Classes> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateClassStudentCount(Long classId) {
        jdbcTemplate.update("update class set student_count=(select count(id) from student where class_id=?) where id=?", new Object[]{classId, classId});
    }
}