package com.livestream.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
	UNCATEGORIZED_EXCEPTION(504, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
	INVALID(504, "Invalid message key", HttpStatus.BAD_REQUEST),
	UNAUTHENTICATED(504, "Unauthenticated", HttpStatus.UNAUTHORIZED),
	UNAUTHORIZED(505, "You don't have permission", HttpStatus.FORBIDDEN),

	// username
	USERNAME_INVALID(500, "Tên đăng nhập phải có ít nhất 6 kí tự.", HttpStatus.BAD_REQUEST),
	USERNAME_NOT_EXISTED(504, "Tên đăng nhập không chính xác.", HttpStatus.NOT_FOUND),
	USER_NOT_EXISTED(504, "Tài khoản không chính xác.", HttpStatus.NOT_FOUND),
	USER_NOT_ACTIVE(503, "Tài khoản đã bị khóa.", HttpStatus.NOT_FOUND),
	USERNAME_EXISTED(502, "Tên đăng nhập đã tồn tại.", HttpStatus.BAD_REQUEST),

	// password
	PASSWORD_INVALID(500, "Mật khẩu phải có ít nhất 6 kí tự", HttpStatus.BAD_REQUEST),
	PASSWORD_NO_INCORRECT(500, "Thông tin không chính xác. Vui lòng thử lại.", HttpStatus.NOT_FOUND),
	PASSWORD_NO_MATCH(500, "Mật khẩu mới không được trùng mật khẩu cũ.", HttpStatus.NOT_FOUND),
	OLD_PASS_INCORRECT(500, "Mật khẩu cũ không chính xác. Vui lòng thử lại.", HttpStatus.NOT_FOUND),

	// quyền
	ROLE_EXISTED(502, "Quyền đã tồn tại.", HttpStatus.BAD_REQUEST),
	ROLE_NOT_EXISTED(504, "Quyền không tồn tại.", HttpStatus.BAD_REQUEST),

	// channel
	CHANNEL_NOT_EXISTED(504, "Kênh không tồn tại.", HttpStatus.NOT_FOUND),
	CHANNEL_ALREADY_EXISTED(502, "Người dùng đã có kênh.", HttpStatus.BAD_REQUEST),

	// category
	CATEGORY_NOT_EXISTED(504, "Danh mục không tồn tại.", HttpStatus.NOT_FOUND),
	CATEGORY_EXISTED(502, "Danh mục đã tồn tại.", HttpStatus.BAD_REQUEST),

	// livestream
	LIVESTREAM_NOT_EXISTED(504, "Livestream không tồn tại.", HttpStatus.NOT_FOUND),

	// video
	VIDEO_NOT_EXISTED(504, "Video không tồn tại.", HttpStatus.NOT_FOUND),

	// clip
	CLIP_NOT_EXISTED(504, "Clip không tồn tại.", HttpStatus.NOT_FOUND),

	// gift
	GIFT_NOT_EXISTED(504, "Quà tặng không tồn tại.", HttpStatus.NOT_FOUND),

	// follower
	ALREADY_FOLLOWING(502, "Đã theo dõi kênh này.", HttpStatus.BAD_REQUEST),
	NOT_FOLLOWING(504, "Chưa theo dõi kênh này.", HttpStatus.NOT_FOUND),

	// subscription
	SUBSCRIPTION_NOT_EXISTED(504, "Không tìm thấy đăng ký.", HttpStatus.NOT_FOUND),
	ALREADY_SUBSCRIBED(502, "Bạn đã đăng ký gói %s của kênh %s.", HttpStatus.BAD_REQUEST),

	// badge
	BADGE_NOT_EXISTED(504, "Huy hiệu không tồn tại.", HttpStatus.NOT_FOUND),
	BADGE_EXISTED(502, "Huy hiệu đã tồn tại.", HttpStatus.BAD_REQUEST),

	// notification
	NOTIFICATION_NOT_EXISTED(504, "Thông báo không tồn tại.", HttpStatus.NOT_FOUND),

	// report
	REPORT_NOT_EXISTED(504, "Báo cáo không tồn tại.", HttpStatus.NOT_FOUND),

	// moderator
	MODERATOR_NOT_EXISTED(504, "Người kiểm duyệt không tồn tại.", HttpStatus.NOT_FOUND),
	ALREADY_MODERATOR(502, "Người dùng đã là kiểm duyệt viên.", HttpStatus.BAD_REQUEST),

	// reaction
	REACTION_NOT_EXISTED(504, "Reaction không tồn tại.", HttpStatus.NOT_FOUND),

	// watch history
	WATCH_HISTORY_NOT_EXISTED(504, "Lịch sử xem không tồn tại.", HttpStatus.NOT_FOUND),

	// stream key
	INVALID_STREAM_KEY(504, "Stream key không hợp lệ.", HttpStatus.UNAUTHORIZED),

	// tag
	TAG_NOT_EXISTED(504, "Tag không tồn tại.", HttpStatus.NOT_FOUND),
	TAG_EXISTED(502, "Tag đã tồn tại.", HttpStatus.BAD_REQUEST),

	// ban
	USER_BANNED(403, "Bạn đã bị cấm khỏi kênh này.", HttpStatus.FORBIDDEN),
	USER_ALREADY_BANNED(502, "Người dùng đã bị cấm.", HttpStatus.BAD_REQUEST),
	BAN_NOT_FOUND(504, "Không tìm thấy lệnh cấm.", HttpStatus.NOT_FOUND),
	INVALID_BAN_EXPIRY(400, "Cấm tạm thời phải có thời gian hết hạn.", HttpStatus.BAD_REQUEST),

	// playlist
	PLAYLIST_NOT_FOUND(504, "Playlist không tồn tại.", HttpStatus.NOT_FOUND),
	VIDEO_ALREADY_IN_PLAYLIST(502, "Video đã có trong playlist.", HttpStatus.BAD_REQUEST),

	// chat restrictions
	SLOW_MODE_ACTIVE(429, "Bạn đang gửi tin nhắn quá nhanh.", HttpStatus.TOO_MANY_REQUESTS),
	FOLLOWERS_ONLY_MODE(403, "Chỉ người theo dõi mới được chat.", HttpStatus.FORBIDDEN),
	SUBSCRIBERS_ONLY_MODE(403, "Chỉ người đăng ký mới được chat.", HttpStatus.FORBIDDEN),

	// wallet
	WALLET_NOT_FOUND(504, "Không tìm thấy ví.", HttpStatus.NOT_FOUND),
	INSUFFICIENT_BALANCE(402, "Số dư không đủ.", HttpStatus.PAYMENT_REQUIRED),
	PACKAGE_NOT_FOUND(504, "Không tìm thấy gói nạp tiền.", HttpStatus.NOT_FOUND),

	// rewards/missions
	MISSION_NOT_FOUND(504, "Nhiệm vụ không tồn tại.", HttpStatus.NOT_FOUND),
	MISSION_EXISTED(502, "Nhiệm vụ đã tồn tại.", HttpStatus.BAD_REQUEST),
	MISSION_NOT_COMPLETED(400, "Nhiệm vụ chưa hoàn thành.", HttpStatus.BAD_REQUEST),
	MISSION_ALREADY_CLAIMED(502, "Nhiệm vụ đã được nhận thưởng.", HttpStatus.BAD_REQUEST),

	// events
	EVENT_NOT_FOUND(504, "Sự kiện không tồn tại.", HttpStatus.NOT_FOUND),
	ALREADY_JOINED_EVENT(502, "Bạn đã tham gia sự kiện này.", HttpStatus.BAD_REQUEST),
	EVENT_FULL(504, "Sự kiện đã đạt giới hạn người tham gia.", HttpStatus.BAD_REQUEST),

	// support
	TICKET_NOT_FOUND(504, "Ticket không tồn tại.", HttpStatus.NOT_FOUND),

	// site info
	SITE_INFO_NOT_FOUND(504, "Nội dung không tồn tại.", HttpStatus.NOT_FOUND),

	;

	ErrorCode(int code, String message, HttpStatusCode statusCode) {
		this.code = code;
		this.message = message;
		this.statusCode = statusCode;
	}

	private int code;
	private String message;
	private HttpStatusCode statusCode;
}
