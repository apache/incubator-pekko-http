# authorize

Applies the given authorization check to the request.

@@@ div { .group-scala }

## Signature

@@signature [SecurityDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/SecurityDirectives.scala) { #authorize }

@@@

## Description

The user-defined authorization check can either be supplied as a @scala[`=> Boolean`]@java[`Supplier<Boolean>`] value which is calculated
just from information out of the lexical scope, or as a function @scala[`RequestContext => Boolean`]@java[`Function[RequestContext, Boolean]`] which can also
take information from the request itself into account.

If the check returns `true` the request is passed on to the inner route unchanged, otherwise an
@apidoc[AuthorizationFailedRejection$] is created, triggering a `403 Forbidden` response by default
(the same as in the case of an @apidoc[AuthenticationFailedRejection$]).

In a common use-case you would check if a user (e.g. supplied by any of the `authenticate*` family of directives,
e.g. @ref[authenticateBasic](authenticateBasic.md)) is allowed to access the inner routes, e.g. by checking if the user has the needed permissions.

See also @ref[authorizeAsync](authorizeAsync.md) for the asynchronous version of this directive.

@@@ note
See also @ref[Authentication vs. Authorization](index.md#authentication-vs-authorization) to understand the differences between those.
@@@

## Example

Scala
:  @@snip [SecurityDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/SecurityDirectivesExamplesSpec.scala) { #authorize0-0 }

Java
:  @@snip [SecurityDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/SecurityDirectivesExamplesTest.java) { #authorize }
