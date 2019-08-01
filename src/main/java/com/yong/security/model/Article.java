package com.yong.security.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "article")
public class Article {
    @Id
    private String id;
    private String title;
    private String text;
    private String openid;
    private String nickName;
    private String imageUrl;

    private Date createdDate;
    private Long like;
    private Long collection;
    private Long reading;

}

