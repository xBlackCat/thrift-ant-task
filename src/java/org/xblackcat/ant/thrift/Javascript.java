package org.xblackcat.ant.thrift;

import java.util.ArrayList;
import java.util.Collection;

/**
* 24.06.2014 12:10
*
* @author xBlackCat
*/ //Javascript
public class Javascript extends AGenerator {
    /**
     * Generate jQuery compatible code.
     */
    private boolean jquery;
    /**
     * Generate node.js compatible code.
     */
    private boolean node;

    public Javascript() {
        super("js");
    }

    public void setJquery(boolean jquery) {
        this.jquery = jquery;
    }

    public void setNode(boolean node) {
        this.node = node;
    }

    @Override
    protected Collection<String> getOptions() {
        ArrayList<String> line = new ArrayList<>();

        if (jquery) {
            line.add("jquery");
        }
        if (node) {
            line.add("node");
        }

        return line;
    }
}
