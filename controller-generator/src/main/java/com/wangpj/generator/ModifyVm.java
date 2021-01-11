package com.wangpj.generator;

import com.wangpj.generator.util.FileUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ModifyVm extends AbstractVm{


    protected ModifyVm(String configDir) {
        super(configDir);
    }

    @Override
    public void createVm() {
//        try {
//            modifyMapperXml();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 修改mapper.xml文件
     * @throws IOException
     */
    public void modifyMapperXml() throws IOException {
        String mapperXmlFile=configMap.get("mapperXmlDir");
        String txt = FileUtil.file2String(mapperXmlFile);



    }
}
