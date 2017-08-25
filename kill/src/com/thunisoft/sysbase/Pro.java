package com.thunisoft.sysbase;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Pro
{
  private static Map<String, String> configMap;

  @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void init()
  {
    InputStream ins = Pro.class.getResourceAsStream("/props/conn.properties");
    Properties p = new Properties();
    Set entry = new HashSet();
    try {
      p.load(ins);
      entry = p.entrySet();
    } catch (IOException e) {
            System.out.println("" + e);
    }
    finally
    {
      try
      {
        ins.close();
      } catch (IOException e) {
                System.out.println(e);
      }
    }
    configMap = new HashMap();
    Iterator it = entry.iterator();
    while (it.hasNext()) {
      Map.Entry obj = (Map.Entry)it.next();
      String key = (String)obj.getKey();
      String value = (String)obj.getValue();
      if ((!"".equals(key)) && (key != null)) {
        configMap.put(key, value);
      }
    }
  }

  public static String getConfig(String key)
  {
    if (configMap.containsKey(key)) {
      return (String)configMap.get(key);
    }
    return null;
  }
}