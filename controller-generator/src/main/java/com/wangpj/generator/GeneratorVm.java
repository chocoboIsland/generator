package com.wangpj.generator;

import com.wangpj.generator.util.FieldUtil;
import com.wangpj.generator.util.FileUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeneratorVm {

    private ReadConfigXml readConfigXml;

    public GeneratorVm(String config) {
        this.readConfigXml = new ReadConfigXml();
        readConfigXml.setConfigXml(config);
    }
    public void createVm() throws Exception
    {
        Map<String,String> configMap=readConfigXml.getConfig();
        String basePackage=configMap.get("basePackage");//基础包*/
        String allDomainName=configMap.get("domainName");
        List<String> domainNamesList=Arrays.asList(allDomainName.split(","));
        domainNamesList=domainNamesList.stream().filter(s -> s!=null&&s.length()!=0).collect(Collectors.toList());
        //String projectName=configMap.get("projectName");
        String outputDir=configMap.get("outputDir");
        String dtoSubPackage=configMap.get("dtoSubPackage");
        String daoSubPackage=configMap.get("daoSubPackage");


        String controllerPackage=basePackage+".controller";//controller包名
        String servicePackage=basePackage+".api.service";//service接口包名
        String serviceImplPackage=basePackage+".service.impl";//service实现类名
        String daoPackage=basePackage+"."+daoSubPackage;//dao包名
        String dtoPackage=basePackage+".api."+dtoSubPackage;//dto报名



        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        ve.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        ve.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        ve.init();
        // 获取模板文件
        Template template;
        List<String> vmList= Arrays.asList("service.vm","serviceImpl.vm","controller.vm",
                "dao.vm","mapper.vm");
        // 设置变量
        VelocityContext ctx = new VelocityContext();
        ctx.put("controllerPackage", controllerPackage);
        ctx.put("servicePackage", servicePackage);
        ctx.put("serviceImplPackage",serviceImplPackage);
        ctx.put("daoPackage", daoPackage);
        ctx.put("dtoPackage", dtoPackage);

        for (String domainName:domainNamesList)
        {
            System.out.println(domainName);
            String domainNameUp=domainName.substring(0,1).toUpperCase()+domainName.substring(1);//首字母大写的domainName
            String domainNameLower=domainName.substring(0,1).toLowerCase()+domainName.substring(1);//首字母小写的domainName

            String controllerClassName=domainNameUp+"Controller";
            String serviceClassName=domainNameUp+"Service";
            String controllerObjectName=domainNameLower+"Controller";
            String serviceObjectName=domainNameLower+"Service";
            String daoClassName=domainNameUp+"Mapper";
            String mapperFileName=domainNameUp+"Mappper";


            ctx.put("controllerClassName",controllerClassName);
            ctx.put("serviceClassName",serviceClassName);
            ctx.put("serviceObjectName",serviceObjectName);
            ctx.put("daoClassName",domainNameUp+"Mapper");
            ctx.put("daoObjectName",domainNameLower+"Mapper");
            ctx.put("dtoClassName",domainNameUp);
            ctx.put("dtoObjectName",domainNameLower);
            ctx.put("domain",domainName);
            ctx.put("domainLower",domainNameLower);
            ctx.put("tableName",FieldUtil.change2DatabaseTableName(domainName));

            for (String vmItem:
                    vmList) {
                template=ve.getTemplate(vmItem);
                template.setEncoding("utf-8");
                StringWriter sw=new StringWriter();
                template.merge(ctx,sw);
                String dir="";
                String className="";
                if (vmItem.equals("controller.vm"))
                {
                    dir=outputDir+"\\controller";
                    className=controllerClassName+".java";
                }else if (vmItem.equals("service.vm"))
                {
                    dir=outputDir+"\\service";
                    className=serviceClassName+".java";
                }else if (vmItem.equals("serviceImpl.vm"))
                {
                    dir=outputDir+"\\service\\impl";
                    className=serviceClassName+"Impl"+".java";
                }else if (vmItem.equals("dao.vm"))
                {
                    dir=outputDir+"\\dao";
                    className=daoClassName+".java";
                }else if (vmItem.equals("mapper.vm"))
                {
                    dir=outputDir+"\\mapper";
                    className=daoClassName+".xml";
                }
                OutputGeneratorUtil.createFile(dir,className,sw.toString());
            }
        }
    }
}
