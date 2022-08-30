package com.meta.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.advice.exception.CPartCodeNotExistException;
import com.meta.advice.exception.CUserNotExistException;
import com.meta.config.principal.PrincipalDetails;
import com.meta.dto.board.req.BoardSaveReqDto;
import com.meta.dto.board.req.BoardUpdReqDto;
import com.meta.entity.BoardEntity;
import com.meta.repository.BoardRepository;
import com.meta.response.PagingResDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	public void saveBoard(BoardSaveReqDto reqData, PrincipalDetails principalDetails) {
		
		long loginId = principalDetails.getUserEntity().getId();
		
		BoardEntity boardEntity = BoardEntity.builder()
				.category(reqData.getCategory())
				.title(reqData.getTitle())
				.contents(reqData.getContents())
				.regId(loginId)
				.updId(loginId)
				.build();
		
		boardRepository.save(boardEntity);
	}
	
	public PagingResDto getBoardList(Pageable pageable) {
		
		Page<BoardEntity> page = boardRepository.findAll(pageable);
		
		log.info("###########################, {}", page.getTotalPages());
		
		long pageNo = page.getNumber();
		long pageSize = page.getSize();
		long totalCount = page.getTotalElements();
		long totalPage = page.getTotalPages();
		// long totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
		// int offSet = pageNo * pageSize;
		
		long startPage = Math.max(1, pageNo - 4);
		long endPage = (int)Math.min(totalPage, pageNo + 5);		
		
		PagingResDto<?> pagingResDto = PagingResDto.builder()
				.content(page.getContent())					
				.pageNo(pageNo)
				.pageSize(pageSize)
				.startPage(startPage)
				.endPage(endPage)
				.totalCount(totalCount)
				.totalPage(totalPage)
				.build();
		
		return pagingResDto;
	}
	
	public BoardEntity getBoard(long id) {
		return boardRepository.findById(id).orElseThrow();
	}
	
	@Transactional
	public void updateBoard(long boardId, BoardUpdReqDto reqData, PrincipalDetails principalDetails) {		
				
		BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow();
		
		checkBoardAuth(boardId, principalDetails);
		
		boardEntity.setTitle(reqData.getTitle());		
		boardEntity.setContents(reqData.getContents());
	}
	
	public void deleteBoard(long boardId, PrincipalDetails principalDetails) {
		
		checkBoardAuth(boardId, principalDetails);	
		
		boardRepository.deleteById(boardId);
	}
	
	private boolean checkBoardAuth(long boardId, PrincipalDetails principalDetails) {
		
		if ("ROLE_ADMIN".equals(principalDetails.getUserEntity().getRole())) {
			return true;
		}
		
		BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow();
		
		long loginId = principalDetails.getUserEntity().getId();
		long boardRegId = boardEntity.getRegId();
		
		if (loginId != boardRegId) {		
			throw new AccessDeniedException("해당글에 접근 권한이 없습니다.");
		}
		
		return true;
		
	}

}
