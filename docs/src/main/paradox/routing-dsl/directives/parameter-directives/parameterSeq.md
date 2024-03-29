# @scala[parameterSeq]@java[parameterList]

@@@ div { .group-scala }

## Signature

@@signature [ParameterDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/ParameterDirectives.scala) { #parameterSeq }

@@@

## Description

Extracts all parameters at once in the original order as (name, value) tuples of type @scala[`(String, String)`]@java[`Map.Entry<String, String>`].

This directive can be used if the exact order of parameters is important or if parameters can occur several times.

See @ref[When to use which parameter directive?](index.md#which-parameter-directive) to understand when to use which directive.

## Example

Scala
:  @@snip [ParameterDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/ParameterDirectivesExamplesSpec.scala) { #parameterSeq }

Java
:  @@snip [ParameterDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/ParameterDirectivesExamplesTest.java) { #parameterSeq }
