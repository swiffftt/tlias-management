package com.shuaige.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginInfo {
    private Integer id;
    private String username;
    private String name;
    private String token;
}
