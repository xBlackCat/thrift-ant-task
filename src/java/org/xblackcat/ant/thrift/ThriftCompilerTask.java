package org.xblackcat.ant.thrift;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.DirSet;

import java.util.ArrayList;
import java.util.List;

/**
 * 24.06.2014 10:03
 *
 * @author xBlackCat
 */
public class ThriftCompilerTask extends Task {
    private Project project;
    private final List<Generator> generators = new ArrayList<>();
    private DirSet includes;
    private String outputDirBase;
    private String outputDir;
    private boolean noWarn;
    private boolean strict;
    private boolean verbose;
    private boolean recursive;
    private boolean debug;
    private boolean allowNegKeys;
    private boolean allow64bitConsts;

    private String compilerPath;

    public void setProject(Project project) {
        this.project = project;
    }


    public void setIncludes(DirSet includes) {
        this.includes = includes;
    }

    public void setOutputDirBase(String outputDirBase) {
        this.outputDirBase = outputDirBase;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public void setNoWarn(boolean noWarn) {
        this.noWarn = noWarn;
    }

    public void setStrict(boolean strict) {
        this.strict = strict;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setAllowNegKeys(boolean allowNegKeys) {
        this.allowNegKeys = allowNegKeys;
    }

    public void setAllow64bitConsts(boolean allow64bitConsts) {
        this.allow64bitConsts = allow64bitConsts;
    }

    public void setCompilerPath(String compilerPath) {
        this.compilerPath = compilerPath;
    }

    public void add(Generator gen) {
        generators.add(gen);
    }

    @Override
    public void execute() throws BuildException {
        if (generators.isEmpty()) {
            throw new BuildException("Cannot proceed without a generator");
        }
    }
}
