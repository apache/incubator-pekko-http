# requestEncodedWith

@@@ div { .group-scala }

## Signature

@@signature [CodingDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/CodingDirectives.scala) { #requestEncodedWith }

@@@

## Description

Passes the request to the inner route if the request is encoded with the argument encoding. Otherwise, rejects the request with an `UnacceptedRequestEncodingRejection(encoding)`.

This directive is the building block for @ref:[decodeRequest](decodeRequest.md) to reject unsupported encodings.
