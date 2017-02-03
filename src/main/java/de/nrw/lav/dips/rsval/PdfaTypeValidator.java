package de.nrw.lav.dips.rsval;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import java.util.Arrays;

public final class PdfaTypeValidator implements IParameterValidator {
    public void validate(String name, String value)
        throws ParameterException {
        String[] types = {"1a", "1b", "2a", "2b", "2u", "3a", "3b", "3u"};
        if (!Arrays.asList(types).contains(value)) {
            throw new ParameterException(String.format(
                        "PDF/A type for validation must be one of %s.",
                        String.join(", ", types)));
        }
    }
}
