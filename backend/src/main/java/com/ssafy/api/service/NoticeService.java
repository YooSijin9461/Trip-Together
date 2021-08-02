package com.ssafy.api.service;

import java.util.List;

import com.ssafy.api.request.NoticeRegisterPostReq;
import com.ssafy.db.entity.Notice;

/**
 *	공지사항 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface NoticeService {
	// CRUD - READ는 전체 공지사항, 공지사항번호에 따른 상세 공지사항 정보
	Notice createNotice(NoticeRegisterPostReq noticeRegisterInfo);
	List<Notice> selectNotice();
	Notice getInfoByNoticeNo(int noticeNo);
	Notice modifyNotice(NoticeRegisterPostReq noticeModifyInfo, int noticeNo);
	void deleteNotice(int noticeNo);
}
