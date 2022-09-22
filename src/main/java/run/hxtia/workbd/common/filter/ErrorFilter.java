package run.hxtia.workbd.common.filter;

import run.hxtia.workbd.common.util.Constants;

import javax.servlet.*;
import java.io.IOException;

/**
 * 将没有到达 Controller是出现的异常转交给 Controller处理
 */
public class ErrorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            String errorUri = Constants.Web.ERROR_URI;
            request.setAttribute(errorUri, e);
            request.getRequestDispatcher(errorUri).forward(request, response);
        }
    }
}
