package cm.cn.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import cm.cn.po.HlCompanyuser;
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		// TODO Auto-generated method stub
		System.out.println(uri);
		System.out.println(" preHandle is running");
		if(uri.contains("companyUserLogin.action")||uri.contains("checkSession")){
        	return true;
        }
		HttpSession session = request.getSession();  
		HlCompanyuser jsUser = (HlCompanyuser)session.getAttribute("companyUser");  
          
        if(jsUser != null){  
            return true;  
        }
        //response.sendRedirect("login.html");
        request.getRequestDispatcher("checkSession.action").forward(request, response);
        //response.sendRedirect("login.html");
        //System.out.println("checkSession.action");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
