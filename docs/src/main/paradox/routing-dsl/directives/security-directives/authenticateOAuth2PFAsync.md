# authenticateOAuth2PFAsync

@@@ div { .group-scala }
## Signature

@@signature [SecurityDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/SecurityDirectives.scala) { #AsyncAuthenticatorPF }

@@signature [SecurityDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/SecurityDirectives.scala) { #authenticateOAuth2PFAsync }

@@@

## Description

Wraps the inner route with OAuth Bearer Token authentication support using a given @scala[@scaladoc[AsyncAuthenticatorPF[T]](org.apache.pekko.http.scaladsl.server.Directives#AsyncAuthenticatorPF[T]=PartialFunction[org.apache.pekko.http.scaladsl.server.directives.Credentials,scala.concurrent.Future[T]])]@java[`AsyncAuthenticatorPF<T>` - Partial function from `Optional<ProvidedCredentials>` to `CompletionStage<User>`].

Provides support for extracting the so-called "*Bearer Token*" from the @apidoc[Authorization] HTTP Header,
which is used to initiate an OAuth2 authorization.

@@@ warning
This directive does not implement the complete OAuth2 protocol, but instead enables implementing it,
by extracting the needed token from the HTTP headers.
@@@

Refer to @ref[authenticateOAuth2](authenticateOAuth2.md) for a detailed description of this directive.

Its semantics are equivalent to `authenticateOAuth2PF` 's, where not handling a case in the Partial Function (PF)
leaves the request to be rejected with a @apidoc[AuthenticationFailedRejection] rejection.

See also @ref[authenticateOAuth2PF](authenticateOAuth2PF.md) if the authorization operation is rather quick, and does not have to execute asynchronously.

See @ref[Credentials and password timing attacks](index.md#credentials-and-timing-attacks) for details about verifying the secret.

For more information on how OAuth2 works see [RFC 6750](https://tools.ietf.org/html/rfc6750).

## Example

Usage in code is exactly the same as @ref[authenticateBasicPFAsync](authenticateBasicPFAsync.md),
with the difference that one must validate the token as OAuth2 dictates (which is currently not part of Apache Pekko HTTP itself).
