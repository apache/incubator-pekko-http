<a id="storeuploadedfile"></a>
# storeUploadedFile

@@@ div { .group-scala }
## Signature

@@signature [FileUploadDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/FileUploadDirectives.scala) { #storeUploadedFile }

@@@

## Description

Streams the contents of a file uploaded as a multipart form into a file on disk and provides the file and
metadata about the upload.

If there is an error writing to disk the request will be failed with the thrown exception. If there is no field
with the given name the request will be rejected. If there are multiple file parts with the same name, the first
one will be used and the subsequent ones ignored.

@@@ note
This directive will stream contents of the request into a file, however one can not start processing these
until the file has been written completely. For streaming APIs it is preferred to use the @ref[fileUpload](fileUpload.md#fileupload)
directive, as it allows for streaming handling of the incoming data bytes.
@@@

## Example

Scala
:  @@snip [FileUploadDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/FileUploadDirectivesExamplesSpec.scala) { #storeUploadedFile }

Java
:  @@snip [FileUploadDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/FileUploadDirectivesExamplesTest.java) { #storeUploadedFile }
