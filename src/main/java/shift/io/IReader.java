package shift.io;

import java.io.IOException;

public interface IReader extends AutoCloseable {
    String readLine() throws IOException;
}
