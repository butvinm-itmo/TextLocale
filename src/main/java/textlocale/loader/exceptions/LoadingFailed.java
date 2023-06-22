package textlocale.loader.exceptions;

import lombok.experimental.StandardException;

/**
 * Occurs when loading of files failed.
 *
 * @see textlocale.loader.FilesLoader
 */
@StandardException
public class LoadingFailed extends Exception {
}
