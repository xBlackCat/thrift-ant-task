package org.xblackcat.ant.thrift;

import java.util.ArrayList;
import java.util.Collection;

/**
* 24.06.2014 12:09
*
* @author xBlackCat
*/ //Cocoa
public class Cocoa extends Generator {
    /**
     * Log every time an unexpected field ID or type is encountered.
     */
    private boolean logUnexpected;
    /**
     * Throws exception if any required field is not set.
     */
    private boolean validateRequired;

    public Cocoa() {
        super("cocoa");
    }

    public void setLogUnexpected(boolean logUnexpected) {
        this.logUnexpected = logUnexpected;
    }

    public void setValidateRequired(boolean validateRequired) {
        this.validateRequired = validateRequired;
    }

    @Override
    protected Collection<String> getOptions() {
        ArrayList<String> list = new ArrayList<>();

        if (logUnexpected) {
            list.add("log_unexpected");
        }
        if (validateRequired) {
            list.add("validate_required");
        }

        return list;
    }
}
