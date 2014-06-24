package org.xblackcat.ant.thrift;

import java.util.Collection;
import java.util.Collections;

/**
* 24.06.2014 12:10
*
* @author xBlackCat
*/ //Ruby
public class Ruby extends Generator {
    /**
     * Add a "require 'rubygems'" line to the top of each generated file.
     */
    private boolean rubygems;

    public Ruby() {
        super("rb");
    }

    public void setRubygems(boolean rubygems) {
        this.rubygems = rubygems;
    }

    @Override
    protected Collection<String> getOptions() {
        if (rubygems) {
            return Collections.singleton("rubygems");
        }

        return null;
    }
}
