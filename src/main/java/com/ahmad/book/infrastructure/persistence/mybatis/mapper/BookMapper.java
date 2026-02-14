package com.ahmad.book.infrastructure.persistence.mybatis.mapper;

import com.ahmad.book.domain.Book;
import org.apache.ibatis.annotations.*;

@Mapper
public interface BookMapper {

    @Insert("INSERT INTO books (title, author, isbn, total_copies, available_copies) " +
            "VALUES (#{title}, #{author}, #{isbn}, #{totalCopies}, #{availableCopies})")
    int insert(Book book);

    @Select("SELECT id, title, author, isbn, total_copies AS totalCopies, available_copies AS availableCopies " +
            "FROM books WHERE id = #{id}")
    @Results(id = "BookResultMap",
            value = {
                    @Result(property = "id", column = "id"),
                    @Result(property = "title", column = "title"),
                    @Result(property = "author", column = "author"),
                    @Result(property = "isbn", column = "isbn"),
                    @Result(property = "totalCopies", column = "total_copies"),
                    @Result(property = "availableCopies", column = "available_copies")
            })
    Book selectById(Long id);

    @Update("UPDATE books SET title = #{title}, author = #{author}, isbn = #{isbn}, " +
            "total_copies = #{totalCopies}, available_copies = #{availableCopies} WHERE id = #{id}")
    void update(Book book);
}
