package de.nrw.lav.dips.rsval;

import com.ser.renditionserver.ConvertException;
import com.ser.renditionserver.Converter;
import com.ser.renditionserver.ValidationResult;
import java.io.File;

public final class ValidationCLI {
    private final Configuration config;
    private final Converter converter;

    private ValidationCLI(String[] args) {
        config = new Configuration(args);
        converter = createConverter();
    }

    private Converter createConverter() {
        Converter c = new Converter();
        c.setServerURL("http://" + config.server() + "/Dxr.Interfaces");
        c.setTimeoutCall(120);
        c.setTimeoutConnect(1);
        return c;
    }

    private Report validate(File f) {
        String opts = "<ValidateOptions><TargetPdfaType>" + config.pdfaType()
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
        return new Report(f.toString(), result, config.pdfaType(), details);
    }

    public static void main(String[] args) {
        ValidationCLI cli = new ValidationCLI(args);
        for (File f : cli.config.inputFiles()) {
            Report r = cli.validate(f);
            System.out.println(r.toString(cli.config.verbose()));
        }
    }
}
