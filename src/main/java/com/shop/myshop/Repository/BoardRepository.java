package com.shop.myshop.Repository;

import com.shop.myshop.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByBoardNo(Long boardNo);
    


}
