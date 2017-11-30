package com.tongwii.dto;


import com.tongwii.domain.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the SubGroup entity.
 */
@Data
public class GroupDTO implements Serializable {

    private String id;

    private String name;

    private String des;

    private String userId;

    private Set<Role> roles = new HashSet<>();

    private String groupId;

}
