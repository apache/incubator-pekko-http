/*
 * Copyright (C) 2009-2022 Lightbend Inc. <https://www.lightbend.com>
 */

package org.apache.pekko.http.javadsl.model.headers;

/**
 *  Model for the `Content-Encoding` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p2-semantics-26#section-3.1.2.2
 */
public abstract class ContentEncoding extends org.apache.pekko.http.scaladsl.model.HttpHeader {
    public abstract Iterable<HttpEncoding> getEncodings();

    public static ContentEncoding create(HttpEncoding... encodings) {
        return new org.apache.pekko.http.scaladsl.model.headers.Content$minusEncoding(org.apache.pekko.http.impl.util.Util.<HttpEncoding, org.apache.pekko.http.scaladsl.model.headers.HttpEncoding>convertArray(encodings));
    }
}
