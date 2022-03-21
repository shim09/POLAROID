package com.project.polaroid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberAddInfo {
    private String memberPhone;
    private String memberAddress;
    private String memberNickname;
}
