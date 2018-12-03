package com.acc.cloud.ms.resource;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acc.cloud.ms.domain.Friend;
import com.acc.cloud.ms.service.FriendService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller responsible for exposing REST API interface for managing {@link Friend} domain entities.
 *
 * @author Laxminarayan Rajput
 */
@Slf4j
@RestController
@RequestMapping("/v1")
public class FriendResource {

    private final FriendService friendService;

    public FriendResource(FriendService friendService) {
        this.friendService = friendService;
    }

    @RequestMapping(path = "/users/{userId}/friends", method = RequestMethod.GET)
    public HttpEntity<?> getFriends(@PathVariable Long userId, Pageable pageable,
                                    PagedResourcesAssembler<Friend> assembler) {
    	log.info("In getFriends Resource method..");
        return Optional.of(friendService.getFriends(userId, pageable))
                .map(a -> new ResponseEntity<>(assembler.toResource(a), HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Could not retrieve friends for the supplied user id"));
    }

    @RequestMapping(path = "/users/{userId}/commands/addFriend", method = RequestMethod.POST)
    public HttpEntity<?> addFriend(@PathVariable Long userId, @RequestParam("friendId") Long friendId) {
        Friend friend;

        if (null != userId && null != friendId) {
        	friend = friendService.addFriend(userId, friendId);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("In addFriend Resource method..");
        return new ResponseEntity<>(friend, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/users/{userId}/commands/removeFriend", method = RequestMethod.PUT)
    public HttpEntity<?> removeFriend(@PathVariable Long userId, @RequestParam("friendId") Long friendId) {

        Friend friend;
        if (null != userId && null != friendId) {
        	friend = friendService.removeFriend(userId, friendId);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("In removeFriend Resource method..");
        return new ResponseEntity<>(friend, HttpStatus.NO_CONTENT);
    }
}
