package com.tweetapp.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponse {
    private LocalDateTime timestamp = LocalDateTime.now();
    @NonNull
    private String message;
    @NonNull
    private List<String> details;
}
