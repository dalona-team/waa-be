package com.dalona.waa.enums;

public enum DogStatus {
    PRE_ADOPTION,              // 임시보호 및 입양 전
    FOSTER_PENDING,           // 임시보호 대기중
    ADOPTION_PENDING,         // 입양 대기중
    OVERSEAS_VOLUNTEER_NEEDED, // 해외이동봉사 모집중
    FOSTER_UNDER_REVIEW,      // 임보 심사중
    ADOPTION_UNDER_REVIEW,    // 입양 심사중
    FOSTERING,                // 임시보호중
    ADOPTED                   // 입양 완료
}
