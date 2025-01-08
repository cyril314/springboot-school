package com.fit.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @AUTO 自定义实体复制赋值工具类
 * @FILE BeanUtils.java
 * @DATE 2018-3-7 下午10:09:52
 * @Author AIM
 */
public abstract class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * 对象不为空
     *
     * @param object  判断对象
     * @param message 异常信息
     */
    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 复制提交内容到原有实体内
     *
     * @param source 新的对象
     * @param target 原有对象
     */
    public static void copyProperties(Object source, Object target) throws BeansException {
        notNull(source, "Source must not be null");
        notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
                        if (!ObjectUtils.isEmpty(value)) {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy properties from source to target", ex);
                    }
                }
            }
        }
    }

    /**
     * 去掉bean中所有属性为字符串的前后空格
     */
    public static void beanAttributeValueTrim(Object bean) {
        if (bean != null) {
            try {
                // 获取所有的字段包括public,private,protected,private
                Field[] fields = bean.getClass().getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Field f = fields[i];
                    if (f.getType().getName().equals("java.lang.String")) {
                        String key = f.getName();// 获取字段名
                        Object value = getFieldValue(bean, key);
                        if (value == null) continue;
                        // 设置到字段
                        setFieldValue(bean, key, value.toString().trim());
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }

    /**
     * 利用反射通过get方法获取bean中字段fieldName的值
     *
     * @param bean      对象实体
     * @param fieldName 字段名
     */
    private static Object getFieldValue(Object bean, String fieldName) throws Exception {
        StringBuffer result = new StringBuffer();
        String methodName = result.append("get").append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1)).toString();

        Object rObject = null;
        Method method = null;

        Class<?>[] classArr = new Class[0];
        method = bean.getClass().getMethod(methodName, classArr);
        rObject = method.invoke(bean, new Object[0]);

        return rObject;
    }

    /**
     * 利用发射调用bean.set方法将value设置到字段
     *
     * @param bean      对象实体
     * @param fieldName 字段名
     * @param value     字段值
     */
    private static void setFieldValue(Object bean, String fieldName, Object value) throws Exception {
        StringBuffer result = new StringBuffer();
        String methodName = result.append("set").append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1)).toString();

        /**
         * 利用发射调用bean.set方法将value设置到字段
         */
        Class<?>[] classArr = new Class[1];
        classArr[0] = "java.lang.String".getClass();
        Method method = bean.getClass().getMethod(methodName, classArr);
        method.invoke(bean, value);
    }

    /**
     * 比较两个实体属性
     *
     * @param source    新的对象
     * @param target    原有对象
     * @param ignoreArr 忽略的字段
     */
    public static Map<String, List<Object>> compareFields(Object source, Object target, String[] ignoreArr) {
        try {
            Map<String, List<Object>> map = new HashMap<String, List<Object>>();
            List<String> ignoreList = null;
            if (ignoreArr != null && ignoreArr.length > 0) {
                // array转化为list
                ignoreList = Arrays.asList(ignoreArr);
            }
            if (source.getClass() == target.getClass()) {// 只有两个对象都是同一类型的才有可比性
                Class clazz = source.getClass();
                // 获取object的属性描述
                PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors();
                for (PropertyDescriptor pd : pds) {// 这里就是所有的属性了
                    String name = pd.getName();// 属性名
                    // 如果当前属性选择忽略比较，跳到下一次循环
                    if ("serialVersionUID".equals(name) && ignoreList != null && ignoreList.contains(name)) {
                        continue;
                    }
                    Method readMethod = pd.getReadMethod();// get方法
                    // 在source上调用get方法等同于获得obj1的属性值
                    Object o1 = readMethod.invoke(source);
                    // 在target上调用get方法等同于获得obj2的属性值
                    Object o2 = readMethod.invoke(target);
                    if (o1 instanceof Timestamp) {
                        o1 = new Date(((Timestamp) o1).getTime());
                    }
                    if (o2 instanceof Timestamp) {
                        o2 = new Date(((Timestamp) o2).getTime());
                    }
                    if (o1 == null && o2 == null) {
                        continue;
                    } else if (o1 == null && o2 != null) {
                        List<Object> list = new ArrayList<Object>();
                        list.add(o1);
                        list.add(o2);
                        map.put(name, list);
                        continue;
                    }
                    if (!o1.equals(o2)) {// 比较这两个值是否相等,不等就可以放入map了
                        List<Object> list = new ArrayList<Object>();
                        list.add(o1);
                        list.add(o2);
                        map.put(name, list);
                    }
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Map集合对象转化成 JavaBean集合对象
     *
     * @param javaBean JavaBean实例对象
     * @param mapList  Map数据集对象
     */
    public static <T> List<T> map2List(Class<T> javaBean, List<Map<String, Object>> mapList) {
        if (mapList == null || mapList.isEmpty()) {
            return null;
        }
        List<T> objectList = new ArrayList<T>();

        T object = null;
        for (Map<String, Object> map : mapList) {
            if (map != null) {
                object = map2Bean(javaBean, map);
                objectList.add(object);
            }
        }

        return objectList;
    }

    /**
     * Map对象转化成 JavaBean对象
     *
     * @param clz JavaBean实例对象
     * @param map Map对象
     */
    public static <T> T map2Bean(Class<T> clz, Map<?, ?> map) {
        T obj = null;
        try {
            obj = clz.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(clz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    if (null != value) {
                        String v = value.toString();// 值
                        // property对应的setter方法
                        Method setter = property.getWriteMethod();
                        Class<?>[] clazz = setter.getParameterTypes();
                        String type = clazz[0].getName();
                        if ("java.lang.Byte".equals(type) || "byte".equals(type)) {
                            setter.invoke(obj, Byte.parseByte(v));
                        } else if ("java.lang.Boolean".equals(type) || "Boolean".equals(type)) {
                            setter.invoke(obj, Boolean.parseBoolean(v));
                        } else if ("java.lang.Short".equals(type) || "short".equals(type)) {
                            setter.invoke(obj, Short.parseShort(v));
                        } else if ("java.lang.Integer".equals(type) || "int".equals(type)) {
                            setter.invoke(obj, Integer.parseInt(v));
                        } else if ("java.lang.Long".equals(type) || "long".equals(type)) {
                            setter.invoke(obj, Long.parseLong(v));
                        } else if ("java.lang.Float".equals(type) || "float".equals(type)) {
                            setter.invoke(obj, Float.parseFloat(v));
                        } else if ("java.lang.Double".equals(type) || "double".equals(type)) {
                            setter.invoke(obj, Double.parseDouble(v));
                        } else if ("java.lang.String".equals(type)) {
                            setter.invoke(obj, v);
                        } else if ("java.lang.Character".equals(type) || "char".equals(type)) {
                            setter.invoke(obj, (Character) value);
                        } else if ("java.util.Date".equals(type)) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            setter.invoke(obj, sdf.parse(v));
                        } else if ("java.lang.Date".equals(type)) {
                            setter.invoke(obj, new Date(((java.sql.Date) value).getTime()));
                        } else if ("java.lang.Timer".equals(type)) {
                            setter.invoke(obj, new Time(((Time) value).getTime()));
                        } else if ("java.sql.Timestamp".equals(type)) {
                            setter.invoke(obj, (Timestamp) value);
                        } else {
                            setter.invoke(obj, value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 任意类型转换成Map
     */
    public static Map<String, Object> obj2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if (field.get(obj) != null) {
                    map.put(field.getName(), field.get(obj));
                } else {
                    map.put(field.getName(), "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将 List<JavaBean>对象转化为List<Map>
     */
    public static List<Map<String, Object>> listBean2ListMap(List<?> list) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        try {
            for (Object tempObj : list) {
                mapList.add(obj2Map(tempObj));
            }
        } catch (Exception e) {
            throw new RuntimeException("List<JavaBean>对象转化为List<Map>异常");
        }
        return mapList;
    }
}