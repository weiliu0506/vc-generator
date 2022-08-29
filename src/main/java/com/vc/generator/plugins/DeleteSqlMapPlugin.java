package com.vc.generator.plugins;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.io.File;
import java.util.List;

/**
 * @Auther: Jerry
 * @Date: 2020/10/22 19:50
 * @Description: 删除旧的SqlMapper
 */
public class DeleteSqlMapPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }


    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        String sqlMapPath = sqlMap.getTargetProject() +
                File.separator +
                sqlMap.getTargetPackage().replaceAll("\\.", File.separator) +
                File.separator + sqlMap.getFileName();
        File sqlMapFile = new File(sqlMapPath);
        if(sqlMapFile.exists()) {//如果mapper.xml存在， 删除旧的mapper.xml, 避免追加内容
        	System.out.println(sqlMapPath + " exists delete!");
            sqlMapFile.delete();
        }
        return super.sqlMapGenerated(sqlMap, introspectedTable);
    }
}
