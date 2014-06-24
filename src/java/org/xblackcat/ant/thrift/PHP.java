package org.xblackcat.ant.thrift;

import java.util.ArrayList;
import java.util.Collection;

/**
* 24.06.2014 12:10
*
* @author xBlackCat
*/ //PHP
public class PHP extends Generator {
    /**
     * Generate PHP inlined files
     */
    private boolean inlined;
    /**
     * Generate PHP server stubs
     */
    private boolean server;
    /**
     * Generate PHP with object oriented subclasses
     */
    private boolean oop;
    /**
     * Generate PHP REST processors
     */
    private boolean rest;

    public PHP() {
        super("php");
    }

    public void setInlined(boolean inlined) {
        this.inlined = inlined;
    }

    public void setServer(boolean server) {
        this.server = server;
    }

    public void setOop(boolean oop) {
        this.oop = oop;
    }

    public void setRest(boolean rest) {
        this.rest = rest;
    }

    @Override
    protected Collection<String> getOptions() {
        ArrayList<String> line = new ArrayList<>();
        if (inlined) {
            line.add("inlined");
        }
        if (server) {
            line.add("server");
        }
        if (oop) {
            line.add("oop");
        }
        if (rest) {
            line.add("rest");
        }
        return line;
    }
}
