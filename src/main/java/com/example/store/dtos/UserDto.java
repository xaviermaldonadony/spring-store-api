package com.example.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto  {
    private Long id;
    String name;
    String email;
}
