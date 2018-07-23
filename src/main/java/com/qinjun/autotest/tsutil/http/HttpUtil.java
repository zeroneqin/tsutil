/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.http;

import com.qinjun.autotest.tsutil.exception.ExceptionUtil;
import com.qinjun.autotest.tsutil.exception.TSUtilException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static HttpResponse sendGet(String url,Map<String,String> headerMap) throws TSUtilException {
        logger.info("Start to send get request");
        HttpResponse httpResponse = new HttpResponse();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet();
            logger.info("Request:"+httpGet.getRequestLine());
            if (headerMap!=null) {
                for (String headerKey: headerMap.keySet()) {
                    String headerValue = headerMap.get(headerKey);
                    if (!StringUtils.isEmpty(headerValue)) {
                        logger.debug("Request header name:[{}], value:[{}]",headerKey,headerValue);
                        httpGet.addHeader(headerKey,headerValue);
                    }
                }
            }

            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);
            Header[] headers = httpResponse.getHeaders();
            for (Header header: headers) {
                logger.debug("Response header name:[{}], value:[{}]",header.getName(),header.getValue());
            }

            httpResponse.setHeaders(headers);
            HttpEntity httpResponseEntity = closeableHttpResponse.getEntity();
            int status = closeableHttpResponse.getStatusLine().getStatusCode();
            httpResponse.setStatus(status);
            logger.info("Response status code:"+status);
            if (httpResponseEntity!=null) {
                logger.info("Response body length:"+httpResponseEntity.getContentLength());
                byte[] reponseByteBody = EntityUtils.toByteArray(httpResponseEntity);
                String responseBody = new String(reponseByteBody,"utf8");
                logger.info("Response body:"+responseBody);
                EntityUtils.consume(httpResponseEntity);
                httpResponse.setBody(responseBody);
                httpResponse.setByteBody(reponseByteBody);
            }
        }
        catch(Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        finally {
            try {
                httpClient.close();
            }
            catch (IOException ioe) {
                String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(ioe);
                logger.error("Get exception:"+exceptionStackTrace);
                throw new TSUtilException(exceptionStackTrace);
            }
        }
        return httpResponse;
    }

    public static HttpResponse sendGet(String url,Map<String,String> headerMap, Map<String,String> queryParamMap,boolean encoding) throws TSUtilException {
        logger.info("Start to send get request");
        HttpResponse httpResponse = new HttpResponse();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if (queryParamMap.size()>0) {
            url = url +"?";
            boolean firstQueryParam = true;
            String queryStr = "";
            for (String queryParamName : queryParamMap.keySet()) {
                String queryParamValue = queryParamMap.get(queryParamName);
                if(encoding) {
                    queryParamValue = URLEncoder.encode(queryParamValue);
                }
                if (firstQueryParam) {
                    queryStr = queryStr + queryParamName +"="+ queryParamValue;
                    firstQueryParam=false;
                }
                else {
                    queryStr = queryStr + "&" + queryParamName + "=" + queryParamValue;
                }
            }
            url = url + queryStr;
        }


        try {
            HttpGet httpGet = new HttpGet();
            logger.info("Request:"+httpGet.getRequestLine());
            if (headerMap!=null) {
                for (String headerKey: headerMap.keySet()) {
                    String headerValue = headerMap.get(headerKey);
                    if (!StringUtils.isEmpty(headerValue)) {
                        logger.debug("Request header name:[{}], value:[{}]",headerKey,headerValue);
                        httpGet.addHeader(headerKey,headerValue);
                    }
                }
            }

            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);
            Header[] headers = httpResponse.getHeaders();
            for (Header header: headers) {
                logger.debug("Response header name:[{}], value:[{}]",header.getName(),header.getValue());
            }

            httpResponse.setHeaders(headers);
            HttpEntity httpResponseEntity = closeableHttpResponse.getEntity();
            int status = closeableHttpResponse.getStatusLine().getStatusCode();
            httpResponse.setStatus(status);
            logger.info("Response status code:"+status);
            if (httpResponseEntity!=null) {
                logger.info("Response body length:"+httpResponseEntity.getContentLength());
                byte[] reponseByteBody = EntityUtils.toByteArray(httpResponseEntity);
                String responseBody = new String(reponseByteBody,"utf8");
                logger.info("Response body:"+responseBody);
                EntityUtils.consume(httpResponseEntity);
                httpResponse.setBody(responseBody);
                httpResponse.setByteBody(reponseByteBody);
            }
        }
        catch(Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        finally {
            try {
                httpClient.close();
            }
            catch (IOException ioe) {
                String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(ioe);
                logger.error("Get exception:"+exceptionStackTrace);
                throw new TSUtilException(exceptionStackTrace);
            }
        }
        return httpResponse;
    }


    public static HttpResponse sendPost(String url, Map<String,String> headerMap, String requestBody, ContentType contentType) throws TSUtilException {
        logger.info("Start to send post request");
        HttpResponse httpResponse = new HttpResponse();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpPost httpPost = new HttpPost();
            logger.info("Request:"+httpPost.getRequestLine());
            if (headerMap!=null) {
                for (String headerKey: headerMap.keySet()) {
                    String headerValue = headerMap.get(headerKey);
                    if (!StringUtils.isEmpty(headerValue)) {
                        logger.debug("Request header name:[{}], value:[{}]",headerKey,headerValue);
                        httpPost.addHeader(headerKey,headerValue);
                    }
                }
            }

            if (!StringUtils.isEmpty(requestBody)) {
                StringEntity stringRequestEntity = new StringEntity(requestBody,contentType);
                httpPost.setEntity(stringRequestEntity);
                logger.debug("Request content type:"+contentType);
                logger.debug("Request body:"+requestBody);
            }

            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);
            Header[] headers = httpResponse.getHeaders();
            for (Header header: headers) {
                logger.debug("Response header name:[{}], value:[{}]",header.getName(),header.getValue());
            }

            httpResponse.setHeaders(headers);
            HttpEntity httpResponseEntity = closeableHttpResponse.getEntity();
            int status = closeableHttpResponse.getStatusLine().getStatusCode();
            httpResponse.setStatus(status);
            logger.info("Response status code:"+status);
            if (httpResponseEntity!=null) {
                logger.info("Response body length:"+httpResponseEntity.getContentLength());
                byte[] reponseByteBody = EntityUtils.toByteArray(httpResponseEntity);
                String responseBody = new String(reponseByteBody,"utf8");
                logger.info("Response body:"+responseBody);
                EntityUtils.consume(httpResponseEntity);
                httpResponse.setBody(responseBody);
                httpResponse.setByteBody(reponseByteBody);
            }
        }
        catch(Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        finally {
            try {
                httpClient.close();
            }
            catch (IOException ioe) {
                String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(ioe);
                logger.error("Get exception:"+exceptionStackTrace);
                throw new TSUtilException(exceptionStackTrace);
            }
        }
        return httpResponse;
    }


    public static HttpResponse sendBytePost(String url, Map<String,String> headerMap, byte[] requsetByteBody) throws TSUtilException {
        logger.info("Start to send post request");
        HttpResponse httpResponse = new HttpResponse();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpPost httpPost = new HttpPost();
            logger.info("Request:"+httpPost.getRequestLine());
            if (headerMap!=null) {
                for (String headerKey: headerMap.keySet()) {
                    String headerValue = headerMap.get(headerKey);
                    if (!StringUtils.isEmpty(headerValue)) {
                        logger.debug("Request header name:[{}], value:[{}]",headerKey,headerValue);
                        httpPost.addHeader(headerKey,headerValue);
                    }
                }
            }

            if (requsetByteBody!=null) {
                HttpEntity httpRequestEntity = new ByteArrayEntity(requsetByteBody);
                httpPost.setEntity(httpRequestEntity);
            }

            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);
            Header[] headers = httpResponse.getHeaders();
            for (Header header: headers) {
                logger.debug("Response header name:[{}], value:[{}]",header.getName(),header.getValue());
            }

            httpResponse.setHeaders(headers);
            HttpEntity httpResponseEntity = closeableHttpResponse.getEntity();
            int status = closeableHttpResponse.getStatusLine().getStatusCode();
            httpResponse.setStatus(status);
            logger.info("Response status code:"+status);
            if (httpResponseEntity!=null) {
                logger.info("Response body length:"+httpResponseEntity.getContentLength());
                byte[] reponseByteBody = EntityUtils.toByteArray(httpResponseEntity);
                String responseBody = new String(reponseByteBody,"utf8");
                logger.info("Response body:"+responseBody);
                EntityUtils.consume(httpResponseEntity);
                httpResponse.setBody(responseBody);
                httpResponse.setByteBody(reponseByteBody);
            }
        }
        catch(Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        finally {
            try {
                httpClient.close();
            }
            catch (IOException ioe) {
                String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(ioe);
                logger.error("Get exception:"+exceptionStackTrace);
                throw new TSUtilException(exceptionStackTrace);
            }
        }
        return httpResponse;
    }



    public static HttpResponse sendFormPost(String url, Map<String,String> headerMap, Map<String,String> formMap) throws TSUtilException {
        logger.info("Start to send post request");
        HttpResponse httpResponse = new HttpResponse();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpPost httpPost = new HttpPost();
            logger.info("Request:"+httpPost.getRequestLine());
            if (headerMap!=null) {
                for (String headerKey: headerMap.keySet()) {
                    String headerValue = headerMap.get(headerKey);
                    if (!StringUtils.isEmpty(headerValue)) {
                        logger.debug("Request header name:[{}], value:[{}]",headerKey,headerValue);
                        httpPost.addHeader(headerKey,headerValue);
                    }
                }
            }

            List<BasicNameValuePair> formParamList = new ArrayList<BasicNameValuePair>();
            for (String formName: formMap.keySet()) {
                String formValue = formMap.get(formName);
                formParamList.add(new BasicNameValuePair(formName,formValue));
                logger.info("Request form param name:[{}], value:[{}]",formName,formValue);
            }

            HttpEntity httpRequestEntity = new UrlEncodedFormEntity(formParamList,"UTF-8");
            httpPost.setEntity(httpRequestEntity);

            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);
            Header[] headers = httpResponse.getHeaders();
            for (Header header: headers) {
                logger.debug("Response header name:[{}], value:[{}]",header.getName(),header.getValue());
            }

            httpResponse.setHeaders(headers);
            HttpEntity httpResponseEntity = closeableHttpResponse.getEntity();
            int status = closeableHttpResponse.getStatusLine().getStatusCode();
            httpResponse.setStatus(status);
            logger.info("Response status code:"+status);
            if (httpResponseEntity!=null) {
                logger.info("Response body length:"+httpResponseEntity.getContentLength());
                byte[] reponseByteBody = EntityUtils.toByteArray(httpResponseEntity);
                String responseBody = new String(reponseByteBody,"utf8");
                logger.info("Response body:"+responseBody);
                EntityUtils.consume(httpResponseEntity);
                httpResponse.setBody(responseBody);
                httpResponse.setByteBody(reponseByteBody);
            }
        }
        catch(Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        finally {
            try {
                httpClient.close();
            }
            catch (IOException ioe) {
                String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(ioe);
                logger.error("Get exception:"+exceptionStackTrace);
                throw new TSUtilException(exceptionStackTrace);
            }
        }
        return httpResponse;
    }
}
