package org.xblackcat.ant.thrift;

import java.util.ArrayList;
import java.util.Collection;

/**
* 24.06.2014 12:09
*
* @author xBlackCat
*/ //C++
public class Cpp extends Generator {
    /**
     * Generate "Continuation OBject"-style classes.
     */
    private boolean cobStyle;
    /**
     * Omit calls to completion__() in CobClient class.
     */
    private boolean noClientCompletion;
    /**
     * Generate templatized reader/writer methods.
     */
    private boolean templates;
    /**
     * Generate pure enums instead of wrapper classes.
     */
    private boolean pureEnums;
    /**
     * Generate type specifications for the dense protocol.
     */
    private boolean dense;
    /**
     * Use full include paths in generated files.
     */
    private boolean includePrefix;

    public Cpp() {
        super("cpp");
    }

    public void setCobStyle(boolean cobStyle) {
        this.cobStyle = cobStyle;
    }

    public void setNoClientCompletion(boolean noClientCompletion) {
        this.noClientCompletion = noClientCompletion;
    }

    public void setTemplates(boolean templates) {
        this.templates = templates;
    }

    public void setPureEnums(boolean pureEnums) {
        this.pureEnums = pureEnums;
    }

    public void setDense(boolean dense) {
        this.dense = dense;
    }

    public void setIncludePrefix(boolean includePrefix) {
        this.includePrefix = includePrefix;
    }

    @Override
    protected Collection<String> getOptions() {
        Collection<String> line = new ArrayList<>();
        if (cobStyle) {
            line.add("cob_style");
        }
        if (noClientCompletion) {
            line.add("no_client_completion");
        }
        if (templates) {
            line.add("templates");
        }
        if (pureEnums) {
            line.add("pure_enums");
        }
        if (dense) {
            line.add("dense");
        }
        if (includePrefix) {
            line.add("include_prefix");
        }
        return line;
    }
}
