package com.flagship.mgallery.controller;

import com.flagship.mgallery.service.PaintingService;
import com.flagship.mgallery.utils.PageModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Flagship
 * @Date 2020/11/30 22:35
 * @Description
 */
@WebServlet(name = "PaintingController", value = "/page")
public class PaintingController extends HttpServlet {
    private PaintingService paintingService = new PaintingService();
    public PaintingController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收Http数据
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String category = request.getParameter("category");

        page = page == null ? "1" : page;
        rows = rows == null ? "6" : rows;

        //2.调用service方法，得到处理结果
        PageModel pageModel = paintingService.pagination(Integer.parseInt(page), Integer.parseInt(rows), category);
        request.setAttribute("pageModel", pageModel);
        //3.请求转发到对应JSP（view）进行数据展示
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
    }
}
