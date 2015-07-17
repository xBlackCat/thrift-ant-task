package org.xblackcat.ant.thrift;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 24.06.2014 12:09
 *
 * @author xBlackCat
 */ //Go
public class Go extends AGenerator {
    /**
     * Package prefix for generated files.
     */
    private String packagePrefix;
    /**
     * Override thrift package import path (default:git.apache.org/thrift.git/lib/go/thrift)
     */
    private String thriftImport;
    /**
     * Package name (default: inferred from thrift file name)
     */
    private String packageName;

    public Go() {
        super("go");
    }

    public void setPackagePrefix(String packagePrefix) {
        this.packagePrefix = packagePrefix;
    }

    public void setThriftImport(String thriftImport) {
        this.thriftImport = thriftImport;
    }

    public void setPackage(String packageName) {
        this.packageName = packageName;
    }

    @Override
    protected Collection<String> getOptions() {
        ArrayList<String> line = new ArrayList<>();
        if (packagePrefix != null) {
            line.add("package_prefix=" + packagePrefix);
        }
        if (thriftImport != null) {
            line.add("thrift_import=" + thriftImport);
        }
        if (packageName != null) {
            line.add("package=" + packageName);
        }
        return line;
    }
}
