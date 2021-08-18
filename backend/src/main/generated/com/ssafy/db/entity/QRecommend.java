package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRecommend extends EntityPathBase<Recommend> {

    private static final long serialVersionUID = 455343204L;

    public static final QRecommend recommend = new QRecommend("recommend");

    public final NumberPath<Long> recommendNo = createNumber("recommendNo", Long.class);
    
    public final NumberPath<Integer> boardNo = createNumber("boardNo", Integer.class);
    
    public final StringPath userId = createString("userId");
    
    public final BooleanPath likeCheck = createBoolean("likeCheck");
    
    public final BooleanPath hateCheck = createBoolean("hateCheck");

    public QRecommend(String variable) {
        super(Recommend.class, forVariable(variable));
    }

    public QRecommend(Path<? extends Recommend> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecommend(PathMetadata metadata) {
        super(Recommend.class, metadata);
    }

}

