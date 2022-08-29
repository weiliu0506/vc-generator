package com.vc.generator.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * @Auther: Jerry
 * @Date: 2020/10/22 19:55
 * @Description: 加入分页sql
 */
public class MySqlLimitPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    /**
     * 生成mapping.xml 添加自定义sql
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
    	addSelectCountXml(document, introspectedTable);        
    	addSelectByPageXml(document, introspectedTable);        
    	addSelectByLastIdXml(document, introspectedTable);        
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    //添加count sql
    private void addSelectCountXml(Document document, IntrospectedTable introspectedTable){
        XmlElement selectCountXmlElement = new XmlElement("select");
        selectCountXmlElement.addAttribute(new Attribute("id", "selectCount"));
        selectCountXmlElement.addAttribute(new Attribute("resultType", "java.lang.Integer"));

        selectCountXmlElement.addElement(new TextElement("select COUNT(*) FROM " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));
        
        document.getRootElement().addElement(selectCountXmlElement);
    }

    //添加 byPage sql
    private void addSelectByPageXml(Document document, IntrospectedTable introspectedTable){
        XmlElement selectCountXmlElement = new XmlElement("select");
        selectCountXmlElement.addAttribute(new Attribute("id", "selectByPage"));
        selectCountXmlElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        selectCountXmlElement.addElement(new TextElement("select * FROM " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));
        selectCountXmlElement.addElement(new TextElement("ORDER BY id DESC"));
        selectCountXmlElement.addElement(new TextElement("limit ${(page-1)*limit},#{limit}"));
        
        document.getRootElement().addElement(selectCountXmlElement);
    }
    
    //添加 byLastId sql
    private void addSelectByLastIdXml(Document document, IntrospectedTable introspectedTable){
        XmlElement selectCountXmlElement = new XmlElement("select");
        selectCountXmlElement.addAttribute(new Attribute("id", "selectByLastId"));
        selectCountXmlElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        selectCountXmlElement.addElement(new TextElement("select * FROM " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));
        selectCountXmlElement.addElement(new TextElement("where id > #{lastId}"));
        selectCountXmlElement.addElement(new TextElement("ORDER BY id DESC"));
        selectCountXmlElement.addElement(new TextElement("limit #{limit}"));
        
        document.getRootElement().addElement(selectCountXmlElement);
    }

    /**
     * 生成Java 添加自定义方法
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        addSelectCountMethod(interfaze);
        addSelectByPagetMethod(interfaze, introspectedTable);
        addSelectByLastIdMethod(interfaze, introspectedTable);
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }
    
    //count 方法
    private void addSelectCountMethod(Interface interfaze){
        // 方法的返回值
        FullyQualifiedJavaType returnTypeInt = FullyQualifiedJavaType.getIntInstance();

        Method selectCountMethod = new Method();
        // 1.设置方法可见性
        selectCountMethod.setVisibility(JavaVisibility.PUBLIC);
        // 2.设置返回值类型 Long类型
        selectCountMethod.setReturnType(returnTypeInt);
        // 3.设置方法名
        selectCountMethod.setName("selectCount");
        interfaze.addMethod(selectCountMethod);
    }

    //byPage 方法
    private void addSelectByPagetMethod(Interface interfaze, IntrospectedTable introspectedTable){
    	interfaze.addImportedType(new FullyQualifiedJavaType("java.util.List"));
    	interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
    	
        // 方法的返回值
        FullyQualifiedJavaType returnTypeList = FullyQualifiedJavaType.getNewListInstance();
        returnTypeList.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        
        Method selectByPageMethod = new Method();
        // 1.设置方法可见性
        selectByPageMethod.setVisibility(JavaVisibility.PUBLIC);
        // 2.设置返回值类型 
        selectByPageMethod.setReturnType(returnTypeList);
        // 3.设置方法名
        selectByPageMethod.setName("selectByPage");
        // 4.设置参数列表
        selectByPageMethod.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "page", "@Param(\"page\")"));
        selectByPageMethod.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "limit", "@Param(\"limit\")"));
        interfaze.addMethod(selectByPageMethod);
    }
    
    //byLastId 方法
    private void addSelectByLastIdMethod(Interface interfaze, IntrospectedTable introspectedTable){
    	interfaze.addImportedType(new FullyQualifiedJavaType("java.util.List"));
    	interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
    	
        // 方法的返回值
        FullyQualifiedJavaType returnTypeList = FullyQualifiedJavaType.getNewListInstance();
        returnTypeList.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        Method selectByPageMethod = new Method();
        // 1.设置方法可见性
        selectByPageMethod.setVisibility(JavaVisibility.PUBLIC);
        // 2.设置返回值类型 
        selectByPageMethod.setReturnType(returnTypeList);
        // 3.设置方法名
        selectByPageMethod.setName("selectByLastId");
        // 4.设置参数列表
        selectByPageMethod.addParameter(new Parameter(PrimitiveTypeWrapper.getIntInstance(), "lastId", "@Param(\"lastId\")"));
        selectByPageMethod.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "limit", "@Param(\"limit\")"));
        interfaze.addMethod(selectByPageMethod);
    }
}
