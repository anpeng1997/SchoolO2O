package cn.pengan.entity;

public class PhoneAuth {
    private Long phoneAuthId;
    private Long userId;
    private String phone;
    private String authNumber;

    public Long getPhoneAuthId() {
        return phoneAuthId;
    }

    public void setPhoneAuthId(Long phoneAuthId) {
        this.phoneAuthId = phoneAuthId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuthNumber() {
        return authNumber;
    }

    public void setAuthNumber(String authNumber) {
        this.authNumber = authNumber;
    }

    @Override
    public String toString() {
        return "PhoneAuth{" +
                "phoneAuthId=" + phoneAuthId +
                ", userId=" + userId +
                ", phone='" + phone + '\'' +
                ", authNumber='" + authNumber + '\'' +
                '}';
    }
}
