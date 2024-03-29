# extractCredentials

@@@ div { .group-scala }

## Signature

@@signature [SecurityDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/SecurityDirectives.scala) { #extractCredentials }

@@@

## Description

Extracts the potentially present @apidoc[HttpCredentials] provided with the request's @apidoc[Authorization] header,
which can be then used to implement some custom authentication or authorization logic.

See @ref[Credentials and password timing attacks](index.md#credentials-and-timing-attacks) for details about verifying the secret.

## Example

Scala
:  @@snip [SecurityDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/SecurityDirectivesExamplesSpec.scala) { #extractCredentials0 }

Java
:  @@snip [SecurityDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/SecurityDirectivesExamplesTest.java) { #extractCredentials }
