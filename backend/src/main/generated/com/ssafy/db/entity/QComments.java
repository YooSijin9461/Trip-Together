package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QComments is a Querydsl query type for Comments
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QComments extends EntityPathBase<Comments> {

    private static final long serialVersionUID = -590415946L;

    public static final QComments comments = new QComments("comments");

    public final NumberPath<Integer> boardNo = createNumber("boardNo", Integer.class);

    public final StringPath comment = createString("comment");

    public final NumberPath<Integer> commentNo = createNumber("commentNo", Integer.class);

    public final DateTimePath<java.sql.Timestamp> commentTime = createDateTime("commentTime", java.sql.Timestamp.class);

    public final StringPath userId = createString("userId");

    public QComments(String variable) {
        super(Comments.class, forVariable(variable));
    }

    public QComments(Path<? extends Comments> path) {
        super(path.getType(), path.getMetadata());
    }

    public QComments(PathMetadata metadata) {
        super(Comments.class, metadata);
    }

}

