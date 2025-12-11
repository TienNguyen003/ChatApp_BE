package com.livestream.DTO.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.livestream.Entity.PageCustom;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse <T>{
	@Builder.Default
	int code = 303;
	String message;
	T result;
	PageCustom page;
}
