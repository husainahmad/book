package com.ahmad.book.infrastructure.persistence.mybatis.mapper;

import com.ahmad.book.domain.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberMapper {
        @Insert("INSERT INTO members (name, email) VALUES (#{name}, #{email})")
        @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
        void insert(Member member);

        @Select("SELECT id, name, email FROM members WHERE id = #{id}")
        @Results(id = "MemberResultMap",
                value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "name", column = "name"),
                        @Result(property = "email", column = "email")
                })
        Member selectById(Long id);

        @Select("SELECT id, name, email FROM members WHERE email = #{email}")
        @ResultMap("MemberResultMap")
        Member selectByEmail(String email);

        @Select("SELECT id, name, email FROM members")
        @ResultMap("MemberResultMap")
        List<Member> selectAll();

        @Update("UPDATE members SET name = #{name}, email = #{email} WHERE id = #{id}")
        void update(Member member);
}
