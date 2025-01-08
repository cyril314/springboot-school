package com.fit.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fit.entity.Classes;
import com.fit.entity.ExamMark;
import com.fit.entity.Student;
import com.fit.entity.StudentMessage;
import com.fit.service.*;
import com.fit.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * 包含学生列表菜单内的所有操作
 */
@Controller
public class StudentController {

    public static final int pagesize = 8;

    @Autowired
    private ClassesService classesService;
    @Autowired
    private ExamMarkService examMarkService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMessageService studentMessageService;
    @Autowired
    private ExamService examService;

    @RequestMapping(value = "/manager/students", method = RequestMethod.GET)
    public ModelAndView listStudent(String pagenum, Student student) {
        ModelAndView mv = new ModelAndView("students");
        mv.addObject("sidebar", "students");
        int num = 1;
        if (null != pagenum) {
            num = Integer.parseInt(pagenum);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageNumber", num);
        params.put("pageSize", pagesize);
        List<Student> list = studentService.findList(params);
        List<Classes> clslist = classesService.findList();
        mv.addObject("studentList", list);
        mv.addObject("clsList", clslist);
        mv.addObject("length", list.size());
        mv.addObject("pagenum", num);
        mv.addObject("student", student);
        return mv;
    }

    @RequestMapping(value = "/manager/leavemessage", method = RequestMethod.GET)
    public ModelAndView leaveMessage(Long studentId) {
        ModelAndView mv = new ModelAndView("redirect:/manager/students");
        Student student = studentService.get(studentId);
        if (null != student) {
            mv.setViewName("addstudentmessage");
            mv.addObject("sidebar", "students");
            mv.addObject("student", student);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("pageNumber", 0);
            params.put("pageSize", 100);
            params.put("studentId", studentId);
            List<StudentMessage> list = studentMessageService.findList(params);
            mv.addObject("studentMessageList", list);
        }
        return mv;
    }

    @RequestMapping(value = "/manager/examdetail", method = RequestMethod.GET)
    public ModelAndView examDetail(Long studentId) {
        ModelAndView mv = new ModelAndView("redirect:/manager/students");
        Student student = studentService.get(studentId);
        if (null != student) {
            mv.setViewName("examdetail");
            mv.addObject("sidebar", "students");
            mv.addObject("student", student);
            List<Map<String, Object>> list = examMarkService.queryExamMarkList(studentId);
            mv.addObject("emlist", list);
        }
        return mv;
    }

    @RequestMapping(value = "/manager/addmessage", method = RequestMethod.POST)
    public ModelAndView addMessage(StudentMessage studentMessage) {
        ModelAndView mv = new ModelAndView("redirect:/manager/leavemessage");
        mv.addObject("studentid", studentMessage.getStudentId());
        studentMessage.setCreatDate(new Date());
        studentMessageService.save(studentMessage);
        mv.addObject("notice", "留言成功");
        return mv;
    }

    @RequestMapping(value = "/manager/deletemessage", method = RequestMethod.GET)
    public ModelAndView deleteMessage(Long studentId, Long messageId) {
        ModelAndView mv = new ModelAndView("redirect:/manager/leavemessage");
        mv.addObject("studentid", studentId);
        studentMessageService.delete(messageId);
        mv.addObject("notice", "删除成功");
        return mv;
    }
}
