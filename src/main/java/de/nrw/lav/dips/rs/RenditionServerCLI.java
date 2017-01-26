package de.nrw.lav.dips.rs;

import com.ser.renditionserver.ConvertException;
import com.ser.renditionserver.Converter;
import com.ser.renditionserver.ValidationResult;
import java.io.File;

public final class RenditionServerCLI {
    private final CommandLineParams params;
    private final Converter converter;

    private RenditionServerCLI(String[] args) {
        params = new CommandLineParams(args);
        converter = createConverter();
    }

    private Converter createConverter() {
        Converter c = new Converter();
        c.setServerURL("http://" + params.server() + "/Dxr.Interfaces");
        c.setTimeoutCall(120);
        c.setTimeoutConnect(1);
        return c;
    }

    private Report validate(File f) {
        String opts = "<ValidateOptions><TargetPdfaType>" + params.pdfaType()
            + "</TargetPdfaType></ValidateOptions>";
        Report.Result result;
        String details;
        try {
            ValidationResult r = converter.validate(f, null, opts, "");
            if (r.getErrorCount() == 0) {
                result = Report.Result.VALID;
                details = "";
            }
            else {
                result = Report.Result.INVALID;
                details = converter.getJobValidateResult(r.getJobID());
            }
        }
        catch (ConvertException e) {
            result = Report.Result.ERROR;
            details = e.getMessage();
        }
        return new Report(f.toString(), result, params.pdfaType(), details);
    }

    public static void main(String[] args) {
        RenditionServerCLI cli = new RenditionServerCLI(args);
        for (File f : cli.params.inputFiles()) {
            Report r = cli.validate(f);
            String s = cli.params.verbose() ? r.toVerbose() : r.toString();
            System.out.println(s);
        }
    }
}
