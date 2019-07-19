package com.huawei.servicecomb.entity;

import lombok.*;

/**
 * @author DEV-TOM
 * user
 */

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class User {
    private String userId;
    private String userName;
    private String phone;
}
