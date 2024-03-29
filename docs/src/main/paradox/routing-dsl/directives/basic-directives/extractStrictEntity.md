# extractStrictEntity

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #extractStrictEntity }

@@@

## Description

Extracts the strict http entity as `HttpEntity.Strict` from the @apidoc[RequestContext].

A timeout parameter is given and if the stream isn't completed after the timeout, the directive will be failed.

@@@ warning

The directive will read the request entity into memory within the size limit(8M by default) and effectively disable streaming.
The size limit can be configured globally with `pekko.http.parsing.max-content-length` or
overridden by wrapping with @ref[withSizeLimit](../misc-directives/withSizeLimit.md) or @ref[withoutSizeLimit](../misc-directives/withoutSizeLimit.md) directive.

@@@

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #extractStrictEntity-example }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #extractStrictEntity }
