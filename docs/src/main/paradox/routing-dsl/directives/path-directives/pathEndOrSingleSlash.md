# pathEndOrSingleSlash

@@@ div { .group-scala }

## Signature

@@signature [PathDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/PathDirectives.scala) { #pathEndOrSingleSlash }

@@@

## Description

Only passes the request to its inner route if the unmatched path of the @apidoc[RequestContext] is either empty
or contains only one single slash.

This directive is a simple alias for `rawPathPrefix(Slash.? ~ PathEnd)` and is mostly used on an inner-level to
discriminate "path already fully matched" from other alternatives (see the example below). For a comparison between path directives check @ref[Overview of path directives](index.md#overview-path).

It is equivalent to `pathEnd | pathSingleSlash` but slightly more efficient.

## Example

Scala
:  @@snip [PathDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/PathDirectivesExamplesSpec.scala) { #pathEndOrSingleSlash- }

Java
:  @@snip [PathDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/PathDirectivesExamplesTest.java) { #path-end-or-single-slash }
