package com.jsdc.gsgwxb.utils;//package com.jsdc.tzjc.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.codehaus.jackson.map.DeserializationConfig;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.map.SerializationConfig;
//import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
//import org.codehaus.jackson.type.JavaType;
//import org.codehaus.jackson.type.TypeReference;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by jack
// */
//@Slf4j
//public class JsonUtil {
//
//    private static ObjectMapper objectMapper = new ObjectMapper();
//    static{
//        //对象的所有字段全部列入
//        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);
//
//        //取消默认转换timestamps形式
//        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);
//
//        //忽略空Bean转json的错误
//        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
//
//        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss`
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//
//        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
//        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
//    }
//
//
//    /**
//     * Object 转 String
//     * @param obj
//     * @param <T>
//     * @return
//     */
//    public static <T> String obj2String(T obj){
//        if(obj == null){
//            return null;
//        }
//        try {
//            return obj instanceof String ? (String)obj :  objectMapper.writeValueAsString(obj);
//        } catch (Exception e) {
//            log.warn("Parse Object to String error",e);
//            return null;
//        }
//    }
//
//    /**
//     * 格式化 (美化)
//     * @param obj
//     * @param <T>
//     * @return
//     */
//    public static <T> String obj2StringPretty(T obj){
//        if(obj == null){
//            return null;
//        }
//        try {
//            return obj instanceof String ? (String)obj :  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
//        } catch (Exception e) {
//            log.warn("Parse Object to String error",e);
//            return null;
//        }
//    }
//
//    /**
//     * String 转 Object
//     * @param str
//     * @param clazz
//     * @param <T>
//     * @return
//     */
//    public static <T> T string2Obj(String str,Class<T> clazz){
//        if(StringUtils.isEmpty(str) || clazz == null){
//            return null;
//        }
//
//        try {
//            return clazz.equals(String.class)? (T)str : objectMapper.readValue(str,clazz);
//        } catch (Exception e) {
//            log.warn("Parse String to Object error",e);
//            return null;
//        }
//    }
//
//    /**
//     * 完善 String 转 List<Bean> Map<String,Bean> 等复杂的类型数据
//     * @param str
//     * @param typeReference
//     * @param <T>
//     * @return
//     */
//    public static <T> T string2Obj(String str, TypeReference<T> typeReference){
//        if(StringUtils.isEmpty(str) || typeReference == null){
//            return null;
//        }
//        try {
//            return (T)(typeReference.getType().equals(String.class)? str : objectMapper.readValue(str,typeReference));
//        } catch (Exception e) {
//            log.warn("Parse String to Object error",e);
//            return null;
//        }
//    }
//
//    /**
//     * String 转 Object :collectionClass表示集合的类型，elementClasses表示元素的类型
//     * @param str
//     * @param collectionClass
//     * @param elementClasses
//     * @param <T>
//     * @return
//     */
//    public static <T> T string2Obj(String str,Class<?> collectionClass,Class<?>... elementClasses){
//        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
//        try {
//            return objectMapper.readValue(str,javaType);
//        } catch (Exception e) {
//            log.warn("Parse String to Object error",e);
//            return null;
//        }
//    }
//
//    public static void main(String[] args) {
//
//        // 1、Bean转String
////        TestPojo testPojo = new TestPojo();
////        testPojo.setName("jack");
////        testPojo.setId(666);
////        String ts = JsonUtil.obj2String(testPojo);
////        log.info("1、Bean转String:{}",ts);
////
////        String userJsonPretty = JsonUtil.obj2StringPretty(testPojo);
////        log.info("1、Bean转String（美化）:{}",userJsonPretty);
////
////        // 2、String转Bean
////        TestPojo testPojo1 = JsonUtil.string2Obj(userJsonPretty,TestPojo.class);
////        log.info("2、String转Bean：{}",testPojo1);
////
////        // 3、Map转String
////        Map<String,Object> map = new HashMap<>();
////        Map<String,Object> map1 = new HashMap<>();
////        map.put("name","jack");
////        map.put("id","1");
////        map1.put("fang",map);
////        String ms = JsonUtil.obj2String(map1);
////        log.info("3、Map转String：{}",ms);
////
////        // 4、String转Map
////        Map<String,Object> map2 = JsonUtil.string2Obj(ms,Map.class);
////        log.info("4、String转Map：{}",map2);
////
////        // 5、List<Bean>转String
////        List<TestPojo> userList = new ArrayList<>();
////        userList.add(testPojo);
////        userList.add(testPojo1);
////        String userListStr = JsonUtil.obj2String(userList);
////        log.info("5、List<Bean>转String:{}",userListStr);
////
////        // 6、String转List<Bean>(1)
////        List<TestPojo> userListObj1 = JsonUtil.string2Obj(userListStr, new TypeReference<List<TestPojo>>() {
////        });
////        log.info("6、String转List<Bean>(1):{}",userListObj1);
////
////        // 6、String转List<Bean>(2)
////        List<TestPojo> userListObj2 = JsonUtil.string2Obj(userListStr,List.class,TestPojo.class);
////        log.info("6、String转List<Bean>(2):{}",userListObj2);
//
//    }
//
//}
