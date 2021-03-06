//--------------------------------------------------------------------------------------------------
// $Header$
// Copyright (c) 1996-2002 Borland Software Corporation. All Rights Reserved.
//--------------------------------------------------------------------------------------------------

package com.borland.dx.text;

import java.io.Serializable;

import com.borland.dx.dataset.Variant;

/**
 * The TimestampFormatter class formats and parses timestamp data.
 */
public class TimestampFormatter extends VariantFormatter implements Serializable
{
 /**
 * Constructs a TimestampFormatter object.
 */
  public TimestampFormatter() {
    super();
  }

  /**
   * Returns a String representing the given timestamp stored in the Variant. A returned empty
   *  string indicates a null or empty input value. null means the formatting failed.
   * @param value Variant
   * @return String
   */
  public final String format(Variant value) {
    return (value == null || value.isNull())
              ? ""
              : value.getTimestamp().toString();
    // Note: toString and valueOf use JDBC date escape syntax so must agree with each other
  }

  /**
   * Analyzes the given String and produces as output a
   *  Variant containing the approriate value.
   * @param stringValue String
   * @param value Variant
   */
  public final void parse(String stringValue, Variant value) {
    if (stringValue == null || (stringValue=stringValue.trim()).length() == 0) {
      value.setUnassignedNull();
      return;
    }
    java.sql.Timestamp t = java.sql.Timestamp.valueOf(stringValue);  // uses JDBC date escape form
    value.setTimestamp(t);
  }

  /**
   * A high-speed parse that parses directly into a character array.
   * @param variant Variant
   * @param value char[]
   * @param offset integer
   * @param len integer
   */
  public void parse(Variant variant, char[] value, int offset, int len) {
    if (len == 0)
      variant.setUnassignedNull();
    else
      variant.setTimestamp(java.sql.Timestamp.valueOf(new String(value, offset, len)));
  }

  /**
   * Returns the Variant type, which is always Variant.TIME for TimestampFormatter.
   *
   */
  public int getVariantType() { return Variant.TIME; }

}
