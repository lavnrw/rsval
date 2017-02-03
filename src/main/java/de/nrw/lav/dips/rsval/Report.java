package de.nrw.lav.dips.rsval;

final class Report {
    private final String inputFile;
    private final Result result;
    private final String pdfaType;
    private final String details;

    enum Result { VALID, INVALID, ERROR }

    Report(String inputFile, Result result, String pdfaType, String details) {
        this.inputFile = inputFile;
        this.result    = result;
        this.pdfaType  = pdfaType;
        this.details   = details;
    }

    @Override
    public String toString() {
        return result + "\tPDF/A-" + pdfaType + "\t" + inputFile;
    }

    String toString(Boolean verbose) {
        String rest = (!verbose || details.isEmpty()) ? ""
            : System.getProperty("line.separator") + details;
        return toString() + rest;
    }
}
