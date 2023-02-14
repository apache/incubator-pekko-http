/*
 * Copyright (C) 2009-2022 Lightbend Inc. <https://www.lightbend.com>
 */

package org.apache.pekko.http.javadsl.model.headers;

/**
 *  Model for the `Remote-Address` header.
 *  Custom header we use for optionally transporting the peer's IP in an HTTP header.
 *
 *  Deprecated since 10.2.0: use the remote-header-attribute instead.
 */
@Deprecated
public abstract class RemoteAddress extends org.apache.pekko.http.scaladsl.model.HttpHeader {
    public abstract org.apache.pekko.http.javadsl.model.RemoteAddress address();

    public static RemoteAddress create(org.apache.pekko.http.javadsl.model.RemoteAddress address) {
        return new org.apache.pekko.http.scaladsl.model.headers.Remote$minusAddress(((org.apache.pekko.http.scaladsl.model.RemoteAddress) address));
    }
}