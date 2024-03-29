# Unsupported HTTP method: PRI

```
Illegal request, responding with status '501 Not Implemented': Unsupported HTTP method: PRI
```

This indicates that an HTTP/2 request was received, but the server was not
correctly set up to handle those. You may have to:

* Make sure the @ref[`pekko.http.server.preview.enable-http2` option](../server-side/http2.md#enable-http-2-support) is enabled
* Make sure you are running @ref[at least JDK version 8u252](../server-side/http2.md)
* Make sure you are not using @apidoc[Http().bindAndHandle()](Http$) or @apidoc[Http().newServerAt().bindFlow()](ServerBuilder), but @apidoc[Http().newServerAt().bind()](ServerBuilder).
