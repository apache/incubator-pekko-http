# listDirectoryContents

@@@ div { .group-scala }

## Signature

@@signature [FileAndResourceDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/FileAndResourceDirectives.scala) { #listDirectoryContents }

@@@

## Description

Completes `GET` requests with a unified listing of the contents of all given directories. The actual rendering of the
directory contents is performed by the in-scope @apidoc[Marshaller[DirectoryListing]].

To just serve files use @ref[getFromDirectory](getFromDirectory.md).

To serve files and provide a browseable directory listing use @ref[getFromBrowseableDirectories](getFromBrowseableDirectories.md) instead.

The rendering can be overridden by providing a custom @scala[@apidoc[Marshaller[DirectoryListing]]]@java[`DirectoryRenderer` implementation], you can read more about it in
@ref[getFromDirectory](getFromDirectory.md) 's documentation.

Note that it's not required to wrap this directive with `get` as this directive will only respond to `GET` requests.

## Example

Scala
:  @@snip [FileAndResourceDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/FileAndResourceDirectivesExamplesSpec.scala) { #listDirectoryContents-examples }

Java
:  @@snip [FileAndResourceDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/FileAndResourceDirectivesExamplesTest.java) { #listDirectoryContents }
