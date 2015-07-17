package org.xblackcat.ant.thrift;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 24.06.2014 12:09
 *
 * @author xBlackCat
 */ //Erlang
public class Erlang extends AGenerator {
    public Erlang() {
        super("erl");
    }

    /**
     * Output files retain naming conventions of Thrift 0.9.1 and earlier.
     */
    private boolean legacynames;

    public void setLegacynames(boolean legacynames) {
        this.legacynames = legacynames;
    }

    @Override
    protected Collection<String> getOptions() {
        ArrayList<String> line = new ArrayList<>();
        if (legacynames) {
            line.add("legacynames");
        }
        return line;
    }
}
