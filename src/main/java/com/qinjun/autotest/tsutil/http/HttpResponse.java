/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.http;

import org.apache.http.Header;

public class HttpResponse {
    Header[] headers;
    int status;
    String body;
    byte[] byteBody;

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public byte[] getByteBody() {
        return byteBody;
    }

    public void setByteBody(byte[] byteBody) {
        this.byteBody = byteBody;
    }
}
