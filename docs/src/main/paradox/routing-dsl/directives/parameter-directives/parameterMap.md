# parameterMap

@@@ div { .group-scala }
## Signature

@@signature [ParameterDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/ParameterDirectives.scala) { #parameterMap }

@@@

## Description

Extracts all parameters at once as a @scala[`Map[String, String]`]@java[`Map<String, String>`] mapping parameter names to parameter values.

If a query contains a parameter value several times, the map will contain the last one.

See also @ref[When to use which parameter directive?](index.md#which-parameter-directive) to understand when to use which directive.

## Example

Scala
:  @@snip [ParameterDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/ParameterDirectivesExamplesSpec.scala) { #parameterMap }

Java
:  @@snip [ParameterDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/ParameterDirectivesExamplesTest.java) { #parameterMap }
