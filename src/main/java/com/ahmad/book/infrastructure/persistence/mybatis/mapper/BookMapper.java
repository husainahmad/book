package com.ahmad.book.infrastructure.persistence.mybatis.mapper;

import com.ahmad.book.domain.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {

    @Insert("INSERT INTO books (title, author, isbn, total_copies, available_copies) " +
            "VALUES (#{title}, #{author}, #{isbn}, #{totalCopies}, #{availableCopies})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    void insert(Book book);

    @Select("SELECT id, title, author, isbn, total_copies AS totalCopies, available_copies AS availableCopies " +
            "FROM books WHERE id = #{id}")
    @Results(id = "BookResultMap",
            value = {
                    @Result(property = "id", column = "id"),
                    @Result(property = "title", column = "title"),
                    @Result(property = "author", column = "author"),
                    @Result(property = "isbn", column = "isbn"),
                    @Result(property = "totalCopies", column = "totalCopies"),
                    @Result(property = "availableCopies", column = "availableCopies")
            })
    Book selectById(Long id);

    @Select("SELECT id, title, author, isbn, total_copies AS totalCopies, available_copies AS availableCopies " +
            "FROM books WHERE isbn = #{isbn}")
    @ResultMap("BookResultMap")
    Book selectByIsbn(String isbn);

    @Select("SELECT id, title, author, isbn, total_copies AS totalCopies, available_copies AS availableCopies " +
            "FROM books")
    @ResultMap("BookResultMap")
    List<Book> selectAll();

    @Update("UPDATE books SET title = #{title}, author = #{author}, isbn = #{isbn}, " +
            "total_copies = #{totalCopies}, available_copies = #{availableCopies} WHERE id = #{id}")
    void update(Book book);
}
