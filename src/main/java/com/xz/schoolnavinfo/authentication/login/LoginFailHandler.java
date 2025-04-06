package com.xz.schoolnavinfo.authentication.login;

import com.xz.schoolnavinfo.common.data.Result;
import com.xz.schoolnavinfo.common.data.ResultBuilder;
import com.xz.schoolnavinfo.common.utils.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * AbstractAuthenticationProcessingFilter抛出AuthenticationException异常后，会跑到这里来
 */
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    String errorMessage = exception.getMessage();
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    PrintWriter writer = response.getWriter();
    Result responseData = ResultBuilder.aResult()
        .data(null)
        .code("fail")
        .msg(errorMessage)
        .build();
    writer.print(JSON.stringify(responseData));
    writer.flush();
    writer.close();
  }
}
