package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 返回信息工作类
 */
public class HttpServletUtil {
    private static JwtUtil jwtUtil = new JwtUtil();

    /**
     * 发送json响应信息
     *
     * @param response
     * @param obj      JavaBean
     * @throws IOException
     */
    public static void sendJsonInfo(HttpServletResponse response, Object obj) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.write(mapper.writeValueAsString(obj));
        out.flush();
        out.close();
    }

    public static String getUserIdFromRequest(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("user_claims");
        String mobile = claims.get("mobile").toString();
        return mobile;
    }

}
