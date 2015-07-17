package org.xblackcat.ant.thrift;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.taskdefs.LogStreamHandler;
import org.apache.tools.ant.types.DirSet;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Resource;
import org.apache.tools.ant.types.resources.FileResource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 24.06.2014 10:03
 *
 * @author xBlackCat
 */
public class ThriftCompilerTask extends Task {
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

    public DirSet createIncludes() {
        if (includes == null) {
            includes = new DirSet();
            includes.setProject(getProject());
        }

        return includes;
    }

    public FileSet createSource() {
        if (source == null) {
            source = new FileSet();
            source.setProject(getProject());
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

    public void add(IGenerator gen) {
        generators.add(gen);
    }

    public void addAS3(AS3 gen) {
        add(gen);
    }

    public void addCGlib(CGlib gen) {
        add(gen);
    }

    public void addCocoa(Cocoa gen) {
        add(gen);
    }

    public void addCpp(Cpp gen) {
        add(gen);
    }

    public void addCSharp(CSharp gen) {
        add(gen);
    }

    public void addD(D gen) {
        add(gen);
    }

    public void addDelphi(Delphi gen) {
        add(gen);
    }

    public void addErlang(Erlang gen) {
        add(gen);
    }

    public void addGo(Go gen) {
        add(gen);
    }

    public void addGraphviz(Graphviz gen) {
        add(gen);
    }

    public void addHaskell(Haskell gen) {
        add(gen);
    }

    public void addHTML(HTML gen) {
        add(gen);
    }

    public void addJava(Java gen) {
        add(gen);
    }

    public void addJavaME(JavaME gen) {
        add(gen);
    }

    public void addJavascript(Javascript gen) {
        add(gen);
    }

    public void addJSON(JSON gen) {
        add(gen);
    }

    public void addLUA(LUA gen) {
        add(gen);
    }

    public void addOCaml(OCaml gen) {
        add(gen);
    }

    public void addPerl(Perl gen) {
        add(gen);
    }

    public void addPHP(PHP gen) {
        add(gen);
    }

    public void addPython(Python gen) {
        add(gen);
    }

    public void addRuby(Ruby gen) {
        add(gen);
    }

    public void addSmalltalk(Smalltalk gen) {
        add(gen);
    }

    public void addXSD(XSD gen) {
        add(gen);
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

        log("Examine source files", Project.MSG_VERBOSE);

        List<String> sourceFiles = new ArrayList<>();

        for (Resource src : source) {
            if (src instanceof FileResource) {
                File file = ((FileResource) src).getFile();
                if (!file.isFile()) {
                    throw new BuildException("Source is not a file: " + file.getAbsolutePath());
                }

                sourceFiles.add(file.getAbsolutePath());
            }
        }

        List<String> cmdLine = new ArrayList<>();
        String compiler;
        if (executable != null) {
            compiler = getProject().replaceProperties(executable);
        } else {
            compiler = "thrift";
        }
        cmdLine.add(compiler);
        if (includes != null && includes.size() > 0) {
            for (Resource dir : includes) {
                if (dir instanceof FileResource) {
                    cmdLine.add("-I");
                    cmdLine.add(((FileResource) dir).getFile().getAbsolutePath());
                }
            }
        }

        if (noWarn) {
            cmdLine.add("-nowarn");
        }
        if (strict) {
            cmdLine.add("-strict");
        }
        if (verbose) {
            cmdLine.add("-verbose");
        }
        if (recurse) {
            cmdLine.add("-recurse");
        }
        if (debug) {
            cmdLine.add("-debug");
        }
        if (allowNegKeys) {
            cmdLine.add("--allow-neg-keys");
        }
        if (allow64bitConsts) {
            cmdLine.add("--allow-64bit-consts");
        }

        if (!commonGen.isEmpty()) {
            runCompiler(cmdLine, sourceFiles, outputDirBase, outputDir, commonGen.toArray(new IGenerator[commonGen.size()]));
        }

        for (IGenerator g : separateGen) {
            runCompiler(cmdLine, sourceFiles, null, g.getOutputDir(), g);
        }
    }

    private void runCompiler(List<String> base, List<String> sourceFiles, String outDirBase, String outDir, IGenerator... generators) {
        log("Prepare compiler", Project.MSG_VERBOSE);

        List<String> compilerCmdLine = new ArrayList<>(base);

        if (outDir != null) {
            compilerCmdLine.add("-out");
            compilerCmdLine.add(resolveDir(outDir));
        } else if (outDirBase != null) {
            compilerCmdLine.add("-o");
            compilerCmdLine.add(resolveDir(outDirBase));
        } else {
            throw new NullPointerException("Output folder is not set");
        }

        StringBuilder log = new StringBuilder();

        for (IGenerator g : generators) {
            compilerCmdLine.add("--gen");
            compilerCmdLine.add(g.getOptionsString());
            log.append(", ");
            log.append(g.getName());
        }

        compilerCmdLine.add("file");

        String[] commandline = compilerCmdLine.toArray(new String[compilerCmdLine.size()]);
        try {
            for (String src : sourceFiles) {
                commandline[commandline.length - 1] = src;

                log("Translate file " + src + " to " + log.substring(2), Project.MSG_INFO);

                log("Execute command line: " + Arrays.toString(commandline), Project.MSG_VERBOSE);

                Execute exe = new Execute(new LogStreamHandler(this, Project.MSG_INFO, Project.MSG_WARN));
                exe.setAntRun(getProject());
                exe.setWorkingDirectory(getProject().getBaseDir());
                exe.setCommandline(commandline);
                exe.setVMLauncher(false);
                int code = exe.execute();

                log("Process returns code " + code, Project.MSG_VERBOSE);
                if (code != 0) {
                    throw new BuildException("Thrift compiler failed to process file " + src);
                }
            }
        } catch (BuildException e) {
            throw e;
        } catch (IOException e) {
            throw new BuildException("Error running " + commandline[0] + " compiler", e, getLocation());
        } catch (Throwable e) {
            throw new BuildException("Unexpected exception", e);
        }
    }

    private String resolveDir(String outputDir) {
        File file = getProject().resolveFile(getProject().replaceProperties(outputDir));
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
