package de.nrw.lav.dips.rs;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

public final class HostNameValidator implements IParameterValidator {
    public void validate(String name, String value)
        throws ParameterException {
        // Stupid domain name "lavnrw.local" ...
        DomainValidator.updateTLDOverride(
                DomainValidator.ArrayType.GENERIC_PLUS,
                new String[] {"local"});
        DomainValidator nameVal = DomainValidator.getInstance(true);
        InetAddressValidator ipVal = InetAddressValidator.getInstance();
        if (!nameVal.isValid(value) && !ipVal.isValid(value)) {
            throw new ParameterException("Invalid host name " + value);
        }
    }
}
