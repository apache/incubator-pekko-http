# authenticateOAuth2

@@@ div { .group-scala }
## Signature

@@signature [SecurityDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/SecurityDirectives.scala) { #Authenticator }

@@signature [SecurityDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/SecurityDirectives.scala) { #authenticateOAuth2 }

@@@

## Description

Wraps the inner route with OAuth Bearer Token authentication support using a given @scala[@scaladoc[Authenticator[T]](org.apache.pekko.http.scaladsl.server.Directives#Authenticator[T]=org.apache.pekko.http.scaladsl.server.directives.Credentials=%3EOption[T])]@java[`Authenticator<T>` - function from `Optional<ProvidedCredentials>` to `Optional<T>`].

Provides support for extracting the so-called "*Bearer Token*" from the @apidoc[Authorization] HTTP Header,
which is used to initiate an OAuth2 authorization. The directive also supports extracting the Bearer Token from URI query parameter `access_token`, as described in [RFC 6750](https://tools.ietf.org/html/rfc6750).

@@@ warning
This directive does not implement the complete OAuth2 protocol, but instead enables implementing it,
by extracting the needed token from the HTTP headers.
@@@

Given a function returning @scala[`Some[T]`]@java[a non empty`Optional<T>`] upon successful authentication and @scala[`None`]@java[an empty `Optional<T>`] otherwise,
respectively applies the inner route or rejects the request with a @apidoc[AuthenticationFailedRejection] rejection,
which by default is mapped to an `401 Unauthorized` response.

Longer-running authentication tasks (like looking up credentials in a database) should use the @ref[authenticateOAuth2Async](authenticateOAuth2Async.md)
variant of this directive which allows it to run without blocking routing layer of Apache Pekko HTTP, freeing it for other requests.

See @ref[Credentials and password timing attacks](index.md#credentials-and-timing-attacks) for details about verifying the secret.

For more information on how OAuth2 works see [RFC 6750](https://tools.ietf.org/html/rfc6750).

## Example

Usage in code is exactly the same as @ref[authenticateBasic](authenticateBasic.md),
with the difference that one must validate the token as OAuth2 dictates (which is currently not part of Apache Pekko HTTP itself).
