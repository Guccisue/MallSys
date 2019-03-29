package com.jjn.mall.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jjn.mall.user.dao.pojo.TAuthInfo;
import com.jjn.mall.user.model.UserModel;

public class opFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		 HttpServletRequest request = (HttpServletRequest) arg0;  
         HttpServletResponse response = (HttpServletResponse) arg1;  
         
         if(request.getRequestURI().indexOf("login") != -1 || request.getRequestURI().indexOf("/userLogin") != -1
        		 || request.getRequestURI().indexOf("/index.jsp") != -1) {  
             arg2.doFilter(arg0, arg1);  
             return;  
         }  

         if(request.getSession().getAttribute("USER_MODEL") == null) {  
        	 response.sendRedirect(request.getContextPath() + "/login.html");  
         } else {
        	 UserModel um = new UserModel();
        	 um = (UserModel) request.getSession().getAttribute("USER_MODEL");
        	 List<TAuthInfo> authList = um.getAuthInfoList();
        	 boolean canOp = false;
        	 for(TAuthInfo tai : authList) {
        		 if(request.getRequestURI().indexOf(tai.getUrl()) > -1) {
        			 canOp = true;
        			 break;
        		 }
        	 }
        	 if(canOp) {
        		 arg2.doFilter(arg0, arg1);  
        	 } else {
        		 response.sendRedirect(request.getContextPath() + "/login.html"); 
        	 }
         }  
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
