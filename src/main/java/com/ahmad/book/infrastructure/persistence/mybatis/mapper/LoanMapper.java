package com.ahmad.book.infrastructure.persistence.mybatis.mapper;

import com.ahmad.book.domain.Loan;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoanMapper {

    @Insert("INSERT INTO loans (member_id, book_id, loan_date, return_date) " +
            "VALUES (#{memberId}, #{bookId}, #{loanDate}, #{returnDate})")
    int insert(Loan loan);

    @Select("SELECT id, member_id AS memberId, book_id AS bookId, loan_date AS loanDate, return_date AS returnDate " +
            "FROM loans WHERE id = #{id}")
    @Results(id = "LoanResultMap",
            value = {
                    @Result(property = "id", column = "id"),
                    @Result(property = "memberId", column = "member_id"),
                    @Result(property = "bookId", column = "book_id"),
                    @Result(property = "loanDate", column = "loan_date"),
                    @Result(property = "returnDate", column = "return_date")
            })
    Loan selectById(Long id);

    @Update("UPDATE loans SET member_id = #{memberId}, book_id = #{bookId}, loan_date = #{loanDate}, " +
            "return_date = #{returnDate} WHERE id = #{id}")
    void update(Loan loan);
}
