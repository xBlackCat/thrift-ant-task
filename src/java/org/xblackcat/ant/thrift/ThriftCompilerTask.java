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
    private List<IGenerator> generators = new ArrayList<>();
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

    public DirSet createIncludes() {
        if (includes == null) {
            includes = new DirSet();
            includes.setProject(project);
        }

        return includes;
    }

    public FileSet createSource() {
        if (source == null) {
            source = new FileSet();
            source.setProject(project);
        }
        return source;
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

    public void addConfigured(IGenerator gen) {
        generators.add(gen);
    }

    public void addConfiguredAS3(AS3 gen) {
        addConfigured(gen);
    }

    public void addConfiguredCGlib(CGlib gen) {
        addConfigured(gen);
    }

    public void addConfiguredCocoa(Cocoa gen) {
        addConfigured(gen);
    }

    public void addConfiguredCpp(Cpp gen) {
        addConfigured(gen);
    }

    public void addConfiguredCSharp(CSharp gen) {
        addConfigured(gen);
    }

    public void addConfiguredD(D gen) {
        addConfigured(gen);
    }

    public void addConfiguredDelphi(Delphi gen) {
        addConfigured(gen);
    }

    public void addConfiguredErlang(Erlang gen) {
        addConfigured(gen);
    }

    public void addConfiguredGo(Go gen) {
        addConfigured(gen);
    }

    public void addConfiguredGraphviz(Graphviz gen) {
        addConfigured(gen);
    }

    public void addConfiguredHaskell(Haskell gen) {
        addConfigured(gen);
    }

    public void addConfiguredHTML(HTML gen) {
        addConfigured(gen);
    }

    public void addConfiguredJava(Java gen) {
        addConfigured(gen);
    }

    public void addConfiguredJavaME(JavaME gen) {
        addConfigured(gen);
    }

    public void addConfiguredJavascript(Javascript gen) {
        addConfigured(gen);
    }

    public void addConfiguredOCaml(OCaml gen) {
        addConfigured(gen);
    }

    public void addConfiguredPerl(Perl gen) {
        addConfigured(gen);
    }

    public void addConfiguredPHP(PHP gen) {
        addConfigured(gen);
    }

    public void addConfiguredPython(Python gen) {
        addConfigured(gen);
    }

    public void addConfiguredRuby(Ruby gen) {
        addConfigured(gen);
    }

    public void addConfiguredSmalltalk(Smalltalk gen) {
        addConfigured(gen);
    }

    public void addConfiguredXSD(XSD gen) {
        addConfigured(gen);
    }

    @Override
    public void execute() throws BuildException {
        if (generators.isEmpty()) {
            throw new BuildException("Cannot proceed without a generator");
        }

        if (source == null || source.size() == 0) {
            throw new BuildException("No files to process");
        }

        List<IGenerator> separateGen = new ArrayList<>();
        List<IGenerator> commonGen = new ArrayList<>();

        for (IGenerator g : generators) {
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

        runCompiler(cmd, outputDirBase, outputDir, commonGen.toArray(new IGenerator[commonGen.size()]));

        for (IGenerator g : separateGen) {
            runCompiler(cmd, null, g.getOutputDir(), g);
        }
    }

    private void runCompiler(Commandline base, String outputDirBase, String outputDir, IGenerator... generators) {
        Commandline compilerCmd = (Commandline) base.clone();

        if (outputDir != null) {
            compilerCmd.createArgument().setValue("-out" + resolveDir(outputDir));
        } else if (outputDirBase != null) {
            compilerCmd.createArgument().setValue("-o" + resolveDir(outputDirBase));
        }

        for (IGenerator g : generators) {
            compilerCmd.createArgument().setValue("--gen");
            compilerCmd.createArgument().setValue(g.getOptionsString());
        }

        try {

            for (Resource src : source) {
                Commandline cmd = (Commandline) compilerCmd.clone();

                File file = project.resolveFile(project.replaceProperties(src.getName()));
                if (!file.isFile()) {
                    throw new BuildException("Source is not a file: " + file.getAbsolutePath());
                }

                cmd.createArgument().setFile(file);

                Execute exe = new Execute(new LogStreamHandler(this, Project.MSG_INFO, Project.MSG_WARN));
                exe.setAntRun(project);
                exe.setWorkingDirectory(project.getBaseDir());
                exe.setCommandline(cmd.getCommandline());
                exe.execute();
            }
        } catch (IOException e) {
            throw new BuildException("Error running " + compilerCmd.getExecutable() + " compiler", e, getLocation());
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
