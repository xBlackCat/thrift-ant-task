package org.xblackcat.ant.thrift;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * 24.06.2014 10:24
 *
 * @author xBlackCat
 */
public abstract class Generator {
    private String outputDir;
    private String generator;

    protected Generator(String generator) {
        this.generator = generator;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public String getOptionsString() {
        StringBuilder line = new StringBuilder("-");
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

}
