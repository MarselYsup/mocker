package ru.itis.mocker.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "greet")
public class MyMavenPlugin extends AbstractMojo {

    @Parameter(property = "greet.name", defaultValue = "World")
    private String name;

    public void execute() throws MojoExecutionException {
        getLog().info("Hello, " + name + "!");
    }
}