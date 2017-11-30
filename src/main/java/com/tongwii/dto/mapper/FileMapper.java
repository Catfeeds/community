package com.tongwii.dto.mapper;

import com.tongwii.domain.File;
import com.tongwii.domain.User;
import com.tongwii.dto.FileDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FileMapper {

    private final UserMapper userMapper;

    public FileMapper(UserMapper userMapper) {this.userMapper = userMapper;}

    public List<File> toEntity(List<FileDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<File> list = new ArrayList<>();
        for ( FileDTO fileDTO : arg0 ) {
            list.add( toEntity( fileDTO ) );
        }

        return list;
    }

    public List<FileDTO> toDto(List<File> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<FileDTO> list = new ArrayList<>();
        for ( File file : arg0 ) {
            list.add( toDto( file ) );
        }

        return list;
    }

    public FileDTO toDto(File file) {
        if ( file == null ) {
            return null;
        }

        FileDTO fileDTO_ = new FileDTO();

        fileDTO_.setUploadUserId( fileUploadUserId( file ) );
        fileDTO_.setId( file.getId() );
        fileDTO_.setFileName( file.getFileName() );
        fileDTO_.setFilePath( file.getFilePath() );
        fileDTO_.setFileType( file.getFileType() );
        fileDTO_.setDes( file.getDes() );
        if ( file.getState() != null ) {
            fileDTO_.setState( file.getState().intValue() );
        }

        return fileDTO_;
    }

    public File toEntity(FileDTO fileDTO) {
        if ( fileDTO == null ) {
            return null;
        }

        File file_ = new File();

        file_.setUploadUser( userMapper.userFromId( fileDTO.getUploadUserId() ) );
        file_.setId( fileDTO.getId() );
        file_.setFileName( fileDTO.getFileName() );
        file_.setFilePath( fileDTO.getFilePath() );
        file_.setFileType( fileDTO.getFileType() );
        file_.setDes( fileDTO.getDes() );
        if ( fileDTO.getState() != null ) {
            file_.setState( fileDTO.getState().byteValue() );
        }
        file_.setUploadUserId( fileDTO.getUploadUserId() );

        return file_;
    }

    private String fileUploadUserId(File file) {

        if ( file == null ) {
            return null;
        }
        User uploadUser = file.getUploadUser();
        if ( uploadUser == null ) {
            return null;
        }
        String id = uploadUser.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
