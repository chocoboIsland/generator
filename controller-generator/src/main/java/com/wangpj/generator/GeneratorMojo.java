package com.wangpj.generator;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
@Mojo(name="generatorMojo" ,defaultPhase=LifecyclePhase.PACKAGE)
public class GeneratorMojo extends AbstractMojo {

    @Parameter
    private String generatorConfigDir;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException{

        GeneratorVm generatorVm;
        if (generatorConfigDir !=null && generatorConfigDir.trim().length()!=0)
        {
            generatorVm =new GeneratorVm(generatorConfigDir);
        }else
        {
           throw new IllegalStateException("没有配置文件generatorConfigDir");
        }
        try {
            generatorVm.createVm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

