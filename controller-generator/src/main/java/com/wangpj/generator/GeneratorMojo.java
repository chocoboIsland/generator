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
    private String configDir;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException{

        ControllerVm controllerVm;
        if (configDir !=null && configDir.trim().length()!=0)
        {
            controllerVm=new ControllerVm(configDir);
        }else
        {
            controllerVm=new ControllerVm();
        }
        try {
            controllerVm.createVm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

