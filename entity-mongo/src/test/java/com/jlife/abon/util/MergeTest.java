package com.jlife.abon.util;

import com.jlife.abon.entity.User;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Dzmitry Misiuk
 */
public class MergeTest {

    @Test
    public void testMergeUser() throws IllegalAccessException {
        User oldUser = new User();
        User newUser = new User();
        oldUser.setId("200");
        oldUser.setEmail("m@m");

        newUser.setEmail("m1@m");
        newUser.setPassword("**");

        User mergedUser = (User) oldUser.merge(newUser);
        Assert.assertNotNull(mergedUser);
        Assert.assertEquals("m1@m", mergedUser.getEmail());
        Assert.assertEquals("200", mergedUser.getId());

    }
}
