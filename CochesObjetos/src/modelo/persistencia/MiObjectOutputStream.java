package modelo.persistencia;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

class MiObjectOutputStream extends ObjectOutputStream {
    public MiObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        // No hacer nada para evitar que se escriba una nueva cabecera
    }
}