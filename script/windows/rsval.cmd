:: This is an example wrapper script for the rsval tool running on Windows.
:: Adapt it to your environment and needs and put it somewhere in your PATH.

:: This line runs the rsval tool, threading through any command line parameters
:: that were given to this wrapper script (in the %* variable). If you didn't
:: save your JAR files (usually, rsval.jar and dxrapi.jar) in the specified
:: directory `C:\bin\rs` you have to adapt the path.
@java -cp C:\bin\rs\* de.nrw.lav.dips.rsval.ValidationCLI %*
