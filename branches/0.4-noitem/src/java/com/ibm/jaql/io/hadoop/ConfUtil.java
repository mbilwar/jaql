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
package com.ibm.jaql.io.hadoop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.hadoop.mapred.JobConf;

import com.ibm.jaql.json.parser.JsonParser;
import com.ibm.jaql.json.type.JsonArray;
import com.ibm.jaql.json.type.JsonRecord;
import com.ibm.jaql.json.type.JsonValue;

/** Provides static methods that serializes and deserializes {@link JsonRecord}s and 
 * {@link JsonArray}s to and from the Hadoop configuration file */
public class ConfUtil
{
  /**
   * Write a text serialized form of args to the conf under the given name.
   * 
   * @param conf
   * @param name
   * @param args
   * @throws Exception
   */
  public static void writeConf(JobConf conf, String name, JsonRecord args)
      throws Exception
  {
    if (args == null) return;

    ByteArrayOutputStream bstr = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bstr);
    args.print(out, 0);
    out.flush();
    out.close();
    conf.set(name, bstr.toString()); // FIXME: memory and strings...
  }

  /**
   * Read a text serialized form located in the conf under name into a JRecord.
   * 
   * @param conf
   * @param name
   * @return
   * @throws Exception
   */
  public static JsonRecord readConf(JobConf conf, String name) throws Exception
  {
    String jsonTxt = conf.get(name);
    if (jsonTxt == null) return null;
    ByteArrayInputStream input = new ByteArrayInputStream(jsonTxt.getBytes());
    JsonParser parser = new JsonParser(input);
    JsonValue data = parser.TopVal();

    return (JsonRecord) data;
  }

  /**
   * @param conf
   * @param name
   * @return
   * @throws Exception
   */
  public static JsonArray readConfArray(JobConf conf, String name)
      throws Exception
  {
    String jsonTxt = conf.get(name);
    if (jsonTxt == null) return null;
    ByteArrayInputStream input = new ByteArrayInputStream(jsonTxt.getBytes());
    JsonParser parser = new JsonParser(input);

    JsonValue data = parser.TopVal();
    return (JsonArray) data;
  }

  /**
   * @param conf
   * @param name
   * @param data
   * @throws Exception
   */
  public static void writeConfArray(JobConf conf, String name, JsonArray data)
      throws Exception
  {
    if (data == null) return;

    ByteArrayOutputStream bstr = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bstr);
    JsonArray mdata = (JsonArray) data; // FIXME: don't depend on this...
    mdata.print(out);
    out.flush();
    out.close();
    conf.set(name, bstr.toString());
  }
}