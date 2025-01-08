package com.fit.util;

import com.fit.entity.Exam;
import com.fit.entity.ExamMark;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * 考试相关工具类
 */
public class ExamUtil {

    /**
     * 为考试成绩排序 并计算平均分/最高分/最低发
     *
     * @param exam 考试对象
     */
    public static Exam sortExamMark(Exam exam, List ems) {
        Collections.sort(ems);// 对分数进行排序
        BigDecimal topmark = null, lowestmark = null, total = new BigDecimal(0);
        int rank = ems.size();
        for (int i = 0; i < ems.size(); i++) {
            ExamMark em = (ExamMark) ems.get(i);
            if (i == 0) lowestmark = em.getMark();// 最低分
            if (i == ems.size() - 1) topmark = em.getMark();// 最高分
            em.setExamTime(exam.getExamTime());
            em.setRank(rank--);
            total = total.add(em.getMark());
        }
        exam.setAverage(total.divide(new BigDecimal(ems.size()), 2, BigDecimal.ROUND_DOWN));
        exam.setTopMark(topmark);
        exam.setLowestMark(lowestmark);
        return exam;
    }
}
