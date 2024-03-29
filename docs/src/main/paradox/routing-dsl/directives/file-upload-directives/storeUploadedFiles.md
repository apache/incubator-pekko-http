<a id="storeuploadedfiles"></a>
# storeUploadedFiles

@@@ div { .group-scala }
## Signature

@@signature [FileUploadDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/FileUploadDirectives.scala) { #storeUploadedFiles }

@@@

## Description

Streams the contents of all files uploaded in a multipart form into files on disk and provides a list of each
file and metadata about the upload.

If there is an error writing to disk the request will be failed with the thrown exception. If there is no field
with the given name the request will be rejected.

## Example

Scala
:  @@snip [FileUploadDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/FileUploadDirectivesExamplesSpec.scala) { #storeUploadedFiles }

Java
:  @@snip [FileUploadDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/FileUploadDirectivesExamplesTest.java) { #storeUploadedFiles }
