# rsval

Command line client for the [SER Doxis4 Rendition Server][rs] PDF/A validation
features, based on the Rendition Server Java API.

## Installation

Prerequisites:

* Java Runtime Environment (JRE) version 8 or greater
* Rendition Server on the same host or reachable over the network

The [provided JAR file `rsval.jar`][releases] contains all dependencies and can
be executed directly, but the user experience may be improved by creating a
wrapper script like the example in [script/rsval][script/rsval].

[releases]: http://example.org/TODO

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
[lib/README.md](lib/README.md) for details).

Run the following commands to build a standalone executable JAR file:

    $ mvn clean
    $ mvn package

This will create `target/rsval-<version>-jar-with-dependencies.jar`. Copy,
rename, symlink, whatever this file to your heart's content!

[rs]: https://web.archive.org/web/20181122130019/http://www.ser-solutions.com/media-library/overview/medien/server-side-format-conversion-doxis4-rendition-server.html
