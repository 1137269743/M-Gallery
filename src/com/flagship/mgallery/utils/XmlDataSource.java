package com.flagship.mgallery.utils;

import com.flagship.mgallery.entity.Painting;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Flagship
 * @Date 2020/11/30 21:29
 * @Description
 */
public class XmlDataSource {
    /**
     * 使用static静态关键字保证数据全局唯一
     */
    private static List<Painting> data = new ArrayList<Painting>();
    private static String dataFile;
    static {
        //得到painting.xml文件完整物理路径
        dataFile = XmlDataSource.class.getResource("/painting.xml").getPath();
        reload();
    }

    private static void reload() {
        try {
            dataFile = URLDecoder.decode(dataFile, "UTF-8");
            System.out.println(dataFile);
            //利用Dom4j对XML进行解析
            SAXReader reader = new SAXReader();
            //1.获取Document文档对象
            Document document = reader.read(dataFile);
            //2.Xpath得到XML节点集合
            List<Node> nodes = document.selectNodes("/root/painting");
            data.clear();
            for (Node node : nodes) {
                Element element = (Element)node;

                Painting painting = new Painting();
                painting.setId(Integer.parseInt(element.attributeValue("id")));
                painting.setPname(element.elementText("pname"));
                painting.setCategory(Integer.parseInt(element.elementText("category")));
                painting.setPrice(Integer.parseInt(element.elementText("price")));
                painting.setPreview(element.elementText("preview"));
                painting.setDescription(element.elementText("description"));

                data.add(painting);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Painting> getRawData() {
        return data;
    }

    public static void append(Painting painting) {
        SAXReader reader = new SAXReader();
        Writer writer = null;
        try {
            Document document = reader.read(dataFile);

            Element root = document.getRootElement();
            Element p = root.addElement("painting");

            p.addAttribute("id", String.valueOf(data.size() + 1));
            p.addElement("pname").setText(painting.getPname());
            p.addElement("category").setText(painting.getCategory().toString());
            p.addElement("price").setText(painting.getPrice().toString());
            p.addElement("preview").setText(painting.getPreview());
            p.addElement("description").setText(painting.getDescription());

            writer = new OutputStreamWriter(new FileOutputStream(dataFile), StandardCharsets.UTF_8);
            document.write(writer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            reload();
        }
    }

    public static void update(Painting painting) {
        SAXReader reader = new SAXReader();
        Writer writer = null;
        try {
            Document document = reader.read(dataFile);
            List<Node> nodes = document.selectNodes("/root/painting[@id=" + painting.getId() + "]");
            if (nodes.size() == 0) {
                throw new RuntimeException("id=" + painting.getId() + "编号油画不存在");
            }
            Element element = (Element)nodes.get(0);
            element.selectSingleNode("pname").setText(painting.getPname());
            element.selectSingleNode("category").setText(painting.getCategory().toString());
            element.selectSingleNode("price").setText(painting.getPrice().toString());
            element.selectSingleNode("preview").setText(painting.getPreview());
            element.selectSingleNode("description").setText(painting.getDescription());
            writer = new OutputStreamWriter(new FileOutputStream(dataFile), StandardCharsets.UTF_8);
            document.write(writer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            reload();
        }
    }

    public static void main(String[] args) {
        Painting painting =new Painting();
        painting.setPname("测试油画");
        painting.setCategory(1);
        painting.setPrice(4000);
        painting.setPreview("/upload/10.jpg");
        painting.setDescription("测试油画描述");
        XmlDataSource.append(painting);
    }

    public static void delete(int id) {
        SAXReader reader = new SAXReader();
        Writer writer = null;
        try {
            Document document = reader.read(dataFile);
            System.out.println(dataFile);
            //节点路径[@属性名=属性值]
            List<Node> nodes = document.selectNodes("/root/painting[@id="+id+"]");
            if(nodes.size() == 0) {
                throw new RuntimeException("id="+id+"编号油画不存在");
            }
            Element p = (Element) nodes.get(0);

            Element parent = p.getParent();
            parent.remove(p);

            writer = new OutputStreamWriter(new FileOutputStream(dataFile), StandardCharsets.UTF_8);
            document.write(writer);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            reload();
        }
    }
}
