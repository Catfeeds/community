package com.tongwii.dto.mapper;

import com.tongwii.domain.Floor;
import com.tongwii.dto.FloorDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FloorMapper {

    public List<Floor> toEntity(List<FloorDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Floor> list = new ArrayList<>();
        for ( FloorDTO floorDTO : arg0 ) {
            list.add( toEntity( floorDTO ) );
        }

        return list;
    }

    public List<FloorDTO> toDto(List<Floor> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<FloorDTO> list = new ArrayList<>();
        for ( Floor floor : arg0 ) {
            list.add( toDto( floor ) );
        }

        return list;
    }

    public FloorDTO toDto(Floor floor) {
        if ( floor == null ) {
            return null;
        }

        FloorDTO floorDTO_ = new FloorDTO();

        floorDTO_.setId( floor.getId() );
        floorDTO_.setCode( floor.getCode() );
        if ( floor.getFloorNumber() != null ) {
            floorDTO_.setFloorNumber( Integer.parseInt( floor.getFloorNumber() ) );
        }
        floorDTO_.setHasElev( floor.getHasElev() );
        floorDTO_.setResidenceId( floor.getResidenceId() );
        floorDTO_.setPrincipalId( floor.getPrincipalId() );

        return floorDTO_;
    }

    public Floor toEntity(FloorDTO floorDTO) {
        if ( floorDTO == null ) {
            return null;
        }

        Floor floor_ = new Floor();

        floor_.setId( floorDTO.getId() );
        floor_.setCode( floorDTO.getCode() );
        floor_.setFloorNumber( String.valueOf( floorDTO.getFloorNumber() ) );
        floor_.setHasElev( floorDTO.getHasElev() );
        floor_.setPrincipalId( floorDTO.getPrincipalId() );
        floor_.setResidenceId( floorDTO.getResidenceId() );

        return floor_;
    }
}
