package com.acc.cloud.ms.service;

import javax.transaction.Transactional;

import org.springframework.cloud.stream.messaging.Source;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.acc.cloud.ms.domain.Friend;
import com.acc.cloud.ms.event.EventType;
import com.acc.cloud.ms.event.FriendEvent;
import com.acc.cloud.ms.repository.FriendRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller responsible for exposing REST API interface for managing {@link Friend} domain entities.
 *
 * @author Laxminarayan Rajput
 */
@Service
@Slf4j
@Transactional
public class FriendService {

    private final Source source;
    private final FriendRepository friendRepository;

    public FriendService(Source source, FriendRepository friendRepository) {
        this.source = source;
        this.friendRepository = friendRepository;
    }

    public Page<Friend> getFriends(Long userId, Pageable pageable) {
    	Page<Friend> friends;
    	if(null != friendRepository.findAllByUserId(userId, pageable)){
    		
    		friends = friendRepository.findAllByUserId(userId, pageable);
		}else {
			return null;
		} 
    	log.info("In getFriends method..");
    	return friends;
    }
    
    public Friend addFriend(Long userId, Long friendId) {
        Friend friend;
        // Check if friend relationship already exists
        if (!friendRepository.existsByUserIdAndFriendId(userId, friendId)) {
            friend = new Friend(userId, friendId);

            // Save friend relationship
            friendRepository.save(friend);

            // Broadcast a new domain event
            source.output().send(MessageBuilder
                    .withPayload(new FriendEvent(friend, EventType.FRIEND_ADDED)).build());
        } else {
            return null;
        }
    	log.info("In addFriend method..");
        return friend;
    }

    public Friend removeFriend(Long userId, Long friendId) {
        // Fetch friend relationship
        Friend friend = friendRepository.findFriendByUserIdAndFriendId(userId, friendId);

        if (friend != null) {
            // Delete friend relationship
            friendRepository.delete(friend);

            // Broadcast a new domain event
            source.output().send(MessageBuilder
                    .withPayload(new FriendEvent(friend, EventType.FRIEND_REMOVED)).build());
        } else {
            return null;
        }
        log.info("In removeFriend method..");
        return friend;
    }
}
