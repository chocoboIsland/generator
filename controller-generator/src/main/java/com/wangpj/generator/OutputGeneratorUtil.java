package com.wangpj.generator;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 创建文件
 */
public class OutputGeneratorUtil {

    public static void createFile(String outputDir,String fileName,String text) throws Exception
    {
        File dir=new File(outputDir);
        if (!dir.exists())
        {
            dir.mkdirs();
        }

        File file=new File(outputDir+"\\"+fileName);
        if(!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream out=new FileOutputStream(file,true);
        out.write(text.getBytes("utf-8"));
        out.close();
    }
}
