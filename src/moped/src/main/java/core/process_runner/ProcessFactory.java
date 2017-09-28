package core.process_runner;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Factory for creating different types of external processes.
 *
 * Created by Virtuality.
 */
public class ProcessFactory {

    /**
     * Creates a new Python process.
     * @param pythonScriptPath Absolute or relative path to the python file.
     * @return The {@link ProcessRunner} object.
     */
    public static ProcessRunner createPythonProcess(String pythonScriptPath) throws FileNotFoundException {
        if (!new File(pythonScriptPath).exists()) {
            throw new FileNotFoundException("Path to python script is not valid!");
        }

        return new ProcessRunnerImpl(new ProcessBuilder(
                "python3", "-u", "-i", pythonScriptPath
        ));
    }

    /**
     * Creates a {@link ProcessRunner} from a {@link ProcessBuilder}
     * @param processBuilder ProcessBuilder to be used
     * @return The created {@link ProcessRunner}
     */
    public static ProcessRunner createCustomProcess(ProcessBuilder processBuilder) {
        return new ProcessRunnerImpl(processBuilder);
    }

}
