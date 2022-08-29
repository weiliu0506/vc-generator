package com.vc.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Jerry
 * @Date: 2020/10/21 17:35
 * @Description:
 */
public class MybatisGenerator {

    public static void main(String[] args){

        try{
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            String path = System.getProperty("user.dir").concat("/src/main/resources/generatorConfig.xml");
            File configFile = new File(path);
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            warnings.forEach(System.out::println);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
