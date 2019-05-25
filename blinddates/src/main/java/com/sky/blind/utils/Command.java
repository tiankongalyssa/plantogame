package com.sky.blind.utils;

import com.sky.blind.dao.UserMapper;
import com.sky.blind.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Command {
    @Autowired
    private UserMapper userMapper;

    public String clearImg() {
        int total;
        int userimg;
        int residue; //剩余
        List<String> fileNames = new ArrayList<>();
        List<String> userImages = new ArrayList<>();
//        File file = new File("D:/upload");
        File file = new File("/www/wwwroot/api.uixkei.cn/upload");
        File[] files = file.listFiles();
        System.out.println("allImages:");
        if (files != null) {
            for (File f : files) {
                System.out.println(f.getName());
                fileNames.add(f.getName());
            }
        }
        total = fileNames.size();
        List<User> all = userMapper.findAll();
        for (User user : all) {
            userImages.add(user.getUserFace().substring(user.getUserFace().lastIndexOf("/") + 1));
            if (user.getImages() != null) {
                String[] split = user.getImages().split(",");
                for (int i = 0; i < split.length; i++) {
                    String str = split[i];
                    split[i] = str.substring(str.lastIndexOf("/") + 1);
                }
                List<String> list = Arrays.asList(split);
                userImages.addAll(list);
            }
        }
        userimg = userImages.size();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("userImages:");
        for (String s : userImages) {
            System.out.println(s);
        }
        fileNames.removeAll(userImages);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("after remove:");
        for (String s : fileNames) {
            System.out.println(s);
        }
        for (String s : fileNames) {
//            File file1 = new File("D:/upload/" + s);
            File file1 = new File("/www/wwwroot/api.uixkei.cn/upload/" + s);
            if (file1.exists()) {
                file1.delete();
            }
        }
//        file = new File("D:/upload");
        file = new File("/www/wwwroot/api.uixkei.cn/upload");
        files = file.listFiles();
        residue = files.length;
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("清理之后");
        for (File f : files) {
            System.out.println(f.getName());
        }
        String s = "fileTotal:" + total + "  userFile:" + userimg + " after Clear:" + residue + " total Clear:" + (total - residue);
        System.out.println(s);
        return s;
    }
}
