package org.xblackcat.ant.thrift;

import java.util.Collection;
import java.util.Collections;

/**
* 24.06.2014 12:10
*
* @author xBlackCat
*/ //HTML
public class HTML extends Generator {
    /**
     * Self-contained mode, includes all CSS in the HTML files. Generates no style.css file, but HTML files will be larger.
     */
    private boolean standalone;

    public HTML() {
        super("html");
    }

    public void setStandalone(boolean standalone) {
        this.standalone = standalone;
    }

    @Override
    protected Collection<String> getOptions() {
        if (standalone) {
            return Collections.singleton("standalone");
        }

        return null;
    }
}
