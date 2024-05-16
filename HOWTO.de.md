# rsval - Rendition Server Command Line PDF/A Validator

Das Tool rsval ist ein Kommandozeilenclient für die PDF/A-Validierungsfunktionen
des [Doxis4 Rendition Server][rs] der Firma [SER][ser].

Der Rendition Server ist für die Integration in größere Softwaresysteme und
nicht als Standalone-Anwendung gedacht, dementsprechend dürftig sind seine
Schnittstellen für menschliche Benutzerinnen und Benutzer ausgestattet. Für die
Validierung bzw. Konvertierung einzelner Dateien wird eine kleine GUI
mitgeliefert, die aber primär für Tests der Installation gedacht ist; die
Bearbeitung mehrerer Dateien oder Verzeichnisse im Batchverfahren ist nicht
vorgesehen. Diese Lücke füllen die Tools [rsval (Rendition Server
Validation)][rsval] zur Validierung von PDF/A und [rscon (Rendition Server
Conversion)][rscon] zur Konvertierung nach PDF/A.

[ser]: https://www.sergroup.com/
[rs]: https://web.archive.org/web/20181122130019/http://www.ser-solutions.com/media-library/overview/medien/server-side-format-conversion-doxis4-rendition-server.html
[rsval]: http://example.org/TODO
[rscon]: http://example.org/TODO

## Installation

Siehe [README.md](README.md).

## Benutzung

Die folgenden Beispiele gehen davon aus, dass der Rendition Server unter dem
Hostnamen `rs.example.org` erreichbar ist. In der Praxis muss das der eigenen
Umgebung angepasst werden.

Anzeige der verfügbaren Programmoptionen:

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

Validierung der Datei `a.pdf` als PDF/A-2b:

~~~console
$ rsval --server rs.example.org --type 2b a.pdf
VALID   PDF/A-2b    a.pdf
~~~

Statt eines einzelnen Dateinamens kann auch eine Liste von Dateien oder
Verzeichnissen (schließt Unterverzeichnisse mit ein) angegeben werden:

~~~console
$ rsval -s rs.example.org -t 2b a.pdf b.pdf
VALID   PDF/A-2b    a.pdf
INVALID PDF/A-2b    b.pdf
~~~

Anzeige ausführlicher Informationen für invalide Dateien:

~~~console
$ rsval -s rs.example.org -t 2b --verbose a.pdf
INVALID PDF/A-2b    a.pdf
... (ganz viel XML)
~~~

Die meisten Optionen können abgekürzt werden und haben zudem Default-Werte,
wodurch sie ggf. weggelassen werden können. Details dazu zeigt `rsval --help`.
