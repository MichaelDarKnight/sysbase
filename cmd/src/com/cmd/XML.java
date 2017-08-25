package com.cmd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XML {
    public static boolean wtite() {
        try {
            Document document = DocumentHelper.createDocument();
            document.setXMLEncoding("UTF-8");
            Element list = document.addElement("root");
            list.addElement("a1").addAttribute("ttt", "ffffffffff").addText("11111");
            list.addElement("b2").addText("斤斤计较");
            list.addElement("c3").addText("hhh");
            list.addElement("d4").addCDATA("$%^$%^&%*&^");
            list.addElement("e5").addText("哈哈哈");
            list.addElement("f6").addText("IOUJJLK");
            OutputXml(document);

        } catch (Exception e) {
            System.out.println("xml文件生成错误！");
            e.printStackTrace();
        }
        return true;
    }

    public static void StringToXml() {
        String text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><root><root><root><root><root><root><root>1111111</root></root></root></root></root></root></root></root>";
        try {
            Document document = DocumentHelper.parseText(text);
            System.out.println(document.asXML().toString());
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void InputXml() {
        javax.swing.filechooser.FileSystemView fsv = javax.swing.filechooser.FileSystemView.getFileSystemView();
        String desktop = fsv.getHomeDirectory().toString();
        SAXReader rd = new SAXReader();
        try {
            Document document = rd.read(new File("D:/1" + "/ttt.xml"));
            System.out.println(document.asXML().toString());
            Element root = document.getRootElement();
            List<Element> list = root.elements();
            for (Element e : list) {
                System.out.println(e.getName() + e.getText() + e.attributeValue("ttt"));
            }
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //最基本的文件读取
        /*
         * try { FileInputStream is=new FileInputStream(desktop+"/ttt.xml");
         * BufferedReader bufferedReader = new BufferedReader(new
         * InputStreamReader(is)); String line =""; while ((line =
         * bufferedReader.readLine()) != null) { System.out.println(line); } }
         * catch (FileNotFoundException e) { // TODO Auto-generated catch block
         * e.printStackTrace(); } //format.setEncoding("UTF-8");//
         * 设置XML文件的编码格式,如果有中文可设置为GBK或UTF-8 //创建的文件，已存在也行 catch (IOException e) {
         * // TODO Auto-generated catch block e.printStackTrace(); }
         */

    }

    public static void OutputXml(Document doc) {
        //这是最常用的获取桌面路径 -2017年3月25日 10:54:30
        javax.swing.filechooser.FileSystemView fsv = javax.swing.filechooser.FileSystemView.getFileSystemView();
        String desktop = fsv.getHomeDirectory().toString();
        XMLWriter writer = null;
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");// 设置XML文件的编码格式,如果有中文可设置为GBK或UTF-8  
        //创建的文件，已存在也行
        File file1 = new File("D:/2/2/");
        if (!file1.exists()) {
            file1.mkdirs();
        }
        UUID uuid = UUID.randomUUID();
        File file = new File(file1, "2222.xml");
        //如果读取的内容中没有中文,可以使用以下的几行代码生成xml  
        //  
        //  try {  
        //   writer = new XMLWriter(new FileWriter(file), format);  
        //  } catch (IOException e1) {  
        //   e1.printStackTrace();  
        //  }  
        // 如果上面设置的xml编码类型为GBK，或设为UTF-8但其中有中文则应当用FileWriter来构建xml文件（使用以下代码），否则会出现中文乱码问题  
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            writer = new XMLWriter(fos, format);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //这里就开始写文件了
        try {
            writer.write(doc);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws DocumentException {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("request");
        Element root1 = root.addElement("sqlist");
        // root1.addElement("sqbh").addText("2222");
        String s = doc.asXML();
        Document doc1 = DocumentHelper.parseText(s);
        Element root2 = doc1.getRootElement();
        Element sqbhs = root2.element("sqlist");
        System.out.println(sqbhs.elements("sqbh") == null || sqbhs.elements("sqbh").size() == 0);
        // wtite();
        // InputXml();
        //StringToXml();
    }
}