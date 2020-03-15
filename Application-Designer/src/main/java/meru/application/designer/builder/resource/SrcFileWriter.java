package meru.application.designer.builder.resource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import meru.sys.JVM;

public class SrcFileWriter {
    private String mIndentStr = "  ";
    private short mIndent;
    private Writer mWriter;

    public SrcFileWriter(File file) throws IOException {

        this.mWriter = new FileWriter(file);
    }

    public SrcFileWriter(File file,
                         String indent) throws IOException {

        this.mIndentStr = indent;
        this.mWriter = new FileWriter(file);
    }

    public SrcFileWriter(Writer writer) throws IOException {

        this.mWriter = writer;
    }

    public SrcFileWriter indent() {

        this.mIndent = (short) (this.mIndent + 2);
        return this;
    }

    public SrcFileWriter outdent() {

        if (this.mIndent > 0) {
            this.mIndent = (short) (this.mIndent - 2);
            return this;
        }

        throw new IllegalStateException("Indent : " + this.mIndent);
    }

    public SrcFileWriter writeln(String str) throws IOException {

        this.mWriter.write(str);
        this.mWriter.write(JVM.SystemProperty.NEW_LINE);
        return this;
    }

    public SrcFileWriter write(String str) throws IOException {

        this.mWriter.write(str);
        return this;
    }

    public SrcFileWriter write(String[] strs) throws IOException {

        for (String str : strs) {
            this.mWriter.write(str);
        }

        return this;
    }

    public SrcFileWriter writeln(String[] strs) throws IOException {

        write(strs).write(new String[] { JVM.SystemProperty.NEW_LINE });
        return this;
    }

    public SrcFileWriter writeln() throws IOException {

        write(new String[] { JVM.SystemProperty.NEW_LINE });
        return this;
    }

    public SrcFileWriter writeIndent() throws IOException {

        for (int i = 0; i < this.mIndent; ++i) {
            this.mWriter.write(this.mIndentStr);
        }

        return this;
    }

    public void close() throws IOException {

        this.mWriter.flush();
        this.mWriter.close();
    }
}