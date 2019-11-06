package com.wangpj.generator;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ControllerVm {

    private ReadConfigXml readConfigXml;

    public ControllerVm(String config) {
        this();
        readConfigXml.setConfigXml(config);
    }

    public ControllerVm() {
        this.readConfigXml = new ReadConfigXml();
    }

    public void createVm() throws Exception
    {
        Map<String,String> configMap=readConfigXml.getConfig();
        String basePackage=configMap.get("basePackage");
        String allDomainName=configMap.get("domainName");
        List<String> domainNamesList=Arrays.asList(allDomainName.split(","));
        String projectName=configMap.get("projectName");
        String outputDir=configMap.get("outputDir");

        String controllerPackage=basePackage+".controller";
        String servicePackage=basePackage+".service";
        String daoPackage=basePackage+".dao";
        String dtoPackage=basePackage+".dto";



        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        // 获取模板文件
        Template template;
        List<String> vmList= Arrays.asList("service.vm","serviceImpl.vm","controller.vm");
        // 设置变量
        VelocityContext ctx = new VelocityContext();
        ctx.put("controllerPackage", controllerPackage);
        ctx.put("servicePackage", servicePackage);
        ctx.put("daoPackage", daoPackage);
        ctx.put("dtoPackage", dtoPackage);

        for (String domainName:domainNamesList)
        {
            String domainUp=domainName.substring(0,1).toUpperCase()+domainName.substring(1);
            String projectNameUp=projectName.substring(0,1).toUpperCase()
                    +projectName.substring(1);

            String controllerClassName=projectNameUp+domainUp+"Controller";
            String serviceClassName=projectNameUp+domainUp+"Service";

            ctx.put("controllerClass",controllerClassName);
            ctx.put("serviceClass",serviceClassName);
            ctx.put("serviceObject",projectName+domainUp+"Service");
            ctx.put("daoClass",projectNameUp+domainUp+"Mapper");
            ctx.put("daoObject",projectName+domainUp+"Mapper");
            ctx.put("dtoClass",projectNameUp+domainUp+"Dto");
            ctx.put("dtoObject",projectName+domainUp+"Dto");
            ctx.put("domain",domainName);

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
                    className=controllerClassName;
                }else if (vmItem.equals("service.vm"))
                {
                    dir=outputDir+"\\service";
                    className=serviceClassName;
                }else if (vmItem.equals("serviceImpl.vm"))
                {
                    dir=outputDir+"\\service\\impl";
                    className=serviceClassName+"Impl";
                }
                OutputGeneratorUtil.createFile(dir,className,sw.toString());
            }
        }
    }
}
