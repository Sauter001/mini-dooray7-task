package com.nhnacademy.taskapi.filter;

import com.nhnacademy.taskapi.entity.Account;
import com.nhnacademy.taskapi.repository.AccountRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccountCheckFilter implements Filter {

    private final AccountRepository accountRepository;

    public AccountCheckFilter(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String userIdHeader = httpRequest.getHeader("X-USER-ID");

        if (userIdHeader != null) {
            Long accountId = Long.valueOf(userIdHeader);

            // AccountRepository를 사용하여 계정 존재 여부 확인
            if (!accountRepository.existsById(accountId)) {
                // 계정이 없으면 새로 추가
                Account newAccount = new Account();
                newAccount.setId(accountId);
                accountRepository.save(newAccount);
            }
        }
        chain.doFilter(request, response);
    }

}
