package com.nosoftskills.predcomposer.alternatives;

import com.nosoftskills.predcomposer.model.User;
import com.nosoftskills.predcomposer.user.UsersService;

import javax.enterprise.inject.Alternative;
import java.util.Arrays;
import java.util.List;

import static com.nosoftskills.predcomposer.common.TestData.user1;
import static com.nosoftskills.predcomposer.common.TestData.user2;

/**
 * @author Ivan St. Ivanov
 */
@Alternative
public class UsersServiceAlternative extends UsersService {

    @Override
    public List<User> getAllUsers() {
        return Arrays.asList(user1, user2);
    }
}
