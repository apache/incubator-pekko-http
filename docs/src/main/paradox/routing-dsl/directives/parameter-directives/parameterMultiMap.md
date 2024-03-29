# parameterMultiMap

@@@ div { .group-scala }

## Signature

@@signature [ParameterDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/ParameterDirectives.scala) { #parameterMultiMap }

@@@

## Description

Extracts all parameters at once as a multi-map of type @scala[`Map[String, List[String]]`]@java[`Map<String, List<String>>`] mapping
a parameter name to a list of all its values.

This directive can be used if parameters can occur several times.

The order of values is *not* specified.

See @ref[When to use which parameter directive?](index.md#which-parameter-directive) to understand when to use which directive.

## Example

Scala
:  @@snip [ParameterDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/ParameterDirectivesExamplesSpec.scala) { #parameterMultiMap }

Java
:  @@snip [ParameterDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/ParameterDirectivesExamplesTest.java) { #parameterMultiMap }
