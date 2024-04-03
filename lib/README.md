This directory should contain the Doxis4 Rendition Server Java API in a file
called `dxrapi.jar`. This file is required to build the rsval tool. If it is
missing this will lead to errors like this:

~~~console
$ mvn package
...
[ERROR] The specified file 'lib/dxrapi.jar' does not exist
...
~~~

However, since the Rendition Server API is a proprietary library owned by
[SER](https://www.sergroup.com/en/) it cannot be distributed together with this
repository. Please ask the good people at SER for a copy of the jar file if you
wish to build the rsval tool yourself.
