package com.xz.schoolnavinfo.data.dto;

import com.xz.schoolnavinfo.authentication.UserInfo;
import com.xz.schoolnavinfo.data.entity.Stuff;
import com.xz.schoolnavinfo.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StuffDTO {
    Stuff stuff;
    UserInfo userInfo;
}
