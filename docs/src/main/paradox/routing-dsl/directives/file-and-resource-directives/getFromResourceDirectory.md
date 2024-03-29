# getFromResourceDirectory

@@@ div { .group-scala }

## Signature

@@signature [FileAndResourceDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/FileAndResourceDirectives.scala) { #getFromResourceDirectory }

@@@

## Description

Completes `GET` requests with the content of the given classpath resource directory.

For details refer to @ref[getFromDirectory](getFromDirectory.md) which works the same way but obtaining the file from the filesystem
instead of the applications classpath.

Note that it's not required to wrap this directive with `get` as this directive will only respond to `GET` requests.

## Example

Scala
:  @@snip [FileAndResourceDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/FileAndResourceDirectivesExamplesSpec.scala) { #getFromResourceDirectory-examples }

Java
:  @@snip [FileAndResourceDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/FileAndResourceDirectivesExamplesTest.java) { #getFromResourceDirectory }
