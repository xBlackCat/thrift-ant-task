package org.xblackcat.ant.thrift;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.taskdefs.LogStreamHandler;
import org.apache.tools.ant.types.Commandline;
import org.apache.tools.ant.types.DirSet;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 24.06.2014 10:03
 *
 * @author xBlackCat
 */
public class ThriftCompilerTask extends Task {
    private Project project;
    private final List<AGenerator> generators = new ArrayList<>();
    private DirSet includes;
    private FileSet source;
    private String outputDirBase;
    private String outputDir;
    private boolean noWarn;
    private boolean strict;
    private boolean verbose;
    private boolean recurse;
    private boolean debug;
    private boolean allowNegKeys;
    private boolean allow64bitConsts;

    private String executable;

    public void setProject(Project project) {
        this.project = project;
    }

    public void setIncludes(DirSet includes) {
        this.includes = includes;
    }

    public void setSource(FileSet source) {
        this.source = source;
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

    public void setRecurse(boolean recurse) {
        this.recurse = recurse;
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

    public void setExecutable(String executable) {
        this.executable = executable;
    }

    public void add(AGenerator gen) {
        generators.add(gen);
    }

    @Override
    public void execute() throws BuildException {
        if (generators.isEmpty()) {
            throw new BuildException("Cannot proceed without a generator");
        }

        if (source == null || source.size() == 0) {
            throw new BuildException("No files to process");
        }

        List<AGenerator> separateGen = new ArrayList<>();
        List<AGenerator> commonGen = new ArrayList<>();

        for (AGenerator g : generators) {
            if (g.generateSeparated()) {
                separateGen.add(g);
            } else {
                commonGen.add(g);
            }
        }

        if (outputDir != null && outputDirBase != null) {
            throw new BuildException("Only one of attributes outputDir or outputDirBase should be specified");
        }

        if (!commonGen.isEmpty() && outputDir == null && outputDirBase == null) {
            throw new BuildException("Specify an output directory for generated files");
        }

        Commandline cmd = new Commandline();
        String compiler;
        if (executable != null) {
            compiler = project.replaceProperties(executable);
        } else {
            compiler = "thrift";
        }
        cmd.setExecutable(compiler);

        runCompiler(cmd, outputDirBase, outputDir, commonGen.toArray(new AGenerator[commonGen.size()]));

        for (AGenerator g : separateGen) {
            runCompiler(cmd, g.getOutputDir(), null, g);
        }
    }

    private void runCompiler(Commandline cmd, String outputDirBase, String outputDir, AGenerator... generators) {
        if (includes != null && includes.size() > 0) {
            for (Resource dir : includes) {
                cmd.createArgument().setValue("-I" + dir.getName());
            }
        }

        if (noWarn) {
            cmd.createArgument().setValue("-nowarn");
        }
        if (strict) {
            cmd.createArgument().setValue("-strict");
        }
        if (verbose) {
            cmd.createArgument().setValue("-verbose");
        }
        if (recurse) {
            cmd.createArgument().setValue("-recurse");
        }
        if (debug) {
            cmd.createArgument().setValue("-debug");
        }
        if (allowNegKeys) {
            cmd.createArgument().setValue("--allow-neg-keys");
        }
        if (allow64bitConsts) {
            cmd.createArgument().setValue("--allow-64bit-consts");
        }

        if (outputDir != null) {
            cmd.createArgument().setValue("-out" + resolveDir(outputDir));
        } else if (outputDirBase != null) {
            cmd.createArgument().setValue("-o" + resolveDir(outputDirBase));
        }

        for (AGenerator g : generators) {
            cmd.createArgument().setValue("--gen");
            cmd.createArgument().setValue(g.getOptionsString());
        }

        Execute exe = new Execute(new LogStreamHandler(this, Project.MSG_INFO, Project.MSG_WARN));
        exe.setAntRun(project);
        exe.setWorkingDirectory(project.getBaseDir());
        exe.setCommandline(cmd.getCommandline());
        try {
            exe.execute();
        } catch (IOException e) {
            throw new BuildException("Error running " + cmd.getExecutable() + " compiler", e, getLocation());
        }
    }

    private String resolveDir(String outputDir) {
        File file = project.resolveFile(project.replaceProperties(outputDir));
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new BuildException("Can't create folder " + file.getAbsolutePath());
            }
        } else if (!file.isDirectory()) {
            throw new BuildException("Not a folder " + file.getAbsolutePath());
        }

        return file.getAbsolutePath();
    }
}
