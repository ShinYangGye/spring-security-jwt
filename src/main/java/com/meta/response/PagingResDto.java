package com.meta.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingResDto<T> {
	private T content;
	
	private long pageNo;
	private long pageSize;
	private long startPage;
	private long endPage;
	private long totalCount;
	private long totalPage;
}
