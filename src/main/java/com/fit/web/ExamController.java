package com.fit.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fit.entity.Classes;
import com.fit.entity.Exam;
import com.fit.entity.ExamMark;
import com.fit.entity.Student;
import com.fit.service.ClassesService;
import com.fit.service.ExamMarkService;
import com.fit.service.ExamService;
import com.fit.service.StudentService;
import com.fit.util.ExamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 包含考试列表菜单内的所有操作
 */
@Controller
public class ExamController {

    public static final int pagesize = 10;

    @Autowired
    private ClassesService classesService;
    @Autowired
    private ExamService examService;
    @Autowired
    private ExamMarkService examMarkService;
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/manager/exams", method = RequestMethod.GET)
    public ModelAndView listStudent(String pagenum, Exam exam) {
        ModelAndView mv = new ModelAndView("exams");
        mv.addObject("sidebar", "exams");
        int num = 1;
        if (null != pagenum) {
            num = Integer.parseInt(pagenum);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageNumber", (num - 1) * pagesize);
        params.put("pageSize", pagesize);
        List<Exam> list = examService.findList(params);
        List<Classes> clslist = classesService.findList();
        mv.addObject("examList", list);
        mv.addObject("clsList", clslist);
        mv.addObject("length", list.size());
        mv.addObject("pagenum", num);
        mv.addObject("exam", exam);
        return mv;
    }

    @RequestMapping(value = "/manager/addexam", method = RequestMethod.POST)
    public ModelAndView addExam(Exam exam) {
        ModelAndView mv = new ModelAndView("addexam");
        mv.addObject("sidebar", "exams");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("classId", exam.getClassId());
        List<Student> stlist = studentService.findList(params);
        mv.addObject("exam", exam);
        mv.addObject("stlist", stlist);
        return mv;
    }

    @RequestMapping(value = "/manager/viewexam", method = RequestMethod.GET)
    public ModelAndView viewExam(Long id) {
        ModelAndView mv = new ModelAndView("viewexam");
        mv.addObject("sidebar", "exams");
        Exam exam = examService.get(id);
        mv.addObject("exam", exam);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("examId", id);
        List<ExamMark> examMarks = examMarkService.findList(params);
        mv.addObject("examMarks", examMarks);
        return mv;
    }

    @RequestMapping(value = "/manager/addexammark", method = RequestMethod.POST)
    public ModelAndView addExamMark(Exam exam) {
        ModelAndView mv = new ModelAndView("redirect:/manager/exams");
        mv.addObject("sidebar", "exams");
        exam.setExamTime(new Date());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("classId", exam.getClassId());
        List<ExamMark> ems = examMarkService.findList(params);
        exam = ExamUtil.sortExamMark(exam, ems);// 为分数排序
        examService.save(exam);
        return mv;
    }
}
