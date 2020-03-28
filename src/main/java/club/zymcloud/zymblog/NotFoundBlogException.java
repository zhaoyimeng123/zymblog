package club.zymcloud.zymblog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author
 * @date 2020/3/23-23:53
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundBlogException extends RuntimeException {
    public NotFoundBlogException() {
    }

    public NotFoundBlogException(String message) {
        super(message);
    }

    public NotFoundBlogException(String message, Throwable cause) {
        super(message, cause);
    }
}
