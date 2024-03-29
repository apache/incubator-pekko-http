# selectPreferredLanguage

@@@ div { .group-scala }

## Signature

@@signature [MiscDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/MiscDirectives.scala) { #selectPreferredLanguage }

@@@

## Description

Inspects the request's `Accept-Language` header and determines,
which of a given set of language alternatives is preferred by the client according to content negotiation rules
defined by [RFC 7231 in section 5.3.5](https://tools.ietf.org/html/rfc7231#section-5.3.5).

If there are several best language alternatives that the client has equal preference for
(even if this preference is zero!) the order of the arguments is used as a tie breaker (first one wins).

## Example

Scala
:  @@snip [MiscDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/MiscDirectivesExamplesSpec.scala) { #selectPreferredLanguage-example }

Java
:  @@snip [MiscDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/MiscDirectivesExamplesTest.java) { #selectPreferredLanguage }
