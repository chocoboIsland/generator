package com.wangpj.generator;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * 用于完成对mybatis-generator生成的文件进行修改
 */
@Mojo(name="ModifyMojo" ,defaultPhase=LifecyclePhase.PACKAGE)
public class ModifyMojo extends AbstractMojo {

    @Parameter
    private String modifyConfigDir;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        ModifyVm modifyVm;
        if (modifyConfigDir !=null && modifyConfigDir.trim().length()!=0)
        {
            modifyVm =new ModifyVm(modifyConfigDir);
        }else
        {
            throw new IllegalStateException("modifyConfigDir");
        }
        try {
            modifyVm.createVm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
