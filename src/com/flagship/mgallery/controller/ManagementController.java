package com.flagship.mgallery.controller;

import com.flagship.mgallery.entity.Painting;
import com.flagship.mgallery.service.PaintingService;
import com.flagship.mgallery.utils.PageModel;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Author Flagship
 * @Date 2020/12/5 16:42
 * @Description
 */
@WebServlet(name = "ManagementController", value = "/management")
public class ManagementController extends HttpServlet {
    private PaintingService paintingService = new PaintingService();

    public ManagementController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String method = request.getParameter("method");
        if ("list".equals(method)) {
            this.list(request, response);
        } else if("delete".equals(method)) {
            this.delete(request, response);
        } else if ("showCreatePage".equals(method)){
            this.showCreatePage(request, response);
        } else if("create".equals(method)) {
            this.create(request, response);
        } else if("showUpdatePage".equals(method)) {
            this.showUpdatePage(request, response);
        } else if("update".equals(method)) {
            this.update(request, response);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        try {
            paintingService.delete(Integer.parseInt(id));
            response.getWriter().println("{\"result\":\"ok\"}");
        } catch (Exception e) {
            response.getWriter().println("{\"result\":\""+e.getMessage()+"\"}");
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sf = new ServletFileUpload(factory);
        int isPreviewModified = 0;
        try {
            List<FileItem> formData = sf.parseRequest(request);
            Painting painting = new Painting();
            for (FileItem fileItem : formData) {
                if (fileItem.isFormField()) {
                    System.out.println(fileItem.getFieldName() + ":" + fileItem.getString("UTF-8"));
                    switch (fileItem.getFieldName()) {
                        case "id":
                            painting.setId(Integer.parseInt(fileItem.getString("UTF-8")));
                            break;
                        case "pname":
                            painting.setPname(fileItem.getString("UTF-8"));
                            break;
                        case "category":
                            painting.setCategory(Integer.parseInt(fileItem.getString("UTF-8")));
                            break;
                        case "price":
                            painting.setPrice(Integer.parseInt(fileItem.getString("UTF-8")));
                            break;
                        case "description":
                            painting.setDescription(fileItem.getString("UTF-8"));
                            break;
                        case "isPreviewModified":
                            isPreviewModified = Integer.parseInt(fileItem.getString("UTF-8"));
                            break;
                        default:
                            break;
                    }
                } else {
                    if (fileItem.getName() != null && fileItem.getName().length() > 3) {
                        String path = request.getServletContext().getRealPath("/upload");
                        String fileName = UUID.randomUUID().toString();
                        String suffix = fileItem.getName().substring(fileItem.getName().lastIndexOf("."));
                        fileItem.write(new File(path, fileName + suffix));
                        painting.setPreview("/upload/" + fileName + suffix);
                    }
                }
            }
            System.out.println();
            System.out.println("id:"+painting.getId());
            System.out.println("pname:"+painting.getPname());
            System.out.println("category:"+painting.getCategory());
            System.out.println("price:"+painting.getPrice());
            System.out.println("preview:"+painting.getPreview());
            System.out.println("description:"+painting.getDescription());
            paintingService.update(painting, isPreviewModified);
            response.sendRedirect("/management?method=list");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showUpdatePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Painting painting = paintingService.findById(Integer.parseInt(id));
        request.setAttribute("painting", painting);
        request.getRequestDispatcher("/WEB-INF/jsp/update.jsp").forward(request, response);
    }

    private void create(HttpServletRequest request, HttpServletResponse response) {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sf = new ServletFileUpload(factory);
        try {
            List<FileItem> formData = sf.parseRequest(request);
            Painting painting = new Painting();
            for (FileItem fileItem : formData) {
                if (fileItem.isFormField()) {
                    switch (fileItem.getFieldName()) {
                        case "pname":
                            painting.setPname(fileItem.getString("UTF-8"));
                            break;
                        case "category":
                            painting.setCategory(Integer.parseInt(fileItem.getString("UTF-8")));
                            break;
                        case "price":
                            painting.setPrice(Integer.parseInt(fileItem.getString("UTF-8")));
                            break;
                        case "description":
                            painting.setDescription(fileItem.getString("UTF-8"));
                            break;
                        default:
                            break;
                    }
                } else {
                    String path = request.getServletContext().getRealPath("/upload");
                    String fileName = UUID.randomUUID().toString();
                    String suffix = fileItem.getName().substring(fileItem.getName().lastIndexOf("."));
                    fileItem.write(new File(path, fileName + suffix));
                    painting.setPreview("/upload/" + fileName + suffix);
                }
            }
            paintingService.create(painting);
            response.sendRedirect("/management?method=list");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showCreatePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/create.jsp").forward(request, response);
    }

    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if (page == null) {
            page = "1";
        }
        if (rows == null) {
            rows = "6";
        }
        PageModel pageModel = paintingService.pagination(Integer.parseInt(page), Integer.parseInt(rows));
        request.setAttribute("pageModel", pageModel);
        request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
    }
}
