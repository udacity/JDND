package com.udacity.course3.exercise13.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "members")
public class Member {

    @Id
    private ObjectId id;
}
