package com.fit.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fit.entity.Classes;
import com.fit.entity.ClassesNews;
import com.fit.entity.Student;
import com.fit.service.ClassesNewsService;
import com.fit.service.ClassesService;
import com.fit.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 包含班级列表菜单内的所有操作
 */
@Controller
public class ClassesController {

    public static int pagesize = 10;

    @Autowired
    private ClassesService classesService;
    @Autowired
    private ClassesNewsService classesNewsService;
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/manager/classes", method = RequestMethod.GET)
    public ModelAndView listStudent(String pagenum, Classes classes) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("classes");
        mv.addObject("sidebar", "classes");
        int num = 1;
        if (null != pagenum) {
            num = Integer.parseInt(pagenum);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageNumber", num);
        params.put("pageSize", pagesize);
        List<Classes> list = classesService.findList(params);
        mv.addObject("classesList", list);
        mv.addObject("length", list.size());
        mv.addObject("pagenum", num);
        mv.addObject("classes", classes);
        return mv;
    }

    @RequestMapping(value = "/manager/addclassespage", method = RequestMethod.GET)
    public ModelAndView addClassesPage() {
        ModelAndView mv = new ModelAndView("addclasses");
        mv.addObject("sidebar", "classes");
        return mv;
    }

    @RequestMapping(value = "/manager/addclasses", method = RequestMethod.POST)
    public ModelAndView addClasses(Classes classes) {
        ModelAndView mv = new ModelAndView();
        Classes cls = classesService.get(classes.getId());
        if (null == cls) {
            mv.setViewName("redirect:/manager/classes");
            classes.setStudentCount(0);
            classesService.save(classes);
        } else {
            mv.setViewName("redirect:/manager/addclassespage");
            mv.addObject("name", classes.getName());
            mv.addObject("headteacher", classes.getHeadTeacher());
            mv.addObject("notice", "已存在编号为" + classes.getId() + "的班级");
        }
        return mv;
    }

    @RequestMapping(value = "/manager/managerstudentpage", method = RequestMethod.GET)
    public ModelAndView studentPage(Long classesId) {
        ModelAndView mv = new ModelAndView("addstudents");
        Classes cls = classesService.get(classesId);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("classId", classesId);
        List<Student> stlist = studentService.findList(params);
        mv.addObject("sidebar", "classes");
        mv.addObject("cls", cls);
        mv.addObject("stlist", stlist);
        mv.addObject("length", stlist.size());
        return mv;
    }

    @RequestMapping(value = "/manager/classesnewspage", method = RequestMethod.GET)
    public ModelAndView classesnewsPage(Long classesId) {
        ModelAndView mv = new ModelAndView();
        Classes cls = classesService.get(classesId);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("classId", classesId);
        List<ClassesNews> cnlist = classesNewsService.findList(params);
        mv.setViewName("addclassesnews");
        mv.addObject("sidebar", "classes");
        mv.addObject("cls", cls);
        mv.addObject("cnlist", cnlist);
        return mv;
    }

    @RequestMapping(value = "/manager/addclassesnews", method = RequestMethod.POST)
    public ModelAndView addClassesNews(ClassesNews classesNews) {
        ModelAndView mv = new ModelAndView();
        classesNews.setCreatDate(new Date());
        classesNewsService.save(classesNews);
        mv.addObject("notice", "添加班级动态成功");
        mv.addObject("classesid", classesNews.getClassId());
        mv.setViewName("redirect:/manager/classesnewspage");
        return mv;
    }

    @RequestMapping(value = "/manager/deleteclassesnews", method = RequestMethod.GET)
    public ModelAndView deleteClassesNews(Long classesId, Long id) {
        ModelAndView mv = new ModelAndView("redirect:/manager/classesnewspage");
        mv.addObject("classesid", classesId);
        classesNewsService.delete(id);
        mv.addObject("notice", "删除动态成功");
        return mv;
    }

    @RequestMapping(value = "/manager/addstudent", method = RequestMethod.POST)
    public ModelAndView addStudent(Student student) {
        ModelAndView mv = new ModelAndView();
        Student stu = studentService.get(student.getId());
        if (stu == null) {
            studentService.save(student);
            classesService.updateClassStudentCount(student.getClassId());
            mv.addObject("notice", "添加学生成功");
        } else {
            mv.addObject("notice", "已经存在编号为" + student.getId() + "的学生(" + stu.getName() + ")！");
        }
        mv.addObject("classesid", student.getClassId());
        mv.setViewName("redirect:/manager/managerstudentpage");
        return mv;
    }

    @RequestMapping(value = "/manager/deletestudent", method = RequestMethod.GET)
    public ModelAndView deleteStudent(Long studentId, Long classId) {
        ModelAndView mv = new ModelAndView();
        studentService.delete(studentId);
        classesService.updateClassStudentCount(classId);
        mv.addObject("classesid", classId);
        mv.addObject("notice", "删除学生信息成功");
        mv.setViewName("redirect:/manager/managerstudentpage");
        return mv;
    }

    @RequestMapping(value = "/manager/updatestudent", method = RequestMethod.POST)
    public ModelAndView updateStudent(Student student) {
        ModelAndView mv = new ModelAndView("redirect:/manager/managerstudentpage");
        studentService.update(student);
        mv.addObject("classesid", student.getClassId());
        mv.addObject("notice", "编辑学生信息成功");
        return mv;
    }
}
