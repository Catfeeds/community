package com.tongwii.dto.mapper;

import com.tongwii.domain.File;
import com.tongwii.dto.FileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity File and its DTO FileDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface FileMapper extends EntityMapper<FileDTO, File> {

    @Mapping(source = "uploadUser.id", target = "uploadUserId")
    FileDTO toDto(File file);

    @Mapping(source = "uploadUserId", target = "uploadUser")
    File toEntity(FileDTO fileDTO);

    default File fromId(String id) {
        if (id == null) {
            return null;
        }
        File file = new File();
        file.setId(id);
        return file;
    }
}
