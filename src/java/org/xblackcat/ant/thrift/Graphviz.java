package org.xblackcat.ant.thrift;

import java.util.Collection;
import java.util.Collections;

/**
* 24.06.2014 12:10
*
* @author xBlackCat
*/ //Graphviz
public class Graphviz extends AGenerator {
    /**
     * Whether to draw arrows from functions to exception.
     */
    private boolean exceptions;

    public Graphviz() {
        super("gv");
    }

    public void setExceptions(boolean exceptions) {
        this.exceptions = exceptions;
    }

    @Override
    protected Collection<String> getOptions() {
        if (exceptions) {
            return Collections.singleton("exceptions");
        }

        return null;
    }
}
