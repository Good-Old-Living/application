package meru.application.designer.builder;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ModelErrorHandler implements ErrorHandler {

    private Exception mException;

    public Exception getException() {
        return mException;
    }

    public void error(SAXParseException exception) throws SAXException {

        mException = exception;
    }

    public void fatalError(SAXParseException exception) throws SAXException {
        mException = exception;
    }

    public void warning(SAXParseException exception) throws SAXException {
        mException = exception;
    }
}