package com.auth0.exception.customexceptions;

import com.auth0.utils.ErrorMessage;
import lombok.*;

import java.util.List;
@Builder
@AllArgsConstructor
@Getter
@Setter
public class ValidationException extends Exception {
    private final transient List<ErrorMessage> errors;
}
