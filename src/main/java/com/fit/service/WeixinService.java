package com.fit.service;

import com.fit.entity.*;
import com.fit.util.WeixinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeixinService {

    @Autowired
    private MessageService messageService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private ExamService examService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMessageService studentMessageService;
    @Autowired
    private ClassesNewsService classesNewsService;
    @Autowired
    private ReplyArticleService replyArticleService;
    @Autowired
    private ExamMarkService examMarkService;


    /**
     * 查询学生最近一次考试情况
     *
     * @param studentId 学生编号
     * @return 返回考试情况字符串
     */
    public String getSingleExamMarkStringByStudentId(Long studentId) {
        StringBuilder sb = new StringBuilder();
        Student student = studentService.get(studentId);
        if (student == null) {
            sb.append("您好，未找到编号为").append(studentId).append("的学生！");
        } else {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("studentId", studentId);
            params.put("pageNumber", 0);
            params.put("pageSize", 1);
            List<ExamMark> list = examMarkService.findList(params);
            sb.append("您好，编号为").append(studentId).append("的学生(").append(student.getName());
            if (list == null || list.size() < 1 || list.get(0).getExamId() == null) {
                sb.append(")无考试记录！");
            } else {
                ExamMark em = list.get(0);
                Exam e = examService.get(em.getExamId());
                DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                sb.append(")最近一次考试记录如下:\n").append("科目：").append(e.getCourse()).append("\n分数：").append(em.getMark()).append("\n班级排名：").append(em.getRank()).append("\n考试时间：").append(sf.format(em.getExamTime())).append("\n老师备注：").append(em.getRemark()).append("\n试卷满分：").append(e.getFullMarks()).append("\n班级均分：").append(e.getAverage()).append("\n班级最高分：").append(e.getTopMark()).append("\n班级最低分：").append(e.getLowestMark()).append("\n考试说明：").append(e.getRemark());
            }
        }
        return sb.toString();
    }

    /**
     * 查询学生历次考试情况(最近10次)
     *
     * @param studentId 学生编号
     * @return 返回考试情况字符串
     */
    public String getExamMarkHistoryStringByStudentId(Long studentId) {
        StringBuilder sb = new StringBuilder();
        Student student = studentService.get(studentId);
        if (student == null) {
            sb.append("您好，未找到编号为").append(studentId).append("的学生！");
        } else {
            sb.append("您好，编号为").append(studentId).append("的学生(").append(student.getName());
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("studentId", studentId);
            params.put("pageNumber", 0);
            params.put("pageSize", 10);
            List<ExamMark> list = examMarkService.findList(params);
            if (list == null || list.size() < 1 || list.get(0).getExamId() == null) {
                sb.append(")无考试记录！");
            } else {
                DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                Exam e = null;
                sb.append(")最近10次考试情况如下:");
                for (ExamMark em : list) {
                    e = examService.get(em.getExamId());
                    sb.append("\n考试时间：").append(sf.format(em.getExamTime())).append("\n科目：").append(e.getCourse()).append("\n分数：").append(em.getMark()).append("\n班级排名：").append(em.getRank()).append("\n老师备注：").append(em.getRemark()).append("\n试卷满分：").append(e.getFullMarks()).append("\n班级均分：").append(e.getAverage()).append("\n班级最高分：").append(e.getTopMark()).append("\n班级最低分：").append(e.getLowestMark()).append("\n考试说明：").append(e.getRemark()).append("\n------分割线-------");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 根据学生编号查询学生最近的老师留言信息
     *
     * @param studentId 学生编号
     * @return 以字符串形式返回老师留言信息
     */
    public String getSingleStudentMessageByStudentId(Long studentId) {
        StringBuilder sb = new StringBuilder();
        Student student = studentService.get(studentId);
        if (student == null) {
            sb.append("您好，未找到编号为").append(studentId).append("的学生！");
        } else {
            sb.append("您好，编号为").append(studentId).append("的学生(").append(student.getName());
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("studentId", studentId);
            params.put("pageNumber", 0);
            params.put("pageSize", 1);
            List<StudentMessage> list = studentMessageService.findList(params);
            if (list == null || list.size() < 1) {
                sb.append(")无老师留言！");
            } else {
                sb.append(")最近老师留言如下:");
                DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                StudentMessage sm = list.get(0);
                sb.append("\n留言时间：").append(sf.format(sm.getCreatDate())).append("\n留言内容：").append(sm.getContent());
            }
        }
        return sb.toString();
    }

    /**
     * 根据学生编号查询学生的老师留言信息记录（最近10次）
     *
     * @param studentId 学生编号
     * @return 以字符串形式返回老师留言信息
     */
    public String getStudentMessageHistoryByStudentId(Long studentId) {
        StringBuilder sb = new StringBuilder();
        Student student = studentService.get(studentId);
        if (student == null) {
            sb.append("您好，未找到编号为").append(studentId).append("的学生！");
        } else {
            sb.append("您好，编号为").append(studentId).append("的学生(").append(student.getName());
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("studentId", studentId);
            params.put("pageNumber", 0);
            params.put("pageSize", 10);
            List<StudentMessage> list = studentMessageService.findList(params);
            if (list == null || list.size() < 1) {
                sb.append(")无老师留言！");
            } else {
                sb.append(")最近(10次)老师留言如下:");
                DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                for (StudentMessage sm : list) {
                    sb.append("\n留言时间：").append(sf.format(sm.getCreatDate())).append("\n留言内容：").append(sm.getContent()).append("\n------分割线-------");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 根据学生编号查询学生所在班级的班级动态
     *
     * @param studentId 学生编号
     * @return 以字符串形式返回学生所在班级的班级动态
     */
    public String getSingleClassesNewsByStudentId(Long studentId) {
        StringBuilder sb = new StringBuilder();
        Student student = studentService.get(studentId);
        if (student == null) {
            sb.append("您好，未找到编号为").append(studentId).append("的学生！");
        } else {
            sb.append("您好，编号为").append(studentId).append("的学生(").append(student.getName());
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("classId", student.getClassId());
            params.put("pageNumber", 0);
            params.put("pageSize", 1);
            List<ClassesNews> list = classesNewsService.findList(params);
            if (list == null || list.size() < 1) {
                sb.append(")所在班级无班级动态！");
            } else {
                sb.append(")最近班级动态如下:");
                DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                ClassesNews cn = list.get(0);
                sb.append("\n动态时间：").append(sf.format(cn.getCreatDate())).append("\n动态内容：").append(cn.getContent());
            }
        }
        return sb.toString();
    }

    /**
     * 根据学生编号查询学生所在班级的班级动态
     *
     * @param studentId 学生编号
     * @return 以字符串形式返回学生所在班级的班级动态
     */
    public String getClassesNewsHistoryByStudentId(Long studentId) {
        StringBuilder sb = new StringBuilder();
        Student student = studentService.get(studentId);
        if (student == null) {
            sb.append("您好，未找到编号为").append(studentId).append("的学生！");
        } else {
            sb.append("您好，编号为").append(studentId).append("的学生(").append(student.getName());
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("classId", student.getClassId());
            params.put("pageNumber", 0);
            params.put("pageSize", 10);
            List<ClassesNews> list = classesNewsService.findList(params);
            if (list == null || list.size() < 1) {
                sb.append(")所在班级无班级动态！");
            } else {
                sb.append(")最近班级动态如下:");
                DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                for (ClassesNews cn : list) {
                    sb.append("\n动态时间：").append(sf.format(cn.getCreatDate())).append("\n动态内容：").append(cn.getContent()).append("\n------分割线-------");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 新增Message对象到数据库中
     */
    public void addMessage(Message message) {
        messageService.save(message);
    }


    /**
     * 将数据库中Message数据分页查出
     *
     * @param start 其实数据条数
     * @param size  展示数据每页的大小
     */
    public List<Message> listMessage(int start, int size) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageNumber", start);
        params.put("pageSize", size);
        return messageService.findList(params);
    }

    /**
     * 将数据库中Message数据分页查出
     *
     * @param start 其实数据条数
     * @param size  展示数据每页的大小
     */
    public List<Reply> listReply(int start, int size) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageNumber", start);
        params.put("pageSize", size);
        return replyService.findList(params);
    }


    /**
     * 保存回复消息至数据库中，如果为news类型消息将article一并保存
     */
    public void addReply(Reply reply) {
        replyService.save(reply);
        if (WeixinUtil.NEWS.equals(reply.getMsgType())) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("replyId", reply.getId());
            List<ReplyArticle> articles = replyArticleService.findList(params);
            for (ReplyArticle a : articles) {
                a.setReplyId(reply.getId());
                replyArticleService.save(a);
            }
        }
    }
}
