# pathSingleSlash

@@@ div { .group-scala }

## Signature

@@signature [PathDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/PathDirectives.scala) { #pathSingleSlash }

@@@

## Description

Only passes the request to its inner route if the unmatched path of the @apidoc[RequestContext]
contains exactly one single slash.

This directive is a simple alias for `pathPrefix(PathEnd)` and is mostly used for matching requests to the root URI
(`/`) on an inner-level to discriminate "all path segments matched" from other alternatives (see the example below). For a comparison between path directives check @ref[Overview of path directives](index.md#overview-path).

## Example

Scala
:  @@snip [PathDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/PathDirectivesExamplesSpec.scala) { #pathSingleSlash- }

Java
:  @@snip [PathDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/PathDirectivesExamplesTest.java) { #path-single-slash }
