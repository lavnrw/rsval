# rsval

Command line client for the [SER Doxis4 Rendition Server][rs] PDF/A validation
features, based on the Rendition Server Java API.

[rs]: https://web.archive.org/web/20181122130019/http://www.ser-solutions.com/media-library/overview/medien/server-side-format-conversion-doxis4-rendition-server.html

[Deutschsprachige Anleitung | German User Guide](HOWTO.de.md)

## Installation

Prerequisites:

* Java Runtime Environment (JRE) version 8 or greater.
* A SER Doxis4 Rendition Server on the same host or network.
* Due to licensing issues you need *two* JAR files:
  * A JAR file containing the Rendition Server Java API (usually called
    something like `dxrapi.jar`). Since this is a proprietary library owned by
    [SER][ser] it cannot be distributed together with this repository. Please
    find it in your Rendition Server installation directory or ask the good
    people at SER for a copy of the JAR file.
  * A second JAR file containing the rest of the application (usually called
    `rsval.jar`). [Get it here!][releases]

The actual installation depends on your environment. Save both JAR files in a
directory that suites your taste (e.g., `/opt/rs` on Linux or `C:\bin\rs` on
Windows). Then create a wrapper script to make the actual `rsval` command
available; examples for Linux and Windows can be found in the [script](script)
directory.

[ser]: https://www.sergroup.com/en/
[releases]: https://github.com/lavnrw/rsval/releases

## Usage

Show the available options:

~~~console
$ rsval --help
Usage: rsval [options] input files
  Options:
    -h, --help
      show this help message
      Default: false
    -s, --server
      name of the host running the rendition server
      Default: localhost
    --timeout
      timeout in seconds, increase in case of errors
      Default: 120
    -t, --type
      PDF/A type (1a, 1b, 2a, 2b, 2u, 3a, 3b, 3u)
      Default: 1b
    -v, --verbose
      print more validation details
      Default: false
    --version
      show version info
      Default: false
~~~

Validate `a.pdf` as PDF/A-2b:

~~~console
$ rsval --server rs.example.org --type 2b a.pdf
VALID   PDF/A-2b    a.pdf
~~~

Validate multiple files (and/or directories, recursively):

~~~console
$ rsval -s rs.example.org -t 2b a.pdf b.pdf
VALID   PDF/A-2b    a.pdf
INVALID PDF/A-2b    b.pdf
~~~

Display verbose information about invalid files:

~~~console
$ rsval -s rs.example.org -t 2b --verbose a.pdf
INVALID PDF/A-2b    a.pdf
... (lots of XML)
~~~

## Building from source

To build this tool the proprietary Rendition Server Java API is required (see
the Installation section above). Get the JAR file and make it available in your
development environment. For example, if you use Maven something like this
should install it into your local repository (check your version number!):

~~~console
$ mvn install:install-file -Dfile=dxrapi.jar -DgroupId=com.ser.renditionserver -DartifactId=dxrapi -Dversion=12.0.0.387 -Dpackaging=jar -DgeneratePom=true
~~~

Then run the following commands to build the `rsval` JAR file:

~~~console
$ mvn clean
$ mvn package
~~~

This will create `target/rsval-<version>-jar-with-dependencies.jar` which
contains all dependencies except for the proprietary Rendition Server API (which
has to be distributed separately, see the Installation section above). To run
the tool *both* JARs are required.
