package com.project.polaroid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDTO {

    private String memberPhone;
    private String memberAddress;
    private String memberNickname;
    private String memberFilename;
    private MultipartFile memberFile;

}
