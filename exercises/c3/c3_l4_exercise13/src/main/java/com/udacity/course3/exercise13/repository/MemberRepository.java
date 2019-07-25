package com.udacity.course3.exercise13.repository;

import com.udacity.course3.exercise13.model.Member;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends MongoRepository<Member, ObjectId> {

}
