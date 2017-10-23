package core.process_runner;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Factory for creating different types of external processes.
 */
public class ProcessFactory {

    /**
     * Creates a new Python process.
     * @param pythonScriptPath Absolute or relative path to the python file.
     * @return The {@link ProcessRunner} object.
     */
    public static ProcessRunner createPythonProcess(String pythonScriptPath) throws FileNotFoundException {
        File script = new File(pythonScriptPath);
        if (!script.exists()) {
            throw new FileNotFoundException(script.getAbsolutePath() + " does not exist!");
        }

        return new ProcessRunnerImpl(new ProcessBuilder(
                "python", "-u", "-i", pythonScriptPath
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
