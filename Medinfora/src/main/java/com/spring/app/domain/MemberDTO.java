package com.spring.app.domain;

public class MemberDTO {
	
	private String userid;			// 아이디
	private String pwd;				// 비밀번호(단방향 암호화)
	private String email;			// 이메일(양방향 암호화)
	private String name;			// 이름
	private String address;			// 주소명
	private String detailAddress;	// 상세주소
	private String birthday;		// 생년월일
	private String mobile;			// 연락처(양방향 암호화)
	private int gender;				// 성별
	private int mIdx;				// 회원코드 초기값 0(관리자 이므로) 무조건 유효성 해야함
	private String registerday;		// 가입일자
	// private String pwdUpdateday;	// 비밀번호 업데이트 날짜
	
	
	// 추가 컬럼
	private int loginmethod;	// 로그인방법 ( 0 : 일반, 1 : 카카오)
	
	
	// === Select 용 === //
	private int lastlogingap;	// 로그인한지 12개월 넘었는지 확인하는 용도
	private int pwdchangegap;	// 비밀번호 변경한지 얼마나 되었는지 확인용
	private boolean requirePwdChange = false;	// 비밀번호 변경안한지 3개월 지났는지 확인 용도
	
	
	private HospitalDTO hdto;
	
	private Integer postcount; // 게시글 올린 갯수
	private String lastLogin; // loginlog에 있는 접속일 가져올 것임 
	private String memberStatusString; //회원 상태 
	
	private String age;
	
	////////////////////////////////////////////////////////////////
	
	// === Getter, Setter === //
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	
	
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	
	
	public int getmIdx() {
		return mIdx;
	}
	public void setmIdx(int mIdx) {
		this.mIdx = mIdx;
	}
	
	
	public String getRegisterday() {
		return registerday;
	}
	public void setRegisterday(String registerday) {
		this.registerday = registerday;
	}
	
	
	public int getLastlogingap() {
		return lastlogingap;
	}
	public void setLastlogingap(int lastlogingap) {
		this.lastlogingap = lastlogingap;
	}
	
	
	public int getPwdchangegap() {
		return pwdchangegap;
	}
	public void setPwdchangegap(int pwdchangegap) {
		this.pwdchangegap = pwdchangegap;
	}
	
	
	public boolean isRequirePwdChange() {
		return requirePwdChange;
	}
	public void setRequirePwdChange(boolean requirePwdChange) {
		this.requirePwdChange = requirePwdChange;
	}
	
	public int getLoginmethod() {
		return loginmethod;
	}
	public void setLoginmethod(int loginmethod) {
		this.loginmethod = loginmethod;
	}
	
	
		public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getMemberStatusString() {
		return memberStatusString;
	}
	public void setMemberStatusString(String memberStatusString) {
		this.memberStatusString = memberStatusString;
	}
	public HospitalDTO getHdto() {
		return hdto;
	}
	public void setHdto(HospitalDTO hdto) {
		this.hdto = hdto;
	}
	public Integer getPostcount() {
		return postcount;
	}
	public void setPostcount(Integer postcount) {
		this.postcount = postcount;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	
}
