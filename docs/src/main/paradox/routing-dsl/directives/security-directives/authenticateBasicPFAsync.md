# authenticateBasicPFAsync

@@@ div { .group-scala }
## Signature

@@signature [SecurityDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/SecurityDirectives.scala) { #AsyncAuthenticatorPF }

@@signature [SecurityDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/SecurityDirectives.scala) { #authenticateBasicPFAsync }

@@@

## Description

Wraps the inner route with Http Basic authentication support using a given @scala[@scaladoc[AsyncAuthenticatorPF[T]](org.apache.pekko.http.scaladsl.server.Directives#AsyncAuthenticatorPF[T]=PartialFunction[org.apache.pekko.http.scaladsl.server.directives.Credentials,scala.concurrent.Future[T]])]@java[`AsyncAuthenticatorPF<T>` - Partial function from `Optional<ProvidedCredentials>` to `CompletionStage<User>`].

Provides support for handling [HTTP Basic Authentication](https://en.wikipedia.org/wiki/Basic_auth).

Refer to @ref[authenticateBasic](authenticateBasic.md) for a detailed description of this directive.

Its semantics are equivalent to `authenticateBasicPF` 's, where not handling a case in the Partial Function (PF)
leaves the request to be rejected with a @apidoc[AuthenticationFailedRejection] rejection.

See @ref[Credentials and password timing attacks](index.md#credentials-and-timing-attacks) for details about verifying the secret.

@@@ warning
Make sure to use basic authentication only over SSL/TLS because credentials are transferred in plaintext.
@@@

## Example

Scala
:  @@snip [SecurityDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/SecurityDirectivesExamplesSpec.scala) { #authenticateBasicPFAsync-0 }

Java
:  @@snip [SecurityDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/SecurityDirectivesExamplesTest.java) { #authenticateBasicPFAsync }
