package com.wangpj.generator;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractVm {
    protected Map<String,String> configMap=new HashMap<>();

    protected AbstractVm(String configDir)  {
        ReadConfigXml readConfigXml = new ReadConfigXml();
        readConfigXml.setConfigXml(configDir);
        try {
            this.configMap=readConfigXml.getConfig();
        } catch (Exception e) {
            throw new IllegalStateException("解析配置文件失败",e);
        }
    }

    public abstract void createVm();
}
