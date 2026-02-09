package shift.io;

import java.io.IOException;

public interface IWriter<T> extends AutoCloseable {
    void write(T t) throws IOException;

}
