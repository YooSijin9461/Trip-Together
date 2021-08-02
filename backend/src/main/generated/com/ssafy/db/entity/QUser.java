package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 846542477L;

    public static final QUser user = new QUser("user");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final NumberPath<Double> avgScore = createNumber("avgScore", Double.class);

    public final NumberPath<Integer> conferenceRoomNo = createNumber("conferenceRoomNo", Integer.class);

    public final StringPath email = createString("email");

    public final ComparablePath<Character> gender = createComparable("gender", Character.class);

    public final StringPath img = createString("img");

    public final BooleanPath isGuide = createBoolean("isGuide");

    public final StringPath mbti = createString("mbti");

    public final StringPath password = createString("password");

    public final StringPath phoneNum = createString("phoneNum");

    public final StringPath userId = createString("userId");

    public final StringPath userName = createString("userName");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

