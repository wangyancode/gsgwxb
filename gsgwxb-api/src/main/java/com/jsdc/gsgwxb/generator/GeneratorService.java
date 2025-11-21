package com.jsdc.gsgwxb.generator;


import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneratorService {

    private static final List<String> templates = new ArrayList<String>() {
        {
            add("vm/controller.java.vm");
            add("vm/service.java.vm");
            add("vm/mapper.java.vm");
            add("vm/dao.java.vm");
            add("vm/vo.java.vm");
        }
    };


    public void generator(Class<?>...classes) throws IOException {
        VelocityInitializer.initVelocity();
        for (Class<?> clazz : classes) {
            String[] names = clazz.getName().split("\\.");
            String ClassName = names[names.length - 1];
            String[] packages = clazz.getPackage().getName().split("\\.");
            String packageName = packages[packages.length - 1];
            String tableName = clazz.getAnnotation(TableName.class).value();

            List<String> fieldNames = Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
            String column = String.join(", ", fieldNames);


            VelocityContext context = new VelocityContext();
            context.put("package" , packageName);
            context.put("ClassName" , ClassName);
            context.put("className" , ClassName.substring(0, 1).toLowerCase() + ClassName.substring(1));
            context.put("tableName" , tableName);
            context.put("column" , column);

            for (String template : templates) {
                StringWriter sw = new StringWriter();
                Template tpl = Velocity.getTemplate(template, "UTF-8");
                tpl.merge(context, sw);
                String path = generatorPath(packageName, template, ClassName);
                FileUtils.writeStringToFile(new File(path), sw.toString(), "UTF-8");
            }
        }
    }

    private String generatorPath(String packageName, String template, String ClassName) {
        String projectPath = System.getProperty("user.dir");
        projectPath = projectPath.replaceAll("szywpt-web", "");
        String javaPath = "src\\main\\java\\com\\jsdc\\szywpt";
        if (template.contains("controller.java")) {
            return projectPath + "szywpt-web\\" + javaPath + "\\controller\\" + packageName + "\\" + ClassName + "Controller.java";
        }
        if (template.contains("service.java")) {
            return projectPath + "szywpt-api\\" + javaPath + "\\service\\" + packageName + "\\" + ClassName + "Service.java";
        }
        if (template.contains("mapper.java")) {
            return projectPath + "szywpt-api\\" + javaPath + "\\mapper\\" + packageName + "\\" + ClassName + "Mapper.java";
        }
        if (template.contains("dao.java")) {
            return projectPath + "szywpt-api\\" + javaPath + "\\dao\\" + packageName + "\\" + ClassName + "Dao.java";
        }
        if (template.contains("vo.java")) {
            return projectPath + "szywpt-model\\" + javaPath + "\\vo\\" + packageName + "\\" + ClassName + "Vo.java";
        }
        return "";
    }

}
