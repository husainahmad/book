package com.ahmad.book.infrastructure.persistence.mybatis.mapper;

import com.ahmad.book.domain.Loan;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LoanMapper {

    @Insert("INSERT INTO loans (member_id, book_id, borrowed_at, due_date, returned_at) " +
            "VALUES (#{memberId}, #{bookId}, #{borrowedAt}, #{dueDate}, #{returnedAt})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    void insert(Loan loan);

    @Select("SELECT id, member_id AS memberId, book_id AS bookId, borrowed_at as borrowedAt, due_date as dueDate, returned_at as returnedAt " +
            "FROM loans WHERE id = #{id}")
    @Results(id = "LoanResultMap",
            value = {
                    @Result(property = "id", column = "id"),
                    @Result(property = "memberId", column = "memberId"),
                    @Result(property = "bookId", column = "bookId"),
                    @Result(property = "borrowedAt", column = "borrowedAt"),
                    @Result(property = "dueDate", column = "dueDate"),
                    @Result(property = "returnedAt", column = "returnedAt")
            })
    Loan selectById(Long id);

    @Select("SELECT id, member_id AS memberId, book_id AS bookId, borrowed_at as borrowedAt, due_date as dueDate, returned_at as returnedAt " +
            "FROM loans WHERE id = #{id} AND book_id = #{bookId} AND member_id = #{memberId}")
    @ResultMap("LoanResultMap")
    Loan selectByIdBookIdMemberId(Long id, Long bookId, Long memberId);

    @Select("SELECT id, member_id AS memberId, book_id AS bookId, borrowed_at as borrowedAt, due_date as dueDate, returned_at as returnedAt " +
            "FROM loans")
    @ResultMap("LoanResultMap")
    List<Loan> selectAll();

    @Select("SELECT id, member_id AS memberId, book_id AS bookId, borrowed_at as borrowedAt, due_date as dueDate, returned_at as returnedAt  " +
            "FROM loans WHERE member_id = #{memberId} AND returned_at IS NULL")
    @ResultMap("LoanResultMap")
    List<Loan> selectActiveByMemberId(Long memberId);

    @Select("SELECT id, member_id AS memberId, book_id AS bookId, borrowed_at as borrowedAt, due_date as dueDate, returned_at as returnedAt  " +
            "FROM loans WHERE member_id = #{memberId} AND due_date < #{now} AND returned_at IS NULL")
    @ResultMap("LoanResultMap")
    List<Loan> selectOverdueByMemberId(Long memberId, LocalDateTime now);

    @Update("UPDATE loans SET member_id = #{memberId}, book_id = #{bookId}, borrowed_at = #{borrowedAt}, " +
            "returned_at = #{returnedAt} WHERE id = #{id}")
    void update(Loan loan);

    @Update("UPDATE loans SET returned_at = #{returnedAt}, updated_at = NOW() WHERE id = #{id}")
    void updateReturnedAt(Long id, LocalDateTime returnedAt);
}
