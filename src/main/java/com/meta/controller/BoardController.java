package com.meta.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.config.jwt.JwtProperties;
import com.meta.config.principal.PrincipalDetails;
import com.meta.dto.board.req.BoardSaveReqDto;
import com.meta.dto.board.req.BoardUpdReqDto;
import com.meta.entity.BoardEntity;
import com.meta.response.PagingResDto;
import com.meta.response.ResponseDto;
import com.meta.response.ResponseService;
import com.meta.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
	
	private final ResponseService responseService;
	
	private final BoardService boardService;

	@PostMapping("/freeboard")
	public ResponseEntity<ResponseDto> saveBoard(@Validated @RequestBody BoardSaveReqDto regData, @AuthenticationPrincipal PrincipalDetails principalDetails) {		
		boardService.saveBoard(regData, principalDetails);
		HttpHeaders headers= new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(responseService.getSuccessResult());
	}
	
	@GetMapping("/freeboards")
	public ResponseEntity<PagingResDto> getBoardList(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		PagingResDto resData = boardService.getBoardList(pageable);
		HttpHeaders headers= new HttpHeaders();	
		return ResponseEntity.ok().headers(headers).body(resData);
	}
	
	/*
	@GetMapping("/freeboards")
	public ResponseEntity<List<BoardEntity>> getBoardList() {
		List<BoardEntity> resData = boardService.getBoardList();
		HttpHeaders headers= new HttpHeaders();	
		return ResponseEntity.ok().headers(headers).body(resData);
	}
	*/
	
	@GetMapping("/freeboards/{id}")
	public ResponseEntity<BoardEntity> getBoard(@PathVariable long id) {
		BoardEntity resData = boardService.getBoard(id);		
		HttpHeaders headers= new HttpHeaders();	
		return ResponseEntity.ok().headers(headers).body(resData);
	}
	
	@PutMapping("/freeboards/{boardId}")
	public ResponseEntity<ResponseDto> updateBoard(@Validated @RequestBody BoardUpdReqDto regData, @PathVariable long boardId, @AuthenticationPrincipal PrincipalDetails principalDetails) {		
		boardService.updateBoard(boardId, regData, principalDetails);
		HttpHeaders headers= new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(responseService.getSuccessResult());
	}
	
	@DeleteMapping("/freeboards/{boardId}")
	public ResponseEntity<ResponseDto> deleteBoard(@PathVariable long boardId, @AuthenticationPrincipal PrincipalDetails principalDetails) {		
		boardService.deleteBoard(boardId, principalDetails);
		HttpHeaders headers= new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(responseService.getSuccessResult());
	}	
	
}
