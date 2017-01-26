package de.nrw.lav.dips.rs;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

final class CommandLineParams {
    private final static String PROG_NAME = "rscli";
    private final static String PROG_VERSION = "0.2.0-SNAPSHOT";
    @Parameter(names = {"-h", "--help"}, description = "show help message",
            help = true)
    private Boolean showHelp = false;
    @Parameter(names = "--version", description = "show version info")
    private Boolean showVersion = false;
    @Parameter(names = {"-s", "--server"},
            description = "name of the host running the rendition server",
            validateWith = HostNameValidator.class)
    private String server = "localhost";
    @Parameter(names = {"-t", "--type"},
            description = "PDF/A type (1a, 1b, 2a, 2b, 2u, 3a, 3b, 3u)",
            validateWith = PdfaTypeValidator.class)
    private String pdfaType = "1b";
    @Parameter(names = {"-v", "--verbose"},
            description = "print more validation details")
    private Boolean verbose = false;
    // @Parameter(names = {"-o", "--output"}, description = "output files")
    // private String outputFiles;
    @Parameter(description = "input-file ...")
    private Collection<String> inputFilePaths = new ArrayList<String>();
    private Collection<File> inputFiles;
    @Parameter(names = "--carter", hidden = true,
            description = "use Carter mode (no parameter validation)")
    private Boolean carterMode = false;

    CommandLineParams(String[] args) {
        JCommander jc = new JCommander(this);
        jc.setProgramName(PROG_NAME);
        try {
            if (Arrays.asList(args).contains("--carter")) {
                carterMode = true;
                jc.parseWithoutValidation(args);
            }
            else {
                jc.parse(args);
            }
        }
        catch (ParameterException e) {
            System.err.println(e.getMessage());
            System.err.println("Use --help for usage info.");
            System.exit(1);
        }
        if (showHelp || args.length == 0) {
            jc.usage();
            System.exit(0);
        }
        if (showVersion) {
            System.out.println(PROG_VERSION);
            System.exit(0);
        }
        inputFiles = createFiles(inputFilePaths);
    }

    private Collection<File> createFiles(Collection<String> paths) {
        Collection<File> fs = new ArrayList<File>();
        for (String p : paths) {
            File f = new File(p);
            if (!(f.exists() && f.canRead())) {
                if (carterMode) {
                    System.err.println("Cannot read file, skip " + p);
                    continue;
                }
                else {
                    System.err.println("Cannot read file " + p);
                    System.exit(1);
                }
            }
            if (f.isDirectory()) {
                fs.addAll(FileUtils.listFiles(f,
                            new String[] {"pdf", "pdfa"}, true));
            }
            else {
                fs.add(f);
            }
        }
        return fs;
    }

    String server() {
        return server;
    }

    String pdfaType() {
        return pdfaType;
    }

    Boolean verbose() {
        return verbose;
    }

    File[] inputFiles() {
        return inputFiles.toArray(new File[0]);
    }
}
