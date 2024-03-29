# entity

@@@ div { .group-scala }

## Signature

@@signature [MarshallingDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/MarshallingDirectives.scala) { #entity }

@@@

## Description

Unmarshalls the request entity to the given type and passes it to its inner Route.  An unmarshaller
returns an `Either` with `Right(value)` if successful or `Left(exception)` for a failure.
The `entity` method will either pass the `value` to the inner route or map the `exception` to a
@apidoc[Rejection].

@scala[The `entity` directive works in conjunction with `as` and `org.apache.pekko.http.scaladsl.unmarshalling` to
convert some serialized "wire format" value into a higher-level object structure.]  
@ref[The unmarshalling documentation](../../../common/unmarshalling.md) explains this process in detail.
This directive simplifies extraction and error handling to the specified type from the request.

An unmarshaller will return a `Left(exception)` in the case of an error.  This is converted to a
@apidoc[Rejection] within the `entity` directive.  The following table lists how exceptions
are mapped to rejections:

|Left(exception)          | Rejection                                                                |
|-------------------------|--------------------------------------------------------------------------|
|`ContentExpected`        | @apidoc[RequestEntityExpectedRejection$]                                         |
|`UnsupportedContentType` | @apidoc[UnsupportedRequestContentTypeRejection], which lists the supported types|
|`MalformedContent`       | @apidoc[MalformedRequestContentRejection], with an error message and cause      |

## Examples

The following example uses @scala[`spray-json`]@java[@ref[Json Support via Jackson](../../../common/json-support.md#jackson-support)] to unmarshall a json request into a simple `Person` 
class. @scala[It utilizes `SprayJsonSupport` via the `PersonJsonSupport` object as the in-scope unmarshaller.]

Scala
:   @@snip [MarshallingDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/MarshallingDirectivesExamplesSpec.scala) { #person-case-class #person-json-support }

Java
:   @@snip [MarshallingDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/MarshallingDirectivesExamplesTest.java) { #person }


Scala
:   @@snip [MarshallingDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/MarshallingDirectivesExamplesSpec.scala) { #example-entity-with-json }

Java
:   @@snip [MarshallingDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/MarshallingDirectivesExamplesTest.java) { #example-entity-with-json }


@@@ div { .group-scala }
It is also possible to use the `entity` directive to obtain raw `JsValue` ( [spray-json](https://github.com/spray/spray-json) ) objects, by simply using
`as[JsValue]`, or any other JSON type for which you have marshallers in-scope.

@@snip [MarshallingDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/MarshallingDirectivesExamplesSpec.scala) { #example-entity-with-raw-json }

@@@
