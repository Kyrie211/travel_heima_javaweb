package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author krismile
 * @Create 2020-07-02 15:45
 */

public class BaseServlet extends HttpServlet {
    /**
     * 原理：
     * 在Servlet中，每次给服务器发送响应，都会自动调用Servlet的service方法
     * 基类Servlet 通过继承该类的子类，将会自动调用其父类（也就是该类）的service方法，因为子类并不会重写该方法
     *
     * 实现的功能:
     * 是根据uri中的字符串，找到需要调用的方法 如 /user/login -> 该类的service方法会实现对子类login方法的调用
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 完成方法的分发 反射的技术
        // 1. 获取请求的路径
        String uri = req.getRequestURI(); // 格式：/项目的虚拟目录/user/XXX 我们需要的是XXX
        // 2. 获取方法的名称
        String methodName = uri.substring(uri.lastIndexOf('/')+1);
        // 3. 获取方法调用的对象
        // 调用该方法的对象是 BaseServlet的子类 谁此刻调用了 谁就是方法的调用者
        // 4. 获取方法对象
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 5. 反射执行该方法
            method.invoke(this, req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将obj对象 以json方式写回前端页面（ajax异步请求）
     * @param obj
     * @param response
     */
    public void writeValue(Object obj, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // 设置响应头
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), obj);
    }

    /**
     * 将obj对象转为json格式数据
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
