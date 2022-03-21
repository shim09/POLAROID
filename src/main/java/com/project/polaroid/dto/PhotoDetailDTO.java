package com.project.polaroid.dto;

import com.project.polaroid.entity.PhotoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDetailDTO {

    private Long photoId;
    private Long boardId;
    private String boardFilename;

    public static PhotoDetailDTO toPhotoDetailDTO(PhotoEntity photoEntity) {
        PhotoDetailDTO photoDetailDTO = new PhotoDetailDTO();
        photoDetailDTO.setPhotoId(photoEntity.getId());
        photoDetailDTO.setBoardId(photoEntity.getBoardId().getId());
        photoDetailDTO.setBoardFilename(photoEntity.getBoardFilename());
        return photoDetailDTO;
    }

    public static List<PhotoDetailDTO> toPhotoDetailDTOList(List<PhotoEntity> photoEntityList) {
        List<PhotoDetailDTO> photoDetailDTOList = new ArrayList<>();
        for (PhotoEntity p: photoEntityList) {
            photoDetailDTOList.add(toPhotoDetailDTO(p));
        }
        return photoDetailDTOList;
    }
}