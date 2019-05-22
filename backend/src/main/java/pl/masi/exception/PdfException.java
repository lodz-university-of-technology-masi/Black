package pl.masi.exception;

public class PdfException extends MasiException{


        public static final String ERROR_CREATING_DOCUMENT = "PDF can't be generate";

        public PdfException() {
            super();
        }

        public PdfException(String message) {
            super(message);
        }

        public PdfException(String message, Throwable cause) {
            super(message, cause);
        }

        public PdfException(Throwable cause) {
            super(cause);
        }
    }

