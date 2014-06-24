package org.xblackcat.ant.thrift;

import java.util.ArrayList;
import java.util.Collection;

/**
* 24.06.2014 12:09
*
* @author xBlackCat
*/ //C#
public class CSharp extends Generator {
    /**
     * Adds Async support using Task.Run.
     */
    private boolean async;
    /**
     * Adds Async CTP support using TaskEx.Run.
     */
    private boolean asyncctp;
    /**
     * Adds bindings for WCF to generated classes.
     */
    private boolean wcf;
    /**
     * Add serialization support to generated classes.
     */
    private boolean serial;
    /**
     * Use nullable types for properties.
     */
    private boolean nullable;
    /**
     * Generate a hashcode and equals implementation for classes.
     */
    private boolean hashcode;
    /**
     * Use new union typing, which includes a static read function for union types.
     */
    private boolean union;

    public CSharp() {
        super("csharp");
    }

    @Override
    protected Collection<String> getOptions() {
        ArrayList<String> line = new ArrayList<>();
        if (async) {
            line.add("async");
        }
        if (asyncctp) {
            line.add("asyncctp");
        }
        if (wcf) {
            line.add("wcf");
        }
        if (serial) {
            line.add("serial");
        }
        if (nullable) {
            line.add("nullable");
        }
        if (hashcode) {
            line.add("hashcode");
        }
        if (union) {
            line.add("union");
        }
        return line;
    }
}
