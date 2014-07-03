/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.duitang.service.l2;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Keys extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Keys\",\"namespace\":\"com.duitang.service.l2\",\"fields\":[{\"name\":\"keys\",\"type\":{\"type\":\"array\",\"items\":\"string\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.util.List<java.lang.CharSequence> keys;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public Keys() {}

  /**
   * All-args constructor.
   */
  public Keys(java.util.List<java.lang.CharSequence> keys) {
    this.keys = keys;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return keys;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: keys = (java.util.List<java.lang.CharSequence>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'keys' field.
   */
  public java.util.List<java.lang.CharSequence> getKeys() {
    return keys;
  }

  /**
   * Sets the value of the 'keys' field.
   * @param value the value to set.
   */
  public void setKeys(java.util.List<java.lang.CharSequence> value) {
    this.keys = value;
  }

  /** Creates a new Keys RecordBuilder */
  public static com.duitang.service.l2.Keys.Builder newBuilder() {
    return new com.duitang.service.l2.Keys.Builder();
  }
  
  /** Creates a new Keys RecordBuilder by copying an existing Builder */
  public static com.duitang.service.l2.Keys.Builder newBuilder(com.duitang.service.l2.Keys.Builder other) {
    return new com.duitang.service.l2.Keys.Builder(other);
  }
  
  /** Creates a new Keys RecordBuilder by copying an existing Keys instance */
  public static com.duitang.service.l2.Keys.Builder newBuilder(com.duitang.service.l2.Keys other) {
    return new com.duitang.service.l2.Keys.Builder(other);
  }
  
  /**
   * RecordBuilder for Keys instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Keys>
    implements org.apache.avro.data.RecordBuilder<Keys> {

    private java.util.List<java.lang.CharSequence> keys;

    /** Creates a new Builder */
    private Builder() {
      super(com.duitang.service.l2.Keys.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(com.duitang.service.l2.Keys.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.keys)) {
        this.keys = data().deepCopy(fields()[0].schema(), other.keys);
        fieldSetFlags()[0] = true;
      }
    }
    
    /** Creates a Builder by copying an existing Keys instance */
    private Builder(com.duitang.service.l2.Keys other) {
            super(com.duitang.service.l2.Keys.SCHEMA$);
      if (isValidValue(fields()[0], other.keys)) {
        this.keys = data().deepCopy(fields()[0].schema(), other.keys);
        fieldSetFlags()[0] = true;
      }
    }

    /** Gets the value of the 'keys' field */
    public java.util.List<java.lang.CharSequence> getKeys() {
      return keys;
    }
    
    /** Sets the value of the 'keys' field */
    public com.duitang.service.l2.Keys.Builder setKeys(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[0], value);
      this.keys = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'keys' field has been set */
    public boolean hasKeys() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'keys' field */
    public com.duitang.service.l2.Keys.Builder clearKeys() {
      keys = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    public Keys build() {
      try {
        Keys record = new Keys();
        record.keys = fieldSetFlags()[0] ? this.keys : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[0]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}