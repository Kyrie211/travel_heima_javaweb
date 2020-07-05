package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 分类模块Servlet
 * @author krismile
 * @Create 2020-07-02 20:14
 */
@WebServlet("/category/*")
public class categoryModuleServlet extends BaseServlet {
    private CategoryService service = new CategoryServiceImpl();

    /**
     * 查询分类栏数据 cid & cname
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 调用service成完成查询工作
        List<Category> cs = service.findAll();
        // 以json方式写回
        writeValue(cs, resp); // 父类BaseServlet封装的方法
    }

}
