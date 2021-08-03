package com.ssafy.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.NoticeRegisterPostReq;
import com.ssafy.db.entity.Notice;
import com.ssafy.db.repository.NoticeRepository;
import com.ssafy.db.repository.NoticeRepositorySupport;

/**
 * Notice 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	NoticeRepository noticeRepository;
	
	@Autowired
	NoticeRepositorySupport noticeRepositorySupport;
	
	@Override
	public Notice createNotice(NoticeRegisterPostReq noticeRegisterInfo) {
		Notice notice = new Notice();
		notice.setNoticeTitle(noticeRegisterInfo.getNoticeTitle());
		notice.setNoticeContent(noticeRegisterInfo.getNoticeContent());
		return noticeRepository.save(notice);
	}

	@Override
	public Page<Notice> selectNotice(Pageable pageable) {
		return noticeRepository.findAll(pageable);
	}

	@Override
	public Notice getInfoByNoticeNo(int noticeNo) {
		Notice notice = noticeRepositorySupport.findByNoticeNo(noticeNo).get();
		return notice;
	}

	@Override
	public Notice modifyNotice(NoticeRegisterPostReq noticeModifyInfo, int noticeNo) {
		Notice notice = noticeRepositorySupport.findByNoticeNo(noticeNo).get();
		notice.setNoticeTitle(noticeModifyInfo.getNoticeTitle());
		notice.setNoticeContent(noticeModifyInfo.getNoticeContent());
		return noticeRepository.save(notice);
	}

	@Override
	public void deleteNotice(int noticeNo) {
		Notice notice = noticeRepositorySupport.findByNoticeNo(noticeNo).get();
		noticeRepository.delete(notice);
	}
}
