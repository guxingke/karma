/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.duitang.service.demo;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public interface DemoServiceSpec {
  public static final org.apache.avro.Protocol PROTOCOL = org.apache.avro.Protocol.parse("{\"protocol\":\"DemoServiceSpec\",\"namespace\":\"com.duitang.service.demo\",\"types\":[],\"messages\":{\"memory_getString\":{\"request\":[{\"name\":\"key\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}],\"response\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}]},\"memory_setString\":{\"request\":[{\"name\":\"key\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"value\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"ttl\",\"type\":\"int\"}],\"response\":\"boolean\"},\"memory_getBytes\":{\"request\":[{\"name\":\"key\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}],\"response\":[\"null\",\"bytes\"]},\"memory_setBytes\":{\"request\":[{\"name\":\"key\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"value\",\"type\":\"bytes\"},{\"name\":\"ttl\",\"type\":\"int\"}],\"response\":\"boolean\"},\"trace_msg\":{\"request\":[{\"name\":\"key\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"ttl\",\"type\":\"long\"}],\"response\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}}}");
  java.lang.String memory_getString(java.lang.String key) throws org.apache.avro.AvroRemoteException;
  boolean memory_setString(java.lang.String key, java.lang.String value, int ttl) throws org.apache.avro.AvroRemoteException;
  java.nio.ByteBuffer memory_getBytes(java.lang.String key) throws org.apache.avro.AvroRemoteException;
  boolean memory_setBytes(java.lang.String key, java.nio.ByteBuffer value, int ttl) throws org.apache.avro.AvroRemoteException;
  java.lang.String trace_msg(java.lang.String key, long ttl) throws org.apache.avro.AvroRemoteException;

  @SuppressWarnings("all")
  public interface Callback extends DemoServiceSpec {
    public static final org.apache.avro.Protocol PROTOCOL = com.duitang.service.demo.DemoServiceSpec.PROTOCOL;
    void memory_getString(java.lang.String key, org.apache.avro.ipc.Callback<java.lang.String> callback) throws java.io.IOException;
    void memory_setString(java.lang.String key, java.lang.String value, int ttl, org.apache.avro.ipc.Callback<java.lang.Boolean> callback) throws java.io.IOException;
    void memory_getBytes(java.lang.String key, org.apache.avro.ipc.Callback<java.nio.ByteBuffer> callback) throws java.io.IOException;
    void memory_setBytes(java.lang.String key, java.nio.ByteBuffer value, int ttl, org.apache.avro.ipc.Callback<java.lang.Boolean> callback) throws java.io.IOException;
    void trace_msg(java.lang.String key, long ttl, org.apache.avro.ipc.Callback<java.lang.String> callback) throws java.io.IOException;
  }
}