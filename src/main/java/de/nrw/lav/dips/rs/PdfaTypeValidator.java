package de.nrw.lav.dips.rs;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import java.util.Arrays;

public final class PdfaTypeValidator implements IParameterValidator {
    public void validate(String name, String value)
        throws ParameterException {
        String[] types = {"1a", "1b", "2a", "2b", "2u", "3a", "3b", "3u"};
        if (!Arrays.asList(types).contains(value)) {
            throw new ParameterException("PDF/A type must be one of " +
                    String.join(", ", types) + ".");
        }
    }
}