package org.xblackcat.ant.thrift;

import java.util.ArrayList;
import java.util.Collection;

/**
* 24.06.2014 12:09
*
* @author xBlackCat
*/ //delphi
public class Delphi extends Generator {
    /**
     * Use AnsiString for binary datatype (default is TBytes).
     */
    private boolean ansiStrBinary;
    /**
     * Enable TypeRegistry, allows for creation of struct, union and container instances by interface or TypeInfo()
     */
    private boolean registerTypes;

    public Delphi() {
        super("delphi");
    }

    public void setAnsiStrBinary(boolean ansiStrBinary) {
        this.ansiStrBinary = ansiStrBinary;
    }

    public void setRegisterTypes(boolean registerTypes) {
        this.registerTypes = registerTypes;
    }

    @Override
    protected Collection<String> getOptions() {
        ArrayList<String> line = new ArrayList<>();
        if (ansiStrBinary) {
            line.add("ansistr_binary");
        }
        if (registerTypes) {
            line.add("register_types");
        }
        return line;
    }
}
