package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author krismile
 * @Create 2020-07-02 15:45
 */
@WebServlet("/user/*")
public class userModuleServlet extends BaseServlet {

    private UserService service = new UserServiceImpl();
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * 用户注册
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先验证 验证码是否正确
        String check = request.getParameter("check");
        //从session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        // 防止验证码二次利用
        session.removeAttribute("CHECKCODE_SERVER");

        //比较验证码是否匹配
        if(checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)){
            // 不匹配
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        // 获取数据，封装成User对象
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            // 将map中的数据 一一对应 封装到user中
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // 调用service层完成注册功能
//        UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);

        // 根据service层的返回信息，给与前端响应
        ResultInfo resultInfo = new ResultInfo();
        if(flag){
            // 注册成功
            resultInfo.setFlag(true);
        }else {
            // 注册失败
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败!");
        }
        // 将数据封装为json格式
//        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultInfo);
        // 设置响应头
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json); // 写回到前端
    }

    /**
     * 邮箱激活
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取激活code
        String code = request.getParameter("code");
        if(code!=null){
            // 调用service层 进行激活验证
//            UserService service = new UserServiceImpl();
            boolean flag = service.active(code);

            // 判断标记
            String msg = null;
            if(flag){
                //激活成功
                msg = "<h4>激活成功，请<a href='login.html'>登录</a></h4>";
            }else{
                //激活失败
                msg = "<h4>激活失败，请联系管理员!</h4>";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);

        }
    }

    /**
     * 用户登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先验证 验证码是否正确
        String check = request.getParameter("check");
        //从session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        // 防止验证码二次利用
        session.removeAttribute("CHECKCODE_SERVER");

        //比较验证码是否匹配
        if(checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)){
            // 不匹配
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json
//            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }


        // 获取用户登录数据
        Map<String, String[]> map = request.getParameterMap();
        // 封装数据
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 调用service层 校验是否可以登录
        UserService service = new UserServiceImpl();
        User u = service.login(user);

        // 根据返回数据，响应不同信息给前端
        ResultInfo info = new ResultInfo();
        // 未找到用户信息
        if(u==null){
            // 登录失败
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误!");
        }
        // 用户登录成功
        if(u!=null && "Y".equals(u.getStatus())){
            // 登录成功
            // 写入session中
            request.getSession().setAttribute("user",u);//登录成功标记
            info.setFlag(true);
        }
        // 用户未激活
        if(u!=null && !"Y".equals(u.getStatus())){
            // 登录失败
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请登录邮箱激活!");
        }

        // 将数据封装为json格式 设置响应头并写回
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 用户退出
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 销毁session数据
        request.getSession().invalidate();
        /**
         * 重定向到登录页面 重定向需要加虚拟目录，
         * 实际就是需要给出完整的路径，因为这种方式的跳转不仅仅在服务器内部，可能会跳到外部资源
         */
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 在session中找到当前登录的用户信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 读取session中数据
        Object user = request.getSession().getAttribute("user");
        // 返回数据 以jason格式返回
//        ObjectMapper mapper = new ObjectMapper();
        // 设置响应头
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), user);
    }

}
