package org.xblackcat.ant.thrift;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectComponent;

import java.util.Collection;
import java.util.Iterator;

/**
 * 24.06.2014 10:24
 *
 * @author xBlackCat
 */
public abstract class AGenerator extends ProjectComponent implements IGenerator {
    private String outputDir;
    private String generator;

    protected AGenerator(String generator) {
        this.generator = generator;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    @Override
    public String getOutputDir() {
        return outputDir;
    }

    @Override
    public String getOptionsString() {
        getProject().log("Build options line for " + generator, Project.MSG_VERBOSE);

        StringBuilder line = new StringBuilder();
        line.append(generator);

        Collection<String> options = getOptions();
        if (options != null && !options.isEmpty()) {
            line.append(':');
            Iterator<String> it = options.iterator();
            line.append(it.next());
            while (it.hasNext()) {
                line.append(',');
                line.append(it.next());
            }
        }

        return line.toString();
    }

    protected Collection<String> getOptions() {
        return null;
    }

    @Override
    public boolean generateSeparated() {
        return outputDir != null;
    }
}
