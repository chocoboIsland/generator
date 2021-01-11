package com.wangpj.generator;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;

public class ReadConfigXml {

    private String configXml;

    /**
     * @return 配置信息的集合
     * @throws Exception
     */
    public  Map<String,String> getConfig() throws Exception
    {
        Map<String,String> map=new HashMap<>();
        //创建SAXReader对象
        SAXReader reader = new SAXReader();
        //xml文件的位置
       // URL sources = ReadConfigXml.class.getClassLoader().getResource(configXml);
        File sources = new File(configXml);//参数为空
        //创建document对象,并读取xml文件 （解析xml文件）

        Document document = reader.read(sources);
        //读取元素   getRootElement() --》 获取父节点  elements() --> 所有节点
        List<Element> elements = document.getRootElement().elements();

        for (Element element : elements) {
           map.put(element.getName(),element.getTextTrim());
        }
        return map;
    }

    public String getConfigXml() {
        return configXml;
    }

    public void setConfigXml(String configXml) {
        this.configXml = configXml;
    }
}
