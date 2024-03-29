# @scala[formFieldSeq]@java[formFieldList]

@@@ div { .group-scala }

## Signature

@@signature [FormFieldDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/FormFieldDirectives.scala) { #formFieldSeq }

@@@

## Description

Extracts all HTTP form fields at once in the original order as (name, value) tuples of type @scala[`(String, String)`]@java[`Map.Entry<String, String>`]. Data posted from [HTML Forms](https://www.w3.org/TR/html401/interact/forms.html#h-17.13.4) is either of type `application/x-www-form-urlencoded` or of type `multipart/form-data`.

This directive can be used if the exact order of form fields is important or if parameters can occur several times.

See @ref[formFields](formFields.md) for an in-depth description.

## Warning

The directive reads all incoming HTTP form fields without any configured upper bound.
It means, that requests with form fields holding significant amount of data (ie. during a file upload)
can cause performance issues or even an `OutOfMemoryError` s.

## Example

Scala
:  @@snip [FormFieldDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/FormFieldDirectivesExamplesSpec.scala) { #formFieldSeq }

Java
:  @@snip [FormFieldDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/FormFieldDirectivesExamplesTest.java) { #formFieldList }
