package com.fit.util;

import java.io.InputStream;
import java.io.Writer;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fit.entity.ExamMark;
import com.fit.entity.Message;
import com.fit.entity.Reply;
import com.fit.entity.ReplyArticle;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 微信相关工具类
 */
public class WeixinUtil {

    public static final String TEXT = "text";
    public static final String IMAGE = "image";
    public static final String LOCATION = "location";
    public static final String LINK = "link";
    public static final String EVENT = "event";
    public static final String MUSIC = "music";
    public static final String NEWS = "news";
    public static final String ERROR_CONTENT = "查询失败，请检查你的回复是否正确。\n查询最近考试情况请回复：学号_考试（如：3021_考试）\n 查询最近最近10次考试情况请回复：学号_考试历史（如：3021_考试历史）\n 查询老师留言情况请回复：学号_留言（如：3021_留言）\n查询老师留言记录（最近10次）情况请回复：学号_留言历史（如：3021_留言历史）\n\n 查询班级动态情况请回复：学号_动态（如：3021_动态）\n查询班级动态记录（最近10次）情况请回复：学号_动态历史（如：3021_动态历史）\n";
    public static final String WELCOME_CONTENT = "欢迎订阅，你可以回复指定内容来了解学生考试情况，老师留言和班级动态。\n查询最近考试情况请回复：学号_考试（如：3021_考试）\n 查询最近最近10次考试情况请回复：学号_考试历史（如：3021_考试历史）\n 查询老师留言情况请回复：学号_留言（如：3021_留言）\n查询老师留言记录（最近10次）情况请回复：学号_留言历史（如：3021_留言历史）\n\n 查询班级动态情况请回复：学号_动态（如：3021_动态）\n查询班级动态记录（最近10次）情况请回复：学号_动态历史（如：3021_动态历史）\n";


    public static String singleExamMarkToString(ExamMark em) {
        if (null == em || em.getExamId() == null) return null;
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    /**
     * 将回复消息对象转换成xml字符串
     *
     * @param reply 回复消息对象
     * @return 返回符合微信接口的xml字符串
     */
    public static String replyToXml(Reply reply) {
        String type = reply.getMsgType();
        if (TEXT.equals(type)) {
            xstream.omitField(Reply.class, "articles");
            xstream.omitField(Reply.class, "articleCount");
            xstream.omitField(Reply.class, "musicUrl");
            xstream.omitField(Reply.class, "hQMusicUrl");
        } else if (MUSIC.equals(type)) {
            xstream.omitField(Reply.class, "articles");
            xstream.omitField(Reply.class, "articleCount");
            xstream.omitField(Reply.class, "content");
        } else if (NEWS.equals(type)) {
            xstream.omitField(Reply.class, "content");
            xstream.omitField(Reply.class, "musicUrl");
            xstream.omitField(Reply.class, "hQMusicUrl");
        }
        xstream.autodetectAnnotations(true);
        xstream.alias("xml", reply.getClass());
        xstream.alias("item", new ReplyArticle().getClass());
        return xstream.toXML(reply);
    }

    /**
     * 存储数据的Map转换为对应的Message对象
     *
     * @param map 存储数据的map
     * @return 返回对应Message对象
     */
    public static Message mapToMessage(Map<String, String> map) {
        if (map == null) return null;
        String msgType = map.get("MsgType");
        Message message = new Message();
        message.setToUserName(map.get("ToUserName"));
        message.setFromUserName(map.get("FromUserName"));
        message.setCreatDate(new Date(Long.parseLong(map.get("CreateTime"))));
        message.setMsgType(msgType);
        message.setMsgId(map.get("MsgId"));
        if (msgType.equals(TEXT)) {
            message.setContent(map.get("Content"));
        } else if (msgType.equals(IMAGE)) {
            message.setPicUrl(map.get("PicUrl"));
        } else if (msgType.equals(LINK)) {
            message.setTitle(map.get("Title"));
            message.setDescription(map.get("Description"));
            message.setUrl(map.get("Url"));
        } else if (msgType.equals(LOCATION)) {
            message.setLocationX(map.get("Location_X"));
            message.setLocationY(map.get("Location_Y"));
            message.setScale(map.get("Scale"));
            message.setLabel(map.get("Label"));
        } else if (msgType.equals(EVENT)) {
            message.setEvent(map.get("Event"));
            message.setEventKey(map.get("EventKey"));
        }
        return message;
    }

    /**
     * 解析request中的xml 并将数据存储到一个Map中返回
     *
     * @param request
     */
    public static Map<String, String> parseXml(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            InputStream inputStream = request.getInputStream();
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            Element root = document.getRootElement();
            List<Element> elementList = root.elements();
            for (Element e : elementList)
                // 遍历xml将数据写入map
                map.put(e.getName(), e.getText());
            inputStream.close();
            inputStream = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * sha1加密算法
     *
     * @param key 需要加密的字符串
     * @return 加密后的结果
     */
    public static String sha1(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(key.getBytes());
            String pwd = new BigInteger(1, md.digest()).toString(16);
            return pwd;
        } catch (Exception e) {
            e.printStackTrace();
            return key;
        }
    }
}