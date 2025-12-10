package com.company.eduboard.global.error;

import com.company.eduboard.global.error.exception.BoardNotFoundException;
import com.company.eduboard.global.error.exception.CommentNotFoundException;
import com.company.eduboard.global.error.exception.DuplicateEmailException;
import com.company.eduboard.global.error.exception.InvalidPasswordException;
import com.company.eduboard.global.error.exception.UnauthorizedActionException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. 개별 예외 처리
    @ExceptionHandler(DuplicateEmailException.class)
    public String handleDuplicateEmailException(DuplicateEmailException ex,
                                                RedirectAttributes redirectAttributes) {

        log.warn("[DuplicateEmailException] {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());

        return "redirect:/users/register";
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public String handleInvalidPasswordException(InvalidPasswordException ex,
                                                 RedirectAttributes redirectAttributes) {

        log.warn("[InvalidPasswordException] {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());

        return "redirect:/users/login";
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public String handleBoardNotFoundException(BoardNotFoundException ex,
                                               RedirectAttributes redirectAttributes) {

        log.warn("[BoardNotFoundException] {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());

        return "redirect:/boards";
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public String handleCommentNotFoundException(CommentNotFoundException ex,
                                                 RedirectAttributes redirectAttributes) {

        log.warn("[CommentNotFoundException] {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());

        return "redirect:/boards";
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public String handleUnauthorizedActionException(UnauthorizedActionException ex,
                                                    RedirectAttributes redirectAttributes) {

        log.warn("[UnauthorizedActionException] {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());

        return "redirect:/";
    }

    // 2. 도메인/비즈니스 예외 (AppException + ErrorCode 기반)

    /**
     * 도메인/비즈니스 예외 처리 (문서, 게시글, 유저 등)
     * - ErrorCode 기준으로 HTTP 상태코드 설정
     * - 공통 에러 페이지 렌더링
     */
    @ExceptionHandler(AppException.class)
    public String handleAppException(AppException ex,
                                     Model model,
                                     HttpServletResponse response) {

        ErrorCode errorCode = ex.getErrorCode();

        response.setStatus(errorCode.getStatus().value());

        log.warn("[AppException] code={}, status={}, message={}",
                errorCode.getCode(), errorCode.getStatus(), ex.getMessage());

        model.addAttribute("errorCode", errorCode.getCode());
        model.addAttribute("status", errorCode.getStatus().value());
        model.addAttribute("message", ex.getMessage());

        // Thymeleaf 공통 에러 페이지
        return "error/custom-error";
    }

    // 3. Spring Security AccessDenied (로그인은 했으나 권한 없음)
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException ex,
                                     Model model,
                                     HttpServletResponse response) {

        ErrorCode errorCode = ErrorCode.AUTH_FORBIDDEN;
        response.setStatus(errorCode.getStatus().value());

        log.warn("[AccessDeniedException] {}", ex.getMessage());

        model.addAttribute("errorCode", errorCode.getCode());
        model.addAttribute("status", errorCode.getStatus().value());
        model.addAttribute("message", errorCode.getMessage());

        return "error/custom-error";
    }

    // 4. 그 외 예상하지 못한 모든 예외 (500)
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex,
                                  Model model,
                                  HttpServletResponse response) {

        ErrorCode errorCode = ErrorCode.SERVER_ERROR;
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        log.error("[UnexpectedException]", ex);

        model.addAttribute("errorCode", errorCode.getCode());
        model.addAttribute("status", errorCode.getStatus().value());
        model.addAttribute("message", errorCode.getMessage());

        return "error/custom-error";
    }
}
