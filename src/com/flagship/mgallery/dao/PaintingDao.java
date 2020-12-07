package com.flagship.mgallery.dao;

import com.flagship.mgallery.entity.Painting;
import com.flagship.mgallery.utils.PageModel;
import com.flagship.mgallery.utils.XmlDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Flagship
 * @Date 2020/11/30 22:25
 * @Description
 */
public class PaintingDao {
    public PageModel pagination(int page, int rows) {
        List<Painting> list = XmlDataSource.getRawData();
        return new PageModel(list, page, rows);
    }

    public PageModel pagination(int category, int page, int rows) {
        List<Painting> list = XmlDataSource.getRawData();
        List<Painting> categoryList = new ArrayList<Painting>();
        for (Painting p : list) {
            if (p.getCategory() == category) {
                categoryList.add(p);
            }
        }
        return new PageModel(categoryList, page, rows);
    }

    public void create(Painting painting) {
        XmlDataSource.append(painting);
    }

    public Painting findById(Integer id) {
        List<Painting> data = XmlDataSource.getRawData();
        Painting painting = null;
        for (Painting p : data) {
            if (p.getId().equals(id)) {
                painting = p;
                break;
            }
        }
        return painting;
    }

    public void update(Painting painting) {
        XmlDataSource.update(painting);
    }

    public void delete(int id) {
        XmlDataSource.delete(id);
    }
}
