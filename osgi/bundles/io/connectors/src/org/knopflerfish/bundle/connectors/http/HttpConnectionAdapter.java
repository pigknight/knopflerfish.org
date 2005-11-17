/*
 * Copyright (c) 2005 Gatespace Telematics. All Rights Reserved.
 */

package org.knopflerfish.bundle.connectors.http;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.microedition.io.HttpConnection;


/**
 * @author Kaspar Weilenmann &lt;kaspar@gatespacetelematics.com&gt;
 */
public class HttpConnectionAdapter implements HttpConnection {

  // private fields

  private HttpURLConnection connection;
  private URL url;


  // constructors

  public HttpConnectionAdapter(HttpURLConnection connection) {
    this.connection = connection;
    this.url = connection.getURL();
  }


  // implements HttpConnection

  public String getType() {
    return connection.getContentType();
  }

  public long getDate() throws IOException {
    return connection.getDate();
  }

  public long getExpiration() throws IOException {
    return connection.getExpiration();
  }

  public String getFile() {
    return url.getFile();
  }

  public String getHeaderField(int n) {
    return connection.getHeaderField(n);
  }

  public String getHeaderField(String name) throws IOException {
    return connection.getHeaderField(name);
  }

  public long getHeaderFieldDate(String name, long def) throws IOException {
    return connection.getHeaderFieldDate(name, def);
  }

  public int getHeaderFieldInt(String name, int def) throws IOException {
    return connection.getHeaderFieldInt(name, def);
  }

  public String getHeaderFieldKey(int n) throws IOException {
    return connection.getHeaderFieldKey(n);
  }

  public String getHost() {
    return url.getHost();
  }

  public long getLastModified() throws IOException {
    return connection.getLastModified();
  }

  public int getPort() {
    return url.getPort();
  }

  public String getProtocol() {
    return url.getProtocol();
  }

  public String getQuery() {
    return url.getQuery();
  }

  public String getRef() {
    return url.getRef();
  }

  public String getRequestMethod() {
    return connection.getRequestMethod();
  }

  public String getRequestProperty(String key) {
    return connection.getRequestProperty(key);
  }

  public int getResponseCode() throws IOException {
    return connection.getResponseCode();
  }

  public String getResponseMessage() throws IOException {
    return connection.getResponseMessage();
  }

  public String getURL() {
    return url.toString();
  }

  public void setRequestMethod(String method) throws IOException {
    connection.setRequestMethod(method);
  }

  public void setRequestProperty(String key, String value) throws IOException {
    connection.setRequestProperty(key, value);
  }

  public String getEncoding() {
    return connection.getContentEncoding();
  }

  public long getLength() {
    return connection.getContentLength();
  }

  public DataInputStream openDataInputStream() throws IOException {
    return new DataInputStream(connection.getInputStream());
  }

  public InputStream openInputStream() throws IOException {
    return openDataInputStream();
  }

  public DataOutputStream openDataOutputStream() throws IOException {
    return new DataOutputStream(connection.getOutputStream());
  }

  public OutputStream openOutputStream() throws IOException {
    return openDataOutputStream();
  }

  public void close() throws IOException {
    connection.disconnect();
  }

} // HttpConnectionAdapter