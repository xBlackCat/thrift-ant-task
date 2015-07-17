package org.xblackcat.ant.thrift;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 24.06.2014 12:10
 *
 * @author xBlackCat
 */ //Ruby
public class Ruby extends AGenerator {
    /**
     * Add a "require 'rubygems'" line to the top of each generated file.
     */
    private boolean rubygems;
    /**
     * Generate files in idiomatic namespaced directories.
     */
    private boolean namespaced;

    public Ruby() {
        super("rb");
    }

    public void setRubygems(boolean rubygems) {
        this.rubygems = rubygems;
    }

    public void setNamespaced(boolean namespaced) {
        this.namespaced = namespaced;
    }

    @Override
    protected Collection<String> getOptions() {
        ArrayList<String> line = new ArrayList<>();
        if (rubygems) {
            line.add("rubygems");
        }
        if (namespaced) {
            line.add("namespaced");
        }

        return line;
    }
}
