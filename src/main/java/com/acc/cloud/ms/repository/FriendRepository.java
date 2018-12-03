package com.acc.cloud.ms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.acc.cloud.ms.domain.Friend;

/**
 * A repository for managing {@link Friend} entities.
 *
 * @author Laxminarayan Rajput
 */
public interface FriendRepository extends JpaRepository<Friend, Long> {

    Friend findFriendByUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);

    Boolean existsByUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);

    Page<Friend> findAllByUserId(@Param("userId") Long userId, Pageable pageable);
}
