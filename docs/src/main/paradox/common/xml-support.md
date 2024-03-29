# XML Support

Apache Pekko HTTP's @ref[marshalling](marshalling.md) and @ref[unmarshalling](unmarshalling.md)
infrastructure makes it rather easy to seamlessly support specific wire representations of your data objects, like JSON,
XML or even binary encodings.

@@@ div { .group-java }

Apache Pekko HTTP does not currently provide a Java API for XML support. If you need to
produce and consume XML, you can write a @ref[custom marshaller](marshalling.md#custom-marshallers)
using [Jackson], which is also the library used for providing @ref[JSON support](json-support.md#jackson-support).

@@ snip [#jackson-xml-support] ($root$/src/test/java/docs/http/javadsl/JacksonXmlSupport.java) { #jackson-xml-support }

The custom XML (un)marshalling code shown above requires that you depend on the `jackson-dataformat-xml` library.

@@dependency [sbt,Gradle,Maven] {
  group="com.fasterxml.jackson.dataformat"
  artifact="jackson-dataformat-xml"
  version="$jackson.xml.version$"
}

@@@

@@@ div { .group-scala }

For XML Apache Pekko HTTP currently provides support for [Scala XML][scala-xml] right out of the box through it's
`pekko-http-xml` module.

## Scala XML Support

The @scaladoc[ScalaXmlSupport](org.apache.pekko.http.scaladsl.marshallers.xml.ScalaXmlSupport) trait provides a `FromEntityUnmarshaller[NodeSeq]` and `ToEntityMarshaller[NodeSeq]` that
you can use directly or build upon.

In order to enable support for (un)marshalling from and to XML with [Scala XML][scala-xml] `NodeSeq` you must add
the following dependency:

@@dependency [sbt,Gradle,Maven] {
  bomGroup2="org.apache.pekko" bomArtifact2="pekko-http-bom_$scala.binary.version$" bomVersionSymbols2="PekkoHttpVersion"
  symbol="PekkoHttpVersion"
  value="$project.version$"
  group="org.apache.pekko"
  artifact="pekko-http-xml_$scala.binary.version$"
  version="PekkoHttpVersion"
}

Once you have done this (un)marshalling between XML and `NodeSeq` instances should work nicely and transparently,
by either using `import org.apache.pekko.http.scaladsl.marshallers.xml.ScalaXmlSupport._` or mixing in the
`org.apache.pekko.http.scaladsl.marshallers.xml.ScalaXmlSupport` trait.

@@@

 [scala-xml]: https://github.com/scala/scala-xml
 [jackson]: https://github.com/FasterXML/jackson
