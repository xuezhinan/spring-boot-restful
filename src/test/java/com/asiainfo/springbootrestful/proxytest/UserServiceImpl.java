package com.asiainfo.springbootrestful.proxytest;

public class UserServiceImpl  implements UserService{
    @Override
    public void add(String name) {
        System.out.println("添加的名字是"+name);
    }
}
