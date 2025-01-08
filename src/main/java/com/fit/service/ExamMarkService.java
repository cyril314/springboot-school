package com.fit.service;

import com.fit.base.BaseService;
import com.fit.entity.ExamMark;
import com.fit.dao.ExamMarkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @AUTO 服务实现类
 * @Author AIM
 * @DATE 2025-01-07 18:18:55
 */
@Service
public class ExamMarkService extends BaseService<ExamMarkDao, ExamMark> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> queryExamMarkList(Long studentId) {
        return jdbcTemplate.queryForList("SELECT em.mark,em.rank,e.course,e.exam_time as examTime,e.full_marks as fullMarks," + "e" +
                ".top_mark as topMark,e.lowest_mark as lowestMark,e.average from exam_mark em LEFT JOIN exam e on em.exam_id=e.id where " +
                "em.student_id=? limit 100", studentId);
    }
}