/*
 * Copyright (C) IBM Corp. 2008.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.ibm.jaql.json.type;

import com.ibm.jaql.json.meta.MetaRecord;

/**
 * 
 */
public class JavaJsonRecord extends JsonRecord
{
  protected MetaRecord meta;
  protected Object     value;
  protected JsonValue[]     fieldBuffers;

  /**
   * 
   */
  public JavaJsonRecord()
  {
  }

  /**
   * @param value
   */
  public JavaJsonRecord(Object value)
  {
    setObject(value);
  }

  /**
   * @param value
   */
  public void setObject(Object value)
  {
    if (meta == null || meta.getClazz() != value.getClass())
    {
      meta = MetaRecord.getMetaRecord(value.getClass());
      fieldBuffers = meta.makeValues();
    }
    this.value = value;
  }

  /**
   * @return
   */
  public Object getObject()
  {
    return value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.jaql.json.type.JRecord#arity()
   */
  @Override
  public int arity()
  {
    return fieldBuffers.length;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.jaql.json.type.JRecord#findName(com.ibm.jaql.json.type.JString)
   */
  @Override
  public int findName(JsonString name)
  {
    return meta.findField(name);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.jaql.json.type.JRecord#findName(java.lang.String)
   */
  @Override
  public int findName(String name)
  {
    return meta.findField(name);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.jaql.json.type.JRecord#getName(int)
   */
  @Override
  public JsonString getName(int i)
  {
    return meta.getName(i);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.jaql.json.type.JRecord#getValue(int)
   */
  @Override
  public JsonValue getValue(int i)
  {
    return fieldBuffers[i] = meta.getValue(value, i, fieldBuffers[i]);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.jaql.json.type.JValue#copy(com.ibm.jaql.json.type.JValue)
   */
  @Override
  public void setCopy(JsonValue jvalue) throws Exception
  {
    JavaJsonRecord that = (JavaJsonRecord) jvalue;
    meta.copy(this.value, that.value);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.jaql.json.type.JValue#getEncoding()
   */
  @Override
  public JsonEncoding getEncoding()
  {
    return JsonEncoding.JAVA_RECORD;
  }

  /**
   * 
   */
  public static class Test
  {
    public long       n;
    public long       m          = 17;
    protected long    no;

    public boolean    xbool      = true;
    public byte       xbyte      = 1;
    public char       xchar      = 'x';
    public short      xshort     = 3;
    public int        xint       = 4;
    public long       xlong      = 5;
    public double     xdouble    = 6.3;
    public float      xfloat     = 5.1f;
    public String     xstring    = "meta-Wow!";

    public Test       xnull      = null;
    public Test       nested;

    public long[]     xlongsNull = null;

    public boolean[]  xbools     = new boolean[]{true, false, true};
    public byte[]     xbytes     = new byte[]{3, 2, 1};
    public char[]     xchars     = new char[]{'c', 'b', 'a'};
    public short[]    xshorts    = new short[]{6, 5, 4};
    public int[]      xints      = new int[]{9, 8, 7};
    public long[]     xlongs     = new long[]{12, 11, 10};
    public double[]   xdoubles   = new double[]{6.6, 5.5, 4.4};
    public float[]    xfloats    = new float[]{3.3f, 2.2f, 1.1f};
    public String[]   xstrings   = new String[]{"three", "two", "one"};

    protected boolean ybool      = true;
    protected byte    ybyte      = 1;
    protected char    ychar      = 'y';
    protected short   yshort     = 3;
    protected int     yint       = 4;
    protected long    ylong      = 5;
    protected double  ydouble    = 6.3;
    protected float   yfloat     = 5.1f;
    protected String  ystring    = "y not?";
    protected Test    ynull      = null;
    protected Test    ynested;

    public Test()
    {
    }
    Test(long n)
    {
      this.xlong = n;
      ylong = -n;
    }

    public boolean getBool()
    {
      return ybool;
    }
    public void setBool(boolean x)
    {
      ybool = x;
    }
    public byte getByte()
    {
      return ybyte;
    }
    public void setByte(byte x)
    {
      ybyte = x;
    }
    public char getChar()
    {
      return ychar;
    }
    public void setChar(char x)
    {
      ychar = x;
    }
    public short getShort()
    {
      return yshort;
    }
    public void setShort(short x)
    {
      yshort = x;
    }
    public int getInt()
    {
      return yint;
    }
    public void setInt(int x)
    {
      yint = x;
    }
    public long getLong()
    {
      return ylong;
    }
    public void setLong(long x)
    {
      ylong = x;
    }
    public double getDouble()
    {
      return ydouble;
    }
    public void setDouble(double x)
    {
      ydouble = x;
    }
    public float getFloat()
    {
      return yfloat;
    }
    public void setFloat(float x)
    {
      yfloat = x;
    }
    public String getString()
    {
      return ystring;
    }
    public void setString(String x)
    {
      ystring = x;
    }
    //public Test    getNull()   { return ynull;   }  public void setNull(Test x) { ynull = x; }
    //public Test    getNested() { return ynested; }  public void setNested(Test x) { ynested = x; }

    protected String[] ystrings = new String[]{"a", "protected", "string",
                                    "array"};
    public String[] getStrings()
    {
      return ystrings;
    }
    public void setStrings(String[] x)
    {
      ystrings = x;
    }
  };
  /**
   * @param n
   * @return
   */
  public JavaJsonRecord eval(JsonNumber n)
  {
    Test test = new Test(n.longValue());
    test.nested = new Test(n.longValue() + 1);
    return new JavaJsonRecord(test);
  }
}